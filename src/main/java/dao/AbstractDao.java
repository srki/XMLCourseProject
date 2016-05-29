package dao;


import com.marklogic.client.document.GenericDocumentManager;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import database.IDatabaseManager;
import javax.xml.xpath.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.NotFoundException;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.URL;
import java.util.Iterator;

@Stateless
@Local(IAbstractDao.class)
public abstract class AbstractDao implements IAbstractDao {

    @EJB
    protected IDatabaseManager databaseManager;

    protected static String getScriptContent(String fileName) {
        ClassLoader classLoader = AbstractDao.class.getClassLoader();
        URL url = classLoader.getResource("scripts/" + fileName);

        if (url == null) {
            return null;
        }

        StringBuilder result = new StringBuilder("");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

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

        return getDocumentAsString(getDocument(documentName));
    }

    @Override
    public String getDocumentAsString(Document document) {
        try {

            DOMSource domSource = new DOMSource(document);
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
    public Document getDocumentFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }

    @Override
    public String getCollectionName() {
        return null;
    }

    @Override
    public XPath getXPathWithNamespace(String namespace) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        xPath.setNamespaceContext(new NamespaceContext() {
            public String getNamespaceURI(String prefix) {
                if (prefix == null) throw new NullPointerException("Null prefix");
                else if ("mlt".equals(prefix)) return namespace;
                else if ("xml".equals(prefix)) return XMLConstants.XML_NS_URI;
                return XMLConstants.NULL_NS_URI;
            }

            public String getPrefix(String uri) {
                throw new UnsupportedOperationException();
            }

            public Iterator getPrefixes(String uri) {
                throw new UnsupportedOperationException();
            }
        });

        return xPath;
    }
}
