package uk.ac.stfc.facilities.mailing.spring.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public CloseableHttpClient client() {
        return HttpClients.createDefault();
    }

}
