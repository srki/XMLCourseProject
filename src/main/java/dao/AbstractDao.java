package dao;


import com.marklogic.client.document.GenericDocumentManager;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import database.IDatabaseManager;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.NotFoundException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

@Stateless
@Local(IAbstractDao.class)
public abstract class AbstractDao implements IAbstractDao {

    @EJB
    IDatabaseManager databaseManager;


    @Override
    public void createDocument(String documentName, Document document) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XMLWriter xmlWriter = new XMLWriter(outputStream, OutputFormat.createPrettyPrint());
        xmlWriter.write(document);
        xmlWriter.close();
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        InputStreamHandle handle = new InputStreamHandle(inputStream);

        XMLDocumentManager docMgr = databaseManager.getDatabaseClient().newXMLDocumentManager();

        if (getCollectionName() == null) {
            docMgr.write(getDirectoryName() + documentName, handle);
        }
        else {
            DocumentMetadataHandle metadata = new DocumentMetadataHandle();
            metadata.getCollections().addAll(getCollectionName());

            docMgr.write(getDirectoryName() + documentName, metadata, handle);
        }
    }

    @Override
    public void deleteDocument(String documentName) {

        GenericDocumentManager docMgr = databaseManager.getDatabaseClient().newDocumentManager();
        docMgr.delete(getDirectoryName() + documentName);
    }

    @Override
    public Document getDocument(String documentName) {

        XMLDocumentManager docMgr = databaseManager.getDatabaseClient().newXMLDocumentManager();
        DOMHandle handle = new DOMHandle();
        docMgr.read(getDirectoryName() + documentName, handle);

        return handle.get();
    }

    @Override
    public String getDocumentAsString(String documentName) {
        try {

            DOMSource domSource = new DOMSource(getDocument(documentName));
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            throw new NotFoundException();
        }
    }

    @Override
    public String getCollectionName() {
        return null;
    }

}
