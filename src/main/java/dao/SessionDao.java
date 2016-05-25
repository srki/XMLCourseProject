package dao;

import com.marklogic.client.eval.ServerEvaluationCall;
import model.Sessions;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.NotFoundException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Date;

@Stateless
@Local(ISessionDao.class)
public class SessionDao extends AbstractDao implements ISessionDao {

    private static final String addSessionQuery = getScriptContent("addSessionQuery.xq");

    private static final String getAllSessionsQuery = getScriptContent("getAllSessions.xq");

    @Override
    public String getDirectoryName() {
        return "/xml/sessions/";
    }

    @Override
    public void storeSession(String raw) throws Exception {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(addSessionQuery);
        call.addVariable("session_string", raw);

        String result = call.evalAs(String.class);

        if (result != null && result.equals("NOT OK"))
            throw new Exception("Failed to store the session");
    }

    @Override
    public String getAllSessions(String status) throws JAXBException {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(getAllSessionsQuery);
        call.addVariable("status", status);

        String raw = call.evalAs(String.class);

        if (raw == null)
            throw new NotFoundException();

        return raw;
    }
}
