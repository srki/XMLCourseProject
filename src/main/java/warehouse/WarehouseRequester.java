package warehouse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * @author - Srđan Milaković
 */
public class WarehouseRequester {
    private String destinationAddress;

    public WarehouseRequester() {
        this("http://127.0.0.1:8081");
    }

    public WarehouseRequester(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void archiveAct(String uri, String xml) {
        String url = destinationAddress + "/api/acts";
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost postRequest = new HttpPost(url);
            postRequest.setEntity(new StringEntity("{\"uri\":\"" + uri + "\", \"xml\":\"" + xml + "\"}"));
            postRequest.setHeader("Content-type", "application/json");
            httpClient.execute(postRequest);
        } catch (IOException e) {
            System.err.println("Can't send a request: " + url);
        }
    }
}
