package uk.ac.stfc.facilities.mailing.mailchimp;

/**
 * Configuration for the Mailchimp client.
 */
public class MailchimpClientConfiguration {
    private static final String SCHEME = "https";
    private static final int PORT = 443;
    private static final String MAILCHIMP_API_BASE_HOST = "https://proxy-api.facilities.rl.ac.uk/mailchimp";
    private static final String MAILCHIMP_API_VERSION_URI = "/3.0/";
    private static final String API_KEY_SPLITTER = "-";

    private final String apiKey;

    private final String baseUri;
    private final String host;

    /**
     * Creates the configuration from the Mailchimp API key which
     * contains all of the connection information for Mailchimp.
     *
     * @param apiKey the API key for Mailchimp.
     */
    public MailchimpClientConfiguration(String apiKey) {
        this.apiKey = apiKey;
        String dataCenter = apiKey.split(API_KEY_SPLITTER)[1];
        this.host = dataCenter + "." + MAILCHIMP_API_BASE_HOST;
        this.baseUri = SCHEME + "://" + host + MAILCHIMP_API_VERSION_URI;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUri;
    }

    public String getHost() {
        return host;
    }

    public String getScheme() {
        return SCHEME;
    }

    public int getPort() {
        return PORT;
    }
}
