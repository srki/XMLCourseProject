package rest.controller;

import dao.IActDao;
import dao.IAmendmentDao;
import model.User;
import rest.response.ResponseFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/acts")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class ActsController {

    @EJB
    private IActDao actDao;

    @EJB
    private IAmendmentDao amendmentDao;

    @POST
    @RolesAllowed({User.REPRESENTATIVE, User.PRESIDENT})
    public Object post(@Context HttpServletRequest request, String actString, @Context User user) {
        try {
            actDao.storeAct(actString, user.getUsername());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return ResponseFactory.createErrorResponse(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    @GET
    @Path("/{uri}")
    public Object get(@PathParam("uri") String uri) {
        return actDao.getDocument(uri);
    }

    @GET
    @Path("/{uri}/amendments")
    public Object getAmendments(@PathParam("uri") String uri, @QueryParam("status") String status,
                                @QueryParam("username") String username) {
        return amendmentDao.getAllAmendments(uri, status, username);
    }

    @GET
    @Path("/{uri}/articles/{id}/{format}")
    public Object getArticle(@PathParam("uri") String uri,
                             @PathParam("id") String id,
                             @PathParam("format") String format) {
        return actDao.getArticle(uri, id, format);
    }

    @GET
    public Object get(@QueryParam("text") String text,
                      @QueryParam("title") String title,
                      @QueryParam("country") String country,
                      @QueryParam("region") String region,
                      @QueryParam("establishment") String establishment,
                      @QueryParam("start_date") Long startDate,
                      @QueryParam("end_date") Long endDate,
                      @QueryParam("city") String city,
                      @QueryParam("serial") String serial,
                      @QueryParam("status") String status,
                      @QueryParam("username") String username) {

        return actDao.getAllActs(text, title, country, region, establishment, startDate,
                                 endDate, city, serial, status, username);
    }
}
