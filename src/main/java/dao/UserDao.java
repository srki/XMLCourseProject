package dao;


import com.marklogic.client.eval.ServerEvaluationCall;
import model.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

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
