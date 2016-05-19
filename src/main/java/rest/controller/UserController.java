package rest.controller;

import dao.IUserDao;
import model.User;
import model.Users;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class UserController {

    @EJB
    IUserDao userDao;

    @GET
    @RolesAllowed({User.REPRESENTATIVE, User.CITIZEN})
    public Object getUsers(@QueryParam("type") String type) {
        try {
            if(type == null || "".equals(type)) {
                Users users = userDao.getUsers();
                return Response.status(200).entity(users).build();
            } else {
                Users users = userDao.getUsersByType(type);
                return Response.status(200).entity(users).build();
            }
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/{username}")
    @RolesAllowed({User.REPRESENTATIVE, User.CITIZEN})
    public Object getUser(@PathParam("username") String username) {
        try {
            User u = userDao.getUser(username);
            return Response.status(200).entity(u).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }
}
