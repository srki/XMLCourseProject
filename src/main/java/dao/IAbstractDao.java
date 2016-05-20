package dao;

import org.w3c.dom.Document;

import java.io.IOException;

interface IAbstractDao {

    String getCollectionName();
    String getDirectoryName();

    void createDocument(String documentName, Document document) throws IOException;
    void deleteDocument(String documentName);

    Document getDocument(String documentName);
    String getDocumentAsString(String documentName);
}
