package dao;


import com.marklogic.client.eval.ServerEvaluationCall;
import model.User;
import model.Users;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Stateless
@Local(IUserDao.class)
public class UserDao extends AbstractDao implements IUserDao {

    private static final String getUserQuery = getScriptContent("getUserQuery.xq");

    private static final String addUserQuery = getScriptContent("addUserQuery.xq");

    private static final String getUsersQuery = getScriptContent("getUsersQuery.xq");

    private static final String getUsersByTypeQuery = getScriptContent("getUsersByTypeQuery.xq");

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
