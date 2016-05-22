package rest.controller;

import dao.ISessionDao;
import model.Sessions;
import rest.response.ResponseFactory;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

@Path("/sessions")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class SessionsController {

    @EJB
    private ISessionDao sessionDao;

    @GET
    public Object get(@QueryParam("status") String status) {
        try {
            Sessions sessions;
            sessions = sessionDao.getAllSessions(status);

            return Response.status(200).entity(sessions).build();
        } catch (JAXBException e){
            e.printStackTrace();
            return Response.status(400).build();
        }
    }


    @POST
    public Object post(@Context HttpServletRequest request, String sessionString) {

        try {
            sessionDao.storeSession(sessionString);

            return Response
                    .status(Response.Status.CREATED)
                    .build();

        } catch (Exception e) {
            return ResponseFactory.createErrorResponse(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }
}
