package dao;

import com.marklogic.client.eval.ServerEvaluationCall;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(IActDao.class)
public class ActDao extends AbstractDao implements IActDao {

    private static final String addActQuery = getScriptContent("addActQuery.xq");

    private static final String getAllActs = getScriptContent("getAllActs.xq");

    @Override
    public void storeAct(String raw) throws Exception {

        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(addActQuery);
        call.addVariable("act_string", raw);

        String result = call.evalAs(String.class);

        if (result!= null && result.equals("NOT OK"))
            throw new Exception("Failed to store the act");
    }

    @Override
    public String getAllActs() {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(getAllActs);

        return call.evalAs(String.class);
    }

    @Override
    public String getDirectoryName() {
        return "/xml/acts/";
    }

}
