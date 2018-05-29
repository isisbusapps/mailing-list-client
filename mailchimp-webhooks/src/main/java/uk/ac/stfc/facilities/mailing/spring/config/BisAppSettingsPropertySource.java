package uk.ac.stfc.facilities.mailing.spring.config;

import org.springframework.core.env.PropertySource;
import uk.stfc.bisapps.config.BISAppProperties;

/**
 * A property source that can be used by Spring so that variables can
 * be injected using <a href="https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Value.html">
 * Spring's <code>@Value</code></a>.
 */
public class BisAppSettingsPropertySource extends PropertySource<BISAppProperties> {

    /**
     * Creates a Spring property source with the given name and
     * underlying property source.
     *
     * @param name   the name of the property source
     * @param source the underlying property source
     */
    public BisAppSettingsPropertySource(
            String name,
            BISAppProperties source) {
        super(name, source);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Object getProperty(String s) {
        return source.getProperty(s);
    }
}
