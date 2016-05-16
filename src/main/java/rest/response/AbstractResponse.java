package rest.response;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;

public class AbstractResponse<T> {

    private Class<T> entityType;

    @SuppressWarnings("unchecked")
    AbstractResponse() {
        entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
