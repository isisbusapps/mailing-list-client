package uk.ac.stfc.facilities.mailing.spring.environment.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import uk.stfc.bisapps.config.BISAppProperties;

/**
 * Provides required {@link BisAppSettingsPropertySource}.
 */
@Configuration
public class BisAppSettingsPropertySourceConfig implements InitializingBean {

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @Value("${config.bisappsettings.path}")
    private String bisAppSettingsFilePath;

    /**
     * Uses <code>IniitializingBean</code> to inject a
     * {@link BisAppSettingsPropertySource}.
     *
     * @inheritDoc
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        BisAppSettingsPropertySource propertySource = new BisAppSettingsPropertySource(
                "BIS app settings property source",
                new BISAppProperties(bisAppSettingsFilePath));
        MutablePropertySources sources = configurableEnvironment.getPropertySources();
        sources.addFirst(propertySource);
    }
}
