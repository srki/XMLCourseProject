package dao;

import org.w3c.dom.Document;

import java.io.IOException;

interface IAbstractDao {

    void createDocument(String documentName, Document document) throws IOException;
    void deleteDocument(String documentName);

    Document getDocument(String documentName);
}
