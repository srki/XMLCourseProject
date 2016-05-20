package rest.interceptors;

import rest.response.ResponseFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBException;

/**
 * @author - Srđan Milaković
 */
@Provider
public class JAXBExceptionMapper implements ExceptionMapper<JAXBException> {

    @Override
    public Response toResponse(JAXBException e) {
        return ResponseFactory.createErrorResponse(Response.Status.BAD_REQUEST, e.getMessage());
    }

}
