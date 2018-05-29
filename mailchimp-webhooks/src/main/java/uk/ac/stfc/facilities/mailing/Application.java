package uk.ac.stfc.facilities.mailing;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import uk.ac.stfc.facilities.mailing.spring.config.MailchimpApplicationContextInitializer;

/**
 * Entry point for the Mailchimp webhooks application.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .initializers(new MailchimpApplicationContextInitializer())
                .run(args);
    }
}
