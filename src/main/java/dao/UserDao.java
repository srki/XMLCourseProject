package dao;


import com.marklogic.client.eval.ServerEvaluationCall;
import model.User;
import model.Users;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.core.Link;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local(IUserDao.class)
public class UserDao extends AbstractDao implements IUserDao {

    private static final String getUserQuery = "declare namespace mlt = 'http://ftn.uns.ac.rs/xml'; " +
            "declare variable $username as xs:token external;" +
            "for $x in doc('/xml/users/xml-user.xml')/mlt:users/mlt:user " +
            "where $x/mlt:username=$username " +
            "return $x";

    private static final String addUserQuery = "declare namespace mlt = 'http://ftn.uns.ac.rs/xml';" +
            "declare variable $user as xs:string external;" +
            "xdmp:node-insert-child(doc('/xml/users/xml-user.xml')/mlt:users, xdmp:unquote($user)/mlt:user);";

    private static final String getUsersQuery = "declare namespace mlt = 'http://ftn.uns.ac.rs/xml';" +
            "fn:doc('/xml/users/xml-user.xml')/mlt:users";

    private static final String getUsersByTypeQuery = "declare namespace mlt = 'http://ftn.uns.ac.rs/xml'; " +
            "declare variable $type as xs:token external;" +
            "for $x in doc('/xml/users/xml-user.xml')/mlt:users/mlt:user " +
            "where $x/mlt:username=$type " +
            "return $x";

    @Override
    public User getUser(String username) throws JAXBException {

        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(getUserQuery);
        call.addVariable("username", username);

        String raw = call.evalAs(String.class);

        JAXBContext context = JAXBContext.newInstance(User.class);
        Unmarshaller um = context.createUnmarshaller();

        return raw != null ? (User) um.unmarshal(new StringReader(raw)) : null;
    }

    @Override
    public Users getUsers() throws JAXBException {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(getUsersQuery);

        String raw = call.evalAs(String.class);

        JAXBContext context = JAXBContext.newInstance(Users.class);
        Unmarshaller um = context.createUnmarshaller();

        return (raw != null) ? (Users) um.unmarshal(new StringReader(raw)) : null;
    }

    @Override
    public Users getUsersByType(String type) throws JAXBException {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(getUsersQuery);

        String raw = call.evalAs(String.class);

        JAXBContext context = JAXBContext.newInstance(Users.class);
        Unmarshaller um = context.createUnmarshaller();

        Users users = (Users) um.unmarshal(new StringReader(raw));
        Users selectedUsers = new Users();

        for(User u : users.getUser()) {
            if(u.getType().equals(type)) {
                selectedUsers.addUser(u);
            }
        }

        return selectedUsers;
    }

    @Override
    public void storeUser(User u) throws Exception {

        if (getUser(u.getUsername()) != null) {
            throw new Exception("User already exists!");
        }

        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();
        call.xquery(addUserQuery);

        String userString = u.toString();
        if(userString.equals(""))
            throw new Exception("Wrong data!");

        call.addVariable("user", userString);
        call.eval();
    }

    @Override
    public String getDirectoryName() {
        return "/xml/users/";
    }

}
