package dao;

import com.marklogic.client.eval.ServerEvaluationCall;
import org.w3c.dom.Document;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by ragnar on 14.6.16..
 */

@Stateless
@Local(ISystemStatusDao.class)
public class SystemStatusDao extends AbstractDao implements ISystemStatusDao {

    private static final String addAmendmentQuery = getScriptContent("updateStatus.xq");

    @Override
    public String getDirectoryName() {
        return "/xml/systemStatus/";
    }

    @Override
    public void updateStatus(String status) throws Exception {
        Document doc = getDocument(documentName);
        doc.getElementsByTagName("status").item(0).setTextContent(status);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        String output = writer.getBuffer().toString().replaceAll("\n|\r", "");

        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();

        call.xquery(addAmendmentQuery);
        call.addVariable("systemStatus_string", output);

        String result = call.evalAs(String.class);

        if (result != null && result.equals("NOT OK"))
            throw new Exception("Failed to update status");
    }
}
