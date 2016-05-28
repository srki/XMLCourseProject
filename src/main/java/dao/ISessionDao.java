package dao;

import javax.xml.bind.JAXBException;

public interface ISessionDao extends IAbstractDao {

    void storeSession(String raw) throws Exception;

    String getAllSessions(String status) throws JAXBException;



}
