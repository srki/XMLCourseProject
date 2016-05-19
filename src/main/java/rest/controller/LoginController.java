package rest.controller;

import dao.IUserDao;
import model.User;
import rest.requests.AuthenticationRequest;
import rest.response.AuthenticationResponse;
import rest.response.ResponseFactory;

import javax.annotation.security.RolesAllowed;
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
    private IUserDao userDao;

    @GET
    @RolesAllowed({User.CITIZEN, User.REPRESENTATIVE, User.PRESIDENT})
    public Object get(@Context User user) {
        return new AuthenticationResponse(user.getUsername(), user.getType(), user.getName(), user.getLastname());
    }

    @POST
    public Object post(@Valid AuthenticationRequest authRequest, @Context HttpServletRequest request) throws JAXBException {
        User u = userDao.getUser(authRequest.getUsername());
        if (u != null && u.getPassword().equals(authRequest.getPassword())) {
            request.getSession().setAttribute(USER_IDENTIFIER, u);
            return new AuthenticationResponse(u.getUsername(), u.getType(), u.getName(), u.getLastname());
        } else {
            return ResponseFactory.createErrorResponse(Response.Status.BAD_REQUEST);
        }

    }
}
