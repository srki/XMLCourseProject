package dao;

import com.marklogic.client.eval.ServerEvaluationCall;
import model.Sessions;

import javax.ejb.Local;
import javax.ejb.Stateless;
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
    public Sessions getAllSessions() throws JAXBException {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(getAllSessionsQuery);

        String raw = call.evalAs(String.class);

        JAXBContext context = JAXBContext.newInstance(Sessions.class);
        Unmarshaller um = context.createUnmarshaller();

        return (raw != null) ? (Sessions) um.unmarshal(new StringReader(raw)) : null;
    }

    @Override
    public Sessions getFinishedSessions() throws JAXBException {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(getAllSessionsQuery);

        String raw = call.evalAs(String.class);

        JAXBContext context = JAXBContext.newInstance(Sessions.class);
        Unmarshaller um = context.createUnmarshaller();

        Sessions sessions = (Sessions) um.unmarshal(new StringReader(raw));
        Sessions selectedSessions = new Sessions();

        sessions.getSessions().stream().filter(s ->(s.getEndDate() != null && s.getEndDate().before(new Date()))).forEach(selectedSessions::addSession);

        return selectedSessions;
    }

    @Override
    public Sessions getUpcomingSessions() throws JAXBException {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(getAllSessionsQuery);

        String raw = call.evalAs(String.class);

        JAXBContext context = JAXBContext.newInstance(Sessions.class);
        Unmarshaller um = context.createUnmarshaller();

        Sessions sessions = (Sessions) um.unmarshal(new StringReader(raw));
        Sessions selectedSessions = new Sessions();

        sessions.getSessions().stream().filter(s -> s.getBeginDate().after(new Date())).forEach(selectedSessions::addSession);

        return selectedSessions;
    }
}
