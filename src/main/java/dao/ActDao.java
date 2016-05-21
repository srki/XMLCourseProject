package dao;

import com.marklogic.client.eval.ServerEvaluationCall;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;

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

        if (result != null && result.equals("NOT OK"))
            throw new Exception("Failed to store the act");
    }

    @Override
    public String getAllActs(String text, String title, String country, String region,
                             String establishment, String startDate, String endDate, String city, String serial) {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        call.xquery(getAllActs);

        call.addVariable("text", text == null ? "" : text);
        call.addVariable("title", title == null ? "" : title);
        call.addVariable("country", country == null ? "" : country);
        call.addVariable("region", region == null ? "" : region);
        call.addVariable("establishment", establishment == null ? "" : establishment);
        call.addVariable("city", city == null ? "" : city);
        call.addVariable("serial", serial == null ? "" : serial);
        call.addVariable("start_date", startDate == null ? "" : format.format(startDate));
        call.addVariable("end_date", endDate == null ? "" : format.format(endDate));

        return call.evalAs(String.class);
    }

    @Override
    public String getDirectoryName() {
        return "/xml/acts/";
    }

}
