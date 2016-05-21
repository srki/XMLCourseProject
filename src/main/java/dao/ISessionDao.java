package dao;

import model.Sessions;

import javax.xml.bind.JAXBException;

public interface ISessionDao extends IAbstractDao {

    void storeSession(String raw) throws Exception;

    Sessions getAllSessions() throws JAXBException;

    Sessions getFinishedSessions() throws JAXBException;

    Sessions getUpcomingSessions() throws JAXBException;
}
