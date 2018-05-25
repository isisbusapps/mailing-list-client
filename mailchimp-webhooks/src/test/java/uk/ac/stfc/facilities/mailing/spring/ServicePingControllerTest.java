package uk.ac.stfc.facilities.mailing.spring;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicePingControllerTest {

    private static final String URL = "https://example.com/service";

    private CloseableHttpClient mockedClient;
    private ServicePingController controller;

    @BeforeEach
    public void setup() {

        mockedClient = mock(CloseableHttpClient.class);

        controller = new ServicePingController(
                mockedClient,
                URL
        );
    }

    @Test
    public void getStatus_unavailable_availableFalse() throws IOException {

        when(mockedClient.execute(argThat(a -> a.getURI().equals(URI.create(URL)))))
                .thenThrow(IOException.class);

        ServicePingController.StatusResponse response = controller.getStatus();

        assertThat(response).isNotNull();
        assertThat(response.getAvailable())
                .isEqualTo(false);
    }

    @Test
    public void getStatus_statusCodeNot200_availableFalse() throws IOException {

        CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
        StatusLine mockedStatusLine = mock(StatusLine.class);

        when(mockedClient.execute(argThat(a -> a.getURI().equals(URI.create(URL)))))
                .thenReturn(httpResponse);

        when(httpResponse.getStatusLine()).thenReturn(mockedStatusLine);

        when(mockedStatusLine.getStatusCode()).thenReturn(404);

        ServicePingController.StatusResponse response = controller.getStatus();

        assertThat(response).isNotNull();
        assertThat(response.getAvailable())
                .isEqualTo(false);
    }

    @Test
    public void getStatus_statusCode200_availableTrue() throws IOException {

        CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
        StatusLine mockedStatusLine = mock(StatusLine.class);

        when(mockedClient.execute(argThat(a -> a.getURI().equals(URI.create(URL)))))
                .thenReturn(httpResponse);

        when(httpResponse.getStatusLine()).thenReturn(mockedStatusLine);

        when(mockedStatusLine.getStatusCode()).thenReturn(200);

        ServicePingController.StatusResponse response = controller.getStatus();

        assertThat(response).isNotNull();
        assertThat(response.getAvailable())
                .isEqualTo(true);

    }

}
