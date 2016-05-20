package rest.response;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author - Srđan Milaković
 */
public class ResponseFactory {

    public static final String CONTENT_TYPE = "content-type";


    public static Response createResponse(Response.Status status, String xmlString) {
        return Response.status(status).entity(xmlString)
                .header(CONTENT_TYPE, MediaType.APPLICATION_XML).build();
    }

    public static Response createResponse(Response.Status status, AbstractResponse<?> response) {
        return createResponse(status, response.toString());
    }

    public static Response createNoContentResponse() {
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    public static Response createErrorResponse(Response.Status status, String message) {
        if (message == null) {
            message = status.toString();
        }
        return createResponse(status, new ErrorResponse(message));
    }

    public static Response createErrorResponse(Response.Status status) {
        return createErrorResponse(status, null);
    }
}
