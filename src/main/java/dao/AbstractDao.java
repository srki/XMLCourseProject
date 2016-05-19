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
import java.net.URL;
import java.util.Scanner;

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

    protected static String getScriptContent(String fileName) {

        StringBuilder result = new StringBuilder("");

        ClassLoader classLoader = AbstractDao.class.getClassLoader();
        URL url = classLoader.getResource("scripts/" + fileName);
        
        if (url == null)
            return null;

        File file = new File(url.getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }


}
