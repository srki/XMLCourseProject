package rest.controller;

import dao.ISystemStatusDao;
import model.User;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/systemStatus")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class SystemStatusController {

    @EJB
    private ISystemStatusDao systemStatusDao;

    @GET
    public Object get() {
        try {
            String status = systemStatusDao.getDocumentAsString(ISystemStatusDao.documentName);
            return Response.status(200).entity(status).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(400).build();
        }
    }

    @PUT
    @RolesAllowed({User.PRESIDENT})
    public Object put(String status) {
        try {
            systemStatusDao.updateStatus(status);
            return Response.status(200).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(400).build();
        }
    }
}
