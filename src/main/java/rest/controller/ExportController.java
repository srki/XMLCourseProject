package rest.controller;

import com.marklogic.client.ResourceNotFoundException;
import dao.IActDao;
import model.User;
import net.sf.saxon.TransformerFactoryImpl;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;

/**
 * @author - Srđan Milaković
 */
@Path("/export")
public class ExportController {

    private static final String ACT_XSL_PATH = "xsl/acts.xsl";
    private static final String ACT_XSL_FO_PATH = "xsl/acts-fo.xsl";
    private static final String FOP_CFG = "fop.xconf";
    private static final String ARTICLE_TAG_NAME = "article";
    private static final String ID_ATTRIBUTE_NAME = "id";
    private static final String MODIFY_ATTRIBUTE_NAME = "modify";
    private static final String URL_FORMAT = "/acts/%s/%s";

    @EJB
    private IActDao actDao;

    @GET
    @Path("/html/{uuid}")
    @Produces(MediaType.TEXT_HTML)
    //@RolesAllowed({User.CITIZEN, User.REPRESENTATIVE, User.PRESIDENT})
    public Object getHtml(@PathParam("uuid") String uuid) throws IOException, TransformerException {
        return exportDocumentToHtml(actDao.getDocument(uuid), ACT_XSL_PATH);
    }

    @GET
    @Path("/html-edit/{uuid}")
    @Produces(MediaType.TEXT_HTML)
    //@RolesAllowed({User.REPRESENTATIVE})
    public Object getEditableHtml(@PathParam("uuid") String uuid) throws IOException, TransformerException {
        Document document = actDao.getDocument(uuid);

        NodeList articles = document.getElementsByTagName(ARTICLE_TAG_NAME);
        for (int i = 0; i < articles.getLength(); i++) {
            Element article = ((Element) articles.item(i));
            String href = String.format(URL_FORMAT, uuid, article.getAttribute(ID_ATTRIBUTE_NAME));
            article.setAttribute(MODIFY_ATTRIBUTE_NAME, href);
        }

        return exportDocumentToHtml(document, ACT_XSL_PATH);
    }

    @GET
    @Path("/pdf/{uuid}")
    @Produces("application/pdf")
    @RolesAllowed({User.CITIZEN, User.REPRESENTATIVE, User.PRESIDENT})
    public Object getPdf(@PathParam("uuid") String uuid) throws Exception {
        return exportDocumentToPdf(actDao.getDocument(uuid), ACT_XSL_FO_PATH);
    }

    private String exportDocumentToHtml(Document document, String xslPath) throws IOException, TransformerException {
        URL url = ExportController.class.getClassLoader().getResource(xslPath);
        if (url == null) {
            throw new ResourceNotFoundException("Can not find xsl file");
        }

        StreamSource streamSource = new StreamSource(url.openStream());
        Transformer transformer = TransformerFactory.newInstance().newTransformer(streamSource);
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));

        return writer.getBuffer().toString();
    }

    private Object exportDocumentToPdf(Document document, String xslPath) throws Exception {
        URL url = ExportController.class.getClassLoader().getResource(xslPath);
        if (url == null) {
            throw new ResourceNotFoundException("Can not find xsl file");
        }

        URL urlConf = ExportController.class.getClassLoader().getResource(FOP_CFG);
        if (urlConf == null) {
            throw new ResourceNotFoundException("Can not find xconf file");
        }

        // Initialize FOP factory object
        FopFactory fopFactory = FopFactory.newInstance(urlConf.toURI());

        // Setup the XSLT transformer factory
        TransformerFactory transformerFactory = new TransformerFactoryImpl();

        // Point to the XSL-FO file
        File xsltFile = new File(ACT_XSL_FO_PATH);

        // Create transformation source
        StreamSource transformSource = new StreamSource(url.openStream());

        // Initialize user agent needed for the transformation
        FOUserAgent userAgent = fopFactory.newFOUserAgent();

        // Create the output stream to store the results
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        // Initialize the XSL-FO transformer object
        Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);

        // Construct FOP instance with desired output format
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);

        // Resulting SAX events
        Result res = new SAXResult(fop.getDefaultHandler());

        // Start XSLT transformation and FOP processing
        xslFoTransformer.transform(new DOMSource(document), res);

        // Generate PDF file
        return outStream.toByteArray();
    }

}
