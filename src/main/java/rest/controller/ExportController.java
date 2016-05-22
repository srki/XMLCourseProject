package rest.controller;

import model.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author - Srđan Milaković
 */
@Path("/export")
public class ExportController {

    @GET
    @Path("/html")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed({User.CITIZEN, User.REPRESENTATIVE, User.PRESIDENT})
    public Object getHtml() {
        return null;
    }

    @GET
    @Path("/html-edit")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed({User.REPRESENTATIVE})
    public Object getEditableHtml() {
        return null;
    }

    @GET
    @Path("/pdf")
    @Produces("application/pdf")
    @RolesAllowed({User.CITIZEN, User.REPRESENTATIVE, User.PRESIDENT})
    public Object getPdf() {
        return null;
    }

}
