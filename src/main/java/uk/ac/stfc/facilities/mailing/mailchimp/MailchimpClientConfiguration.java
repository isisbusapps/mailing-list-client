package uk.ac.stfc.facilities.mailing.mailchimp;

/**
 * Configuration for the Mailchimp client.
 */
public class MailchimpClientConfiguration {
    private static final String SCHEME = "https";
    private static final int PORT = 443;
    private static final String MAILCHIMP_API_VERSION_URI = "/3.0/";
    private final String apiKey;
    private final String baseUri;
    private final String host;

    /**
     * Creates the configuration from the Mailchimp API key which
     * contains all of the connection information for Mailchimp.
     *
     * @param apiKey the API key for Mailchimp.
     */
    public MailchimpClientConfiguration(String apiKey,String host) {
        this.apiKey = apiKey;
        this.host = host;
        this.baseUri = SCHEME + "://" + this.host + MAILCHIMP_API_VERSION_URI;
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
