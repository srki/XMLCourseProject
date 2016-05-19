package rest.controller;

import dao.IActDao;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/acts")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class ProposeController {

    @EJB
    private IActDao actDao;

    @POST
    public Object post(@Context HttpServletRequest request, String actString) {

        try {
            actDao.storeAct(actString);

            return Response
                    .status(Response.Status.CREATED)
                    .build();

        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @GET
    @Path("/{uuid}")
    public Object get(@PathParam("uuid") String uuid) {
        return actDao.getDocument(uuid);
    }

    @GET
    public Object get(@QueryParam("title") String title,
                      @QueryParam("country") String country,
                      @QueryParam("region") String region,
                      @QueryParam("establishment") String establishment,
                      @QueryParam("date") String date,
                      @QueryParam("city") String city,
                      @QueryParam("serial") String serial) {




        return actDao.getAllActs();
    }
}
