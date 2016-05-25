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
        return "/xml/amendment/";
    }

    @Override
    public void storeAmendment(String raw) throws Exception {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(addAmendmentQuery);
        call.addVariable("amendment_string", raw);

        String result = call.evalAs(String.class);

        if (result != null && result.equals("NOT OK"))
            throw new Exception("Failed to store the amendment");
    }

    @Override
    public String getAllAmendments(String actUri, String status) {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(getAllAmendmentsQuery);
        call.addVariable("act_uri", actUri == null ? "" : actUri);
        call.addVariable("status", status == null ? "" : status);

        return call.evalAs(String.class);
    }
}
