package rest;


import rest.response.TestXML;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/test")
@Produces(MediaType.APPLICATION_XML)
public class TestXmlRest {

    @GET
    public Object get() {

        TestXML t = new TestXML();

        t.setTestInt(10);
        t.setTestString("Ovo je test");

        return Response
                .status(Response.Status.CREATED)
                .entity(t)
                .build();
    }

}
