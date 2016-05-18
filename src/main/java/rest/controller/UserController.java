package rest.controller;

import dao.IUserDao;
import model.User;
import model.Users;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserController {

    @EJB
    IUserDao userDao;

    @GET
    public Object getUsers() {
        try {
            Users users = userDao.getUsers();
            return Response.status(200).entity(users.toString()).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/{username}")
    public Object getUser(@PathParam("username") String username) {
        try {
            User u = userDao.getUser(username);
            return Response.status(200).entity(u.toString()).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }
}
