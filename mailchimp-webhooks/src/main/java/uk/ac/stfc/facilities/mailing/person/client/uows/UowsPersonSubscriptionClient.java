package uk.ac.stfc.facilities.mailing.person.client.uows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.ac.stfc.facilities.mailing.person.client.PersonSubscriptionClient;
import uk.ac.stfc.facilities.mailing.person.client.PersonSubscriptionClientException;
import uk.stfc.userOfficeClient.*;

import javax.annotation.PostConstruct;
import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Integrates with the UserOfficeWebService and acts as a facade.
 */
@Component
public class UowsPersonSubscriptionClient implements PersonSubscriptionClient<UowsPersonSubscriptionDetails> {

    private static final Logger LOG = LogManager.getLogger();

    private final String sessionId;
    private final String userOfficeWebServiceUrl;

    private UserOfficeWebService_Service uows;

    /**
     * Creates a UowsPersonSubscriptionClient with the required session information
     * and the URL for the web service.
     *
     * @param sessionId the session ID required to make changes to
     *                  people's email addresses
     * @param url       the URL of the user office web service.
     */
    public UowsPersonSubscriptionClient(
            @Value("${mailchimp.webhooks.client.session-id}") String sessionId,
            @Value("${UserOfficeWebServiceURL}") String url) {
        this.sessionId = sessionId;
        this.userOfficeWebServiceUrl = url;
    }

    /**
     * Initialise the user office web service for this instance. Only
     * one web service is needed per instance.
     */
    @PostConstruct
    public void init() throws MalformedURLException {
        this.uows = new UserOfficeWebService_Service(new URL(userOfficeWebServiceUrl));
    }

    /**
     * Gets a person by their marketing email.
     *
     * @param marketingEmail the person's marketing email
     *
     * @return the person with the given marketing email
     *
     * @throws PersonSubscriptionClientException on failure to retrieve the
     *                                           details
     */
    @Override
    public UowsPersonSubscriptionDetails getPersonByMarketingEmail(String marketingEmail)
            throws PersonSubscriptionClientException {
        try {
            return new UowsPersonSubscriptionDetails(getWebServicePort().getPersonDTOByMarketingEmail(sessionId, marketingEmail));
        } catch (SessionException_Exception | UserPermissionsException_Exception e) {
            LOG.warn("Unable to get person", e);
            throw new PersonSubscriptionClientException("Unable to retrieve person details", e);
        }
    }

    /**
     * Saves the given person.
     *
     * @param personSubscriptionDetails the details of the person
     *
     * @throws PersonSubscriptionClientException on failure to delete the
     *                                           person
     */
    @Override
    public void savePerson(UowsPersonSubscriptionDetails personSubscriptionDetails)
            throws PersonSubscriptionClientException {
        try {
            getWebServicePort().updatePersonByDTOFromSource(
                    sessionId, personSubscriptionDetails.getPersonDTO(), RequestSourceDTO.MAILING_API);
        } catch (PersonDTOException_Exception | SessionException_Exception e) {
            LOG.warn("Unable to save person", e);
            throw new PersonSubscriptionClientException("Unable to save person", e);
        }
    }

    /**
     * Creates an instance of the web service port from this instance's
     * service. These cannot be used concurrently.
     *
     * @return an new web service port
     */
    private UserOfficeWebService getWebServicePort() {
        UserOfficeWebService userOfficeWebService = uows.getUserOfficeWebServicePort();

        BindingProvider bindingProvider = (BindingProvider) userOfficeWebService;
        Map<String, Object> context = bindingProvider.getRequestContext();

        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, userOfficeWebServiceUrl);

        return userOfficeWebService;
    }

}
