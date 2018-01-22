package uk.ac.stfc.facilities.mailing.spring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import uk.ac.stfc.facilities.mailing.spring.environment.config.BisAppSettingsPropertySource;
import uk.stfc.bisapps.config.BISAppProperties;

/**
 * Spring initialiser that adds the custom <code?BISAppSettings</code>
 * property source to the environment.
 */
public class MailchimpApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        String bisAppSettingsFilePath = environment.getProperty("config.bisappsettings.path");

        environment.getPropertySources().addFirst(new BisAppSettingsPropertySource(
                "BIS app settings property source",
                new BISAppProperties(bisAppSettingsFilePath)));
    }
}
