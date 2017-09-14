package uk.ac.stfc.facilities.mailing.mailchimp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.stfc.facilities.mailing.api.exceptions.MailingListClientException;
import uk.ac.stfc.facilities.mailing.api.exceptions.NotFoundMailingListClientException;
import uk.ac.stfc.facilities.mailing.mailchimp.gson.adapters.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * An internal client that handles the requests to Mailchimp, dealing
 * with the conversion to and from the requests and responses from
 * the DTOs.
 */
class MailchimpInternalHttpClient {

    /**
     * The Mailchimp username for basic authentication doesn't matter,
     * as long as some value is provided, it will be accepted. In this
     * situation, &ldquo;user&rdquo; has been used as a placeholder.
     */
    private static final String MAILCHIMP_USERNAME_PLACEHOLDER = "user";

    private static final Logger LOG = LogManager.getLogger();

    /**
     * Used to create an instance of the internal client.
     *
     * @param configuration the configuration for the Mailchimp client.
     * @return a new instance of the internal HTTP client
     */
    static MailchimpInternalHttpClient getInstance(MailchimpClientConfiguration configuration) {

        CredentialsProvider provider = new BasicCredentialsProvider();
        Credentials credentials = new UsernamePasswordCredentials(
                MAILCHIMP_USERNAME_PLACEHOLDER,
                configuration.getApiKey()
        );
        provider.setCredentials(AuthScope.ANY, credentials);

        AuthCache authCache = new BasicAuthCache();
        authCache.put(new HttpHost(configuration.getHost(), configuration.getPort(), configuration.getScheme()), new BasicScheme());

        HttpClientContext context = HttpClientContext.create();

        context.setCredentialsProvider(provider);
        context.setAuthCache(authCache);

        CloseableHttpClient client = HttpClientBuilder.create()
                .build();

        return new MailchimpInternalHttpClient(configuration, client, context);
    }

    private final CloseableHttpClient client;
    private final HttpContext context;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    private final MailchimpClientConfiguration configuration;

    /**
     * This should only be used within the internal client.
     */
    @Deprecated
    MailchimpInternalHttpClient(
            MailchimpClientConfiguration configuration,
            CloseableHttpClient client,
            HttpContext context) {
        this.configuration = configuration;
        this.client = client;
        this.context = context;
    }

    /**
     * Completes a get request for a resource, converts the response to
     * the given type.
     *
     * @param uri           the location of the resource that the
     *                      request will get from. This is relative to
     *                      the base URL for Mailchimp.
     * @param responseClass the class of the response.
     * @param <T>           the type of the response, taken from the
     *                      given class.
     * @return the response object
     * @throws MailingListClientException if the resource is unavailable
     */
    <T> T get(String uri, Class<T> responseClass) throws MailingListClientException {
        HttpGet request = new HttpGet(withBaseUrl(uri));

        return doRequest(request, responseClass);
    }

    /**
     * Completes a post request for a resource, converts the given
     * request body to JSON and the response to the given type.
     *
     * @param uri           the location of the resource that the
     *                      request will post to. This is relative to
     *                      the base URL for Mailchimp.
     * @param body          the object for the body, which will be
     *                      converted to JSON.
     * @param responseClass the class of the response.
     * @param <T>           the type of the response, taken from the
     *                      given class.
     * @return the response body converted from JSON to the correct
     * type.
     * @throws MailingListClientException if the resource is unavailable.
     */
    <T> T post(String uri, Object body, Class<T> responseClass) throws MailingListClientException {
        HttpPost request = new HttpPost(withBaseUrl(uri));
        request.setEntity(createEntity(body));

        return doRequest(request, responseClass);
    }

    /**
     * Completes a put request for a resource, converts the given
     * request body to JSON and the response to the given type.
     *
     * @param uri           the location of the resource that the
     *                      request will put to. This is relative to
     *                      the base URL for Mailchimp.
     * @param body          the object for the body, which will be
     *                      converted to JSON.
     * @param responseClass the class of the response.
     * @param <T>           the type of the response, taken from the
     *                      given class.
     * @return the response body converted from JSON to the correct
     * type.
     * @throws MailingListClientException if the resource is unavailable.
     */
    <T> T put(String uri, Object body, Class<T> responseClass) throws MailingListClientException {
        HttpPut request = new HttpPut(withBaseUrl(uri));
        request.setEntity(createEntity(body));

        return doRequest(request, responseClass);
    }

    /**
     * Completes a delete request for a resource, converts the response
     * to the given type.
     *
     * @param uri           the location of the resource that the
     *                      request will delete. This is relative to
     *                      the base URL for Mailchimp.
     * @param responseClass the class of the response
     * @param <T>           the type of the response, taken from the
     *                      given class
     * @return the response body converted from JSON to the correct
     * type.
     * @throws MailingListClientException if the resource is unavailable.
     */
    <T> T delete(String uri, Class<T> responseClass) throws MailingListClientException {
        HttpDelete request = new HttpDelete(withBaseUrl(uri));

        return doRequest(request, responseClass);
    }

    private HttpEntity createEntity(Object object) {
        String requestText = gson.toJson(object);

        return new StringEntity(requestText, "UTF-8");
    }

    private String withBaseUrl(String url) {
        return configuration.getBaseUrl() + url;
    }

    private <T> T doRequest(HttpUriRequest request, Class<T> responseClass) throws MailingListClientException {
        LOG.debug("making {} request for {}", request::getMethod, request::getURI);
        try {
            HttpResponse response = client.execute(request, context);

            manageResponseStatus(request, response);

            LOG.debug("successful {} request for {}", request::getMethod, request::getURI);

            return fromJson(response.getEntity(), responseClass);
        } catch (IOException e) {
            LOG.error("unable to complete {} request for {}", request.getMethod(), request.getURI(), e);
            throw new MailingListClientException("Unable to execute the request " + request.getURI(), e);
        }
    }

    private void manageResponseStatus(
            HttpUriRequest request,
            HttpResponse response) throws IOException, MailingListClientException {

        switch (response.getStatusLine().getStatusCode()) {
            case HttpStatus.SC_OK:
            case HttpStatus.SC_CREATED:
            case HttpStatus.SC_ACCEPTED:
            case HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION:
            case HttpStatus.SC_NO_CONTENT:
            case HttpStatus.SC_RESET_CONTENT:
            case HttpStatus.SC_PARTIAL_CONTENT:
            case HttpStatus.SC_MULTI_STATUS:
                return;
            case HttpStatus.SC_NOT_FOUND:
                throw new NotFoundMailingListClientException(
                        "Could not find resource for " + request.getURI().toString());
            default:
                String errorMessage = String.format("failed %s request for %s with %s and body \"%s\"",
                        request.getMethod(),
                        request.getURI(),
                        response.getStatusLine(),
                        EntityUtils.toString(response.getEntity()));

                throw new MailingListClientException(errorMessage);

        }
    }

    private <T> T fromJson(HttpEntity entity, Class<T> responseClass) throws MailingListClientException {
        LOG.debug("converting from JSON for {}", responseClass::getCanonicalName);
        try {
            if (entity != null && entity.getContentLength() != 0) {
                String resultText = EntityUtils.toString(entity);
                T result = gson.fromJson(
                        resultText,
                        responseClass
                );
                LOG.debug("converted from JSON for {}, with JSON {}",
                        responseClass::getCanonicalName,
                        () -> resultText);
                return result;
            } else {
                return null;
            }
        } catch (IOException e) {
            LOG.error("failed to convert from JSON for {}", responseClass.getCanonicalName());
            throw new MailingListClientException("Unable to read the response", e);
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }


}
