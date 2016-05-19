package dao;

import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import database.ISchemaDatabaseManager;
import org.w3c.dom.Document;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * @author - Srđan Milaković
 */
@Local(ISchemasDao.class)
@Stateless
public class SchemasDao implements ISchemasDao {

    private static final String DIRECTORY = "/xml/";

    @EJB
    private ISchemaDatabaseManager schemaDatabaseManager;

    @Override
    public String getSchema(String schemaName) {
        try {
            DOMSource domSource = new DOMSource(getSchemaDocument(schemaName));
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Document getSchemaDocument(String schemaName) {
        XMLDocumentManager manager = schemaDatabaseManager.getSchemaDatabaseClient().newXMLDocumentManager();
        DOMHandle handle = new DOMHandle();
        manager.read(DIRECTORY + schemaName, handle);
        return handle.get();
    }

}
