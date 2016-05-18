package dao;


import model.User;
import model.Users;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface IUserDao extends IAbstractDao {

    User getUser(String username) throws JAXBException;
    Users getUsers() throws JAXBException;
    Users getUsersByType(String type) throws JAXBException;
    void storeUser(User u) throws Exception;

}
