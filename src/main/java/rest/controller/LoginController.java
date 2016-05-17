package rest.controller;

import dao.IUserDao;
import model.User;
import rest.requests.AuthenticationRequest;
import rest.response.AuthenticationResponse;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

@Path("/login")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class LoginController {

    public static final String USER_IDENTIFIER = "USER";

    @EJB
    IUserDao userDao;

    @GET
    public Object get(@Context HttpServletRequest request) {

        User u = (User) request.getSession().getAttribute(USER_IDENTIFIER);

        if(u != null){
            return Response.status(200)
                    .entity(new AuthenticationResponse(u.getUsername(), u.getType(), u.getName(), u.getLastname()))
                    .build();
        }
        else {
            return Response.status(400)
                    .build();
        }
    }

    @POST
    public Object post(@Valid AuthenticationRequest authRequest, @Context HttpServletRequest request) {

        try {
            User u = userDao.getUser(authRequest.getUsername());

            if (u != null && u.getPassword().equals(authRequest.getPassword())) {

                request.getSession().setAttribute(USER_IDENTIFIER, u);
                return Response.status(200)
                        .entity(new AuthenticationResponse(u.getUsername(), u.getType(), u.getName(), u.getLastname()))
                        .build();
            }
            else {
                return Response.status(400).build();
            }

        } catch (JAXBException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
