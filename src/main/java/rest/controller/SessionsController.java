package rest.controller;

import dao.ISessionDao;
import model.User;
import rest.response.ResponseFactory;

import javax.annotation.security.RolesAllowed;
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
    @RolesAllowed({User.REPRESENTATIVE, User.PRESIDENT})
    public Object get(@QueryParam("status") String status) {
        try {
            String sessions = sessionDao.getAllSessions(status);
            return Response.status(200).entity(sessions).build();
        } catch (JAXBException e){
            e.printStackTrace();
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/{uuid}")
    @RolesAllowed({User.REPRESENTATIVE, User.PRESIDENT})
    public Object getSingle(@PathParam("uuid") String uuid) {
        return sessionDao.getDocument(uuid);
    }

    @POST
    @RolesAllowed(User.PRESIDENT)
    public Object post(@Context HttpServletRequest request, String sessionString) {
        try {
            sessionDao.storeSession(sessionString);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return ResponseFactory.createErrorResponse(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

}
