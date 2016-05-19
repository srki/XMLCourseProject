package rest.interceptors;

import com.marklogic.client.ResourceNotFoundException;
import rest.response.ResponseFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author - Srđan Milaković
 */
@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
    @Override
    public Response toResponse(ResourceNotFoundException e) {
        return ResponseFactory.createErrorResponse(Response.Status.NOT_FOUND, "Resource did not found.");
    }
}
