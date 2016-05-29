package dao;

import javax.xml.xpath.XPath;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;

interface IAbstractDao {

    String getCollectionName();
    String getDirectoryName();

    void createDocument(String documentName, Document document) throws IOException;
    void deleteDocument(String documentName);

    Document getDocumentFromString(String xml) throws Exception;
    Document getDocument(String documentName);
    String getDocumentAsString(String documentName);
    String getDocumentAsString(Document document);

    XPath getXPathWithNamespace(String namespace);
}
