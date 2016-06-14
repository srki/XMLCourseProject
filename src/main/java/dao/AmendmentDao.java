package dao;

import com.marklogic.client.eval.ServerEvaluationCall;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(IAmendmentDao.class)
public class AmendmentDao extends AbstractDao implements IAmendmentDao {

    private static final String addAmendmentQuery = getScriptContent("addAmendment.xq");
    private static final String getAllAmendmentsQuery = getScriptContent("getAllAmendments.xq");

    @Override
    public String getDirectoryName() {
        return "/xml/amendments/";
    }

    @Override
    public void storeAmendment(String raw, String username) throws Exception {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(addAmendmentQuery);
        call.addVariable("amendment_string", raw);
        call.addVariable("username", username);

        String result = call.evalAs(String.class);

        if (result != null && result.equals("NOT OK"))
            throw new Exception("Failed to store the amendment");
    }

    @Override
    public String getAllAmendments(String actUri, String status, String username) {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(getAllAmendmentsQuery);
        call.addVariable("act_uri", actUri == null ? "" : actUri);
        call.addVariable("status", status == null ? "" : status);
        call.addVariable("username", username == null ? "" : username);

        return call.evalAs(String.class);
    }

    @Override
    public void deleteAmendments(String uri, String username) {

    }

}
