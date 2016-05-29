package dao;

import com.marklogic.client.Transaction;
import com.marklogic.client.eval.ServerEvaluationCall;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.util.LinkedList;
import java.util.List;

@Stateless
@Local(ISessionDao.class)
public class SessionDao extends AbstractDao implements ISessionDao {

    private static final String addSessionQuery = getScriptContent("addSessionQuery.xq");

    private static final String getAllSessionsQuery = getScriptContent("getAllSessions.xq");

    private static final String validateSessionQuery = getScriptContent("validateSession.xq");

    @EJB
    private IActDao actDao;

    @EJB
    private IAmendmentDao amendmentDao;

    private List<Document> getPurifiedActs(String sessionRaw) throws Exception {
        LinkedList<Document> retVal = new LinkedList<>();
        Document session = getDocumentFromString(sessionRaw);

        XPath xPath = getXPathWithNamespace("http://ftn.uns.ac.rs/xml");
        NodeList nodes = (NodeList)xPath.evaluate("//mlt:act", session, XPathConstants.NODESET);

        for(int i = 0; i < nodes.getLength(); ++i) {
            Element act = (Element) nodes.item(i);
            if (act.getAttribute("status").equals("approved_as_whole")) {
                retVal.add(getPurifiedAct(act, xPath));
            }
        }

        return retVal;
    }

    private Document getPurifiedAct(Element e, XPath xPath) throws Exception {
        Document act = actDao.getDocument(e.getAttribute("ref"));
        NodeList amendments = (NodeList)xPath.evaluate("//mlt:amendment", e, XPathConstants.NODESET);

        for(int i = 0; i < amendments.getLength(); ++i) {
            Element amendmentInfo = (Element)amendments.item(i);
            if(amendmentInfo.getAttribute("status").equals("approved")) {
                Element amendment = amendmentDao.getDocument(amendmentInfo.getAttribute("ref")).getDocumentElement();
                executeAmendmentOperation(act, amendment, xPath);
            }
        }

        return act;
    }

    private void executeAmendmentOperation(Document act, Element amendment, XPath xPath) throws Exception {
        String articleId = amendment.getAttribute("articleId");
        Element article = (Element)xPath.evaluate("//mlt:article[@id='" + articleId + "']", act, XPathConstants.NODE);

        if(amendment.getAttribute("operation").equals("delete")) {
            article.getParentNode().removeChild(article);
        } else {
            Element newArticle = (Element)xPath.evaluate("//mlt:article[@id='" + articleId + "']",
                    amendment,
                    XPathConstants.NODE);

            if (amendment.getAttribute("operation").equals("update")) {
                article.getParentNode().replaceChild(act.importNode(newArticle, true), article);
            }

            if (amendment.getAttribute("operation").equals("insert")) {
                newArticle.setAttribute("id", getNextArticleId(act, xPath).toString());
                article.getParentNode().insertBefore(act.importNode(newArticle, true), article);
            }
        }
    }

    private Integer getNextArticleId(Document act, XPath xPath) throws Exception {
        NodeList ids = (NodeList)xPath.evaluate("//mlt:article/@id", act, XPathConstants.NODESET);

        Integer index = 0;
        for (int i = 0; i < ids.getLength(); ++i) {
            Integer value = Integer.parseInt(ids.item(i).getNodeValue());
            index =  value > index ? value : index;
        }

        return index;
    }

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
        if (result != null && result.equals("NOT OK")) {
            throw new Exception("Failed to store the session");
        }
    }

    @Override
    public void storeSessionResults(String raw, String uri) throws Exception {
        Transaction t = this.databaseManager.getDatabaseClient().openTransaction();
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();
        call.xquery(validateSessionQuery);
        call.addVariable("session_string", raw);

        String result = call.evalAs(String.class);
        if ((result != null && result.equals("NOT OK")) || result == null) {
            t.rollback();
            throw new BadRequestException();
        } else if(result.equals("OK")) {
            List<Document> purifiedActs = getPurifiedActs(raw);
        }

        t.commit();
    }

    @Override
    public String getAllSessions(String status) throws JAXBException {
        ServerEvaluationCall call = this.databaseManager.getDatabaseClient().newServerEval();
        call.xquery(getAllSessionsQuery);
        call.addVariable("status", status);

        String raw = call.evalAs(String.class);
        if (raw == null) {
            throw new NotFoundException();
        }

        return raw;
    }
}
