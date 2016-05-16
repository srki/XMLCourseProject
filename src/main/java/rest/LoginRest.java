package rest;

import dao.IUserDao;
import model.User;
import rest.response.TestXML;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@Produces(MediaType.APPLICATION_XML)
public class LoginRest {

    @EJB
    IUserDao userDao;

    @POST
    public Object get() {

        return Response
                .status(Response.Status.CREATED)
                .entity(null)
                .build();
    }
}
