package rest.controller;

import dao.ISessionDao;
import model.Sessions;

import javax.ejb.EJB;
import javax.ws.rs.*;
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
    public Object get(@QueryParam("req") String req) {
        try {
            Sessions sessions;
            if ("upcoming".equals(req)) {
                sessions = sessionDao.getUpcomingSessions();
                return Response.status(200).entity(sessions).build();
            } else if ("finished".equals(req)) {
                sessions = sessionDao.getFinishedSessions();
                return Response.status(200).entity(sessions).build();
            } else {
                sessions = sessionDao.getAllSessions();
                return Response.status(200).entity(sessions).build();
            }
        } catch (JAXBException e){
            e.printStackTrace();
            return Response.status(400).build();
        }
    }

    @POST
    public Object post() {
        return null;
    }
}
