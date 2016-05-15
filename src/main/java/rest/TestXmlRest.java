package rest;


import database.IDatabaseManager;
import rest.response.TestXML;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/test")
@Produces(MediaType.APPLICATION_XML)
public class TestXmlRest {

    @EJB
    IDatabaseManager databaseManager;

    @GET
    public Object get() {

        TestXML t = new TestXML();

        t.setTestInt(10);
        t.setTestString("Ovo je test");

        databaseManager.printStuff();

        return Response
                .status(Response.Status.CREATED)
                .entity(t)
                .build();
    }

}
