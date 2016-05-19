package dao;

import com.marklogic.client.eval.ServerEvaluationCall;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(IActDao.class)
public class ActDao extends AbstractDao implements IActDao {

    private static final String addActQuery = "import schema 'http://ftn.uns.ac.rs/xml' at '/xml/acts.xsd';" +
            "declare namespace mlt = 'http://ftn.uns.ac.rs/xml';" +
            "declare variable $act_string as xs:string external;" +
            "declare variable $act := xdmp:unquote($act_string);" +
            "declare variable $errors := xdmp:validate($act, 'strict');" +
            "declare variable $validation_error := if (exists($errors//error:error)) then 'NOT OK' else 'OK';" +
            "declare variable $is_not_act_error := if (name($act/mlt:act) eq 'act') then 'OK' else 'NOT OK';" +
            "declare variable $less_articles := if(count($act//mlt:article) le 2) then 'NOT OK 3' else 'OK';" +
            "if ($validation_error eq 'OK' and $is_not_act_error eq 'OK' and $less_articles eq 'OK') then " +
            "  try {" +
            "    if (empty(xdmp:document-insert('/xml/acts/' || sem:uuid-string() || '.xml', $act, xdmp:default-permissions(), 'xml.acts'))) then 'OK' else 'NOT OK'" +
            "  }" +
            "  catch($exception){" +
            "    'NOT OK'" +
            "  }" +
            "else 'NOT OK';";
    
    private static final String getAllDocuments = "declare namespace mlt = 'http://ftn.uns.ac.rs/xml';" +
            "<acts>" +
            "{" +
            "  for $x in cts:uris((), (), cts:directory-query('/xml/acts/','infinity')) " +
            "  return " +
            "  <act>" +
            "    <uri>{$x}</uri>" +
            "    <title>{data(doc($x)/mlt:act/@title)}</title>" +
            "  </act>" +
            "}" +
            "</acts>";

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

        call.xquery(getAllDocuments);

        return call.evalAs(String.class);
    }

    @Override
    public String getDirectoryName() {
        return "/xml/acts/";
    }

}
