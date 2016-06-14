package rest.controller;

import dao.IAmendmentDao;
import model.User;
import rest.response.ResponseFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/amendments")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class AmendmentController {

    @EJB
    IAmendmentDao amendmentDao;

    @POST
    @RolesAllowed({User.REPRESENTATIVE, User.PRESIDENT})
    public Object post(String raw, @Context User user) {
        try {
            System.out.println("asd");
            amendmentDao.storeAmendment(raw, user.getUsername());

            return Response
                    .status(Response.Status.CREATED)
                    .build();

        } catch (Exception e) {
            return ResponseFactory.createErrorResponse(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    @GET
    public Object get(@QueryParam("status") String status, @QueryParam("username") String username) {
        return amendmentDao.getAllAmendments("", status, username);
    }

    @GET
    @Path("/{actUri}")
    public Object get(@PathParam("actUri") String actUri, @QueryParam("status") String status) {
        return amendmentDao.getAllAmendments(actUri, status);
    }
}
