package rest.response;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;

public class AbstractResponse<T> implements Serializable {

    public static final String CONTENT_TYPE = "content-type";

    private Class<T> entityType;

    @SuppressWarnings("unchecked")
    AbstractResponse() {
        entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public static Response createResponse(Response.Status status, String xmlString) {
        return Response.status(status).entity(xmlString)
                .header(CONTENT_TYPE, MediaType.APPLICATION_XML).build();
    }

    public static Response createResponse(Response.Status status, AbstractResponse<?> response) {
        return createResponse(status, response.toString());
    }

    public static Response createErrorResponse(Response.Status status, String message) {
        return createResponse(status, new ErrorResponse(message));
    }

    @Override
    public String toString() {

        try {
            JAXBContext context = JAXBContext.newInstance(entityType);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            m.marshal(this, sw);

            return sw.toString();
        }
        catch (Exception e) {
            return "";
        }
    }
}
