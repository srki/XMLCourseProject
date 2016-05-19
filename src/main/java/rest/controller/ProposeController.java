package rest.controller;

import dao.IActDao;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/propose")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class ProposeController {

    @EJB
    IActDao actDao;

    @POST
    public Object post(@Context HttpServletRequest request) {

        try {

            String xml = org.apache.commons.io.IOUtils.toString(request.getInputStream());
            actDao.storeAct(xml);

            return Response
                    .status(Response.Status.CREATED)
                    .build();

        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
