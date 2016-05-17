package dao;


import model.User;

import javax.xml.bind.JAXBException;

public interface IUserDao extends IAbstractDao {

    User getUser(String username) throws JAXBException;
    void storeUser(User u) throws Exception;

}
