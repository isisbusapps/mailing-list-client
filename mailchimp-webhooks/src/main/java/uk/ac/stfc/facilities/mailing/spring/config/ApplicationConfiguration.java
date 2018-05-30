package uk.ac.stfc.facilities.mailing.spring.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    /**
     * We only want one HTTP client for the whole application as the
     * connections are pooled.
     *
     * @see <a href="https://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html">Apache HttpClient Fundamentals (section 1.2.1.)</a>
     *
     * @return the HttpClient for the application
     */
    @Bean
    public CloseableHttpClient client() {
        return HttpClients.createDefault();
    }

}
