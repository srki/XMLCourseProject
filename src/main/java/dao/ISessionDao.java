package dao;

import javax.xml.bind.JAXBException;

public interface ISessionDao extends IAbstractDao {

    void storeSession(String raw) throws Exception;

    void storeSessionResults(String raw, String uri) throws Exception;

    String getAllSessions(String status) throws JAXBException;

}
