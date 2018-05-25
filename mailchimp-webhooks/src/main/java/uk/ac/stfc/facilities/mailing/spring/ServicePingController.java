package uk.ac.stfc.facilities.mailing.spring;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Provides high level monitoring information about whether the service is available.
 */
@Controller
public class ServicePingController {

    private final String url;

    private final CloseableHttpClient client;

    public ServicePingController(
            @Autowired CloseableHttpClient client,
            @Value("${UserOfficeWebServiceURL}") String url) {
        this.url = url;
        this.client = client;
    }

    @GetMapping("/status")
    @ResponseBody
    public StatusResponse getStatus() {
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpResponse response = client.execute(httpGet)) {

            boolean available = response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;

            StatusResponse statusResponse = new StatusResponse(available);

            EntityUtils.consume(response.getEntity());

            return statusResponse;
        } catch (IOException e) {
            return new StatusResponse(false);
        }
    }

    public static class StatusResponse {
        private final boolean available;

        public StatusResponse(boolean available) {
            this.available = available;
        }

        public boolean getAvailable() {
            return available;
        }
    }
}
