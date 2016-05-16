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
            "return $x\n";

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
    public String getDirectoryName() {
        return "/xml/users/";
    }
}
