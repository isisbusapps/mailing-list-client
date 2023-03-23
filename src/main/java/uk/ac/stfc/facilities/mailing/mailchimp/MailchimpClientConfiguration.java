package uk.ac.stfc.facilities.mailing.mailchimp;

/**
 * Configuration for the Mailchimp client.
 */
public class MailchimpClientConfiguration {
    private final String apiKey;
    private final String baseUri;

    /**
     * Creates the configuration from the Mailchimp API key which
     * contains all of the connection information for Mailchimp.
     *
     * @param apiKey the API key for Mailchimp.
     * @param baseUri the Mailchimp baseUri.
     */
    public MailchimpClientConfiguration(String apiKey,String baseUri) {
        this.apiKey = apiKey;
        this.baseUri = baseUri;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUri;
    }

}
