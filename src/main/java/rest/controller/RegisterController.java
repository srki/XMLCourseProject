package rest.controller;


import dao.IUserDao;
import model.User;
import rest.requests.RegistrationRequest;
import rest.response.ResponseFactory;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class RegisterController {

    @EJB
    IUserDao userDao;

    @POST
    public Object post(@Valid RegistrationRequest registrationRequest, @Context HttpServletRequest request) {

        User u = new User();
        u.setUsername(registrationRequest.getUsername());
        u.setPassword(registrationRequest.getPassword());
        u.setName(registrationRequest.getName());
        u.setLastname(registrationRequest.getLastname());
        u.setType(User.CITIZEN);

        try {
            userDao.storeUser(u);
            return Response.status(200).build();
        } catch (Exception e) {
            return ResponseFactory.createErrorResponse(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

}
