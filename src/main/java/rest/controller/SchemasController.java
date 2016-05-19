package rest.controller;

import dao.ISchemasDao;
import model.User;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author - Srđan Milaković
 */
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
@Path("/schemas")
public class SchemasController {

    @EJB
    private ISchemasDao schemasDao;

    @GET
    @Path("/{name}")
    @RolesAllowed({User.REPRESENTATIVE, User.PRESIDENT})
    public Object getSchema(@PathParam("name") String name) {
        return schemasDao.getSchema(name);
    }
}
