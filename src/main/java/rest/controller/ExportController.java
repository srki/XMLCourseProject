package rest.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.marklogic.client.ResourceNotFoundException;
import dao.IActDao;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author - Srđan Milaković
 */
@Path("/export")
public class ExportController {

    private static final String ACT_XSL_PATH = "xsl/acts.xsl";
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
        return documentToHtml(actDao.getDocument(uuid), ACT_XSL_PATH);
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

        return documentToHtml(document, ACT_XSL_PATH);
    }

    @GET
    @Path("/pdf/{uuid}")
    @Produces("application/pdf")
    //@RolesAllowed({User.CITIZEN, User.REPRESENTATIVE, User.PRESIDENT})
    public Object getPdf(@PathParam("uuid") String uuid) throws Exception {
        return htmlToPdf(documentToHtml(actDao.getDocument(uuid), ACT_XSL_PATH));
    }

    private String documentToHtml(Document document, String xslPath) throws IOException, TransformerException {
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

    private Object htmlToPdf(String html) throws DocumentException, IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outStream);
        document.open();

        //FontFactory.register("/fonts/Calibri.ttf");
        InputStream stream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, stream, StandardCharsets.UTF_8);

        document.close();

        return outStream.toByteArray();
    }

}
