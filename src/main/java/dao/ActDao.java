package dao;

import com.marklogic.client.eval.ServerEvaluationCall;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.NotFoundException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Stateless
@Local(IActDao.class)
public class ActDao extends AbstractDao implements IActDao {

    private static final String addActQuery = getScriptContent("addActQuery.xq");

    private static final String getAllActs = getScriptContent("getAllActs.xq");

    private static final String getArticle = getScriptContent("getArticle.xq");

    @Override
    public void storeAct(String raw) throws Exception {

        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(addActQuery);
        call.addVariable("act_string", generateIds(raw));

        String result = call.evalAs(String.class);

        if (result != null && result.equals("NOT OK"))
            throw new Exception("Failed to store the act");
    }

    public String generateIds(String rawString) throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(rawString));

        Document doc = db.parse(is);

        NodeList list = doc.getElementsByTagName("article");
        for(int i=0; i<list.getLength(); i++) {
            ((Element)list.item(i)).setAttribute("id", Integer.toString(i));
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.getBuffer().toString().replaceAll("\n|\r", "");
    }

    @Override
    public String getAllActs(String text, String title, String country, String region, String establishment,
                             Long startDate, Long endDate, String city, String serial, String status) {
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
        call.addVariable("start_date", startDate == null ? "" : format.format(new Date(startDate)));
        call.addVariable("end_date", endDate == null ? "" : format.format(new Date(endDate)));
        call.addVariable("status", status == null ? "" : status);

        return call.evalAs(String.class);
    }

    @Override
    public String getArticle(String uri, String id, String format) {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(getArticle);
        call.addVariable("act_uri", uri);
        call.addVariable("article_id", id);
        call.addVariable("format", format != null ? format : "article");

        String val = call.evalAs(String.class);

        if (val == null)
            throw new NotFoundException();

        return val;
    }

    @Override
    public String getDirectoryName() {
        return "/xml/acts/";
    }

}
