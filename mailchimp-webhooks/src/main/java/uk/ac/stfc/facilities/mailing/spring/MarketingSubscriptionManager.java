package uk.ac.stfc.facilities.mailing.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.stfc.facilities.mailing.person.client.PersonSubscriptionClient;
import uk.ac.stfc.facilities.mailing.person.client.PersonSubscriptionClientException;
import uk.ac.stfc.facilities.mailing.person.client.PersonSubscriptionDetails;

/**
 * Manages the subscriptions of users.
 */
@Service
public class MarketingSubscriptionManager {

    private final PersonSubscriptionClient<PersonSubscriptionDetails> personSubscriptionClient;

    /**
     * Creates a marketing subscription manager with the given person
     * subscription client.
     *
     * @param personSubscriptionClient the given person subscription
     *                                 client
     */
    public MarketingSubscriptionManager(
            @Autowired PersonSubscriptionClient<PersonSubscriptionDetails> personSubscriptionClient
    ) {
        this.personSubscriptionClient = personSubscriptionClient;
    }

    /**
     * Updates a person's email address using the instance's client.
     *
     * @param oldEmail the old email of the person. This is used to find
     *                 the user.
     * @param newEmail the new email for the person
     *
     * @throws PersonSubscriptionClientException if there was a problem
     *                                           updating them email
     */
    public void updateEmail(String oldEmail, String newEmail) throws PersonSubscriptionClientException {

        PersonSubscriptionDetails personSubscriptionDetails = personSubscriptionClient.getPersonByMarketingEmail(oldEmail);

        personSubscriptionDetails.setMailingEmail(newEmail);

        personSubscriptionClient.savePerson(personSubscriptionDetails);
    }

    /**
     * Unsubscribes the person with the given email address.
     *
     * @param email the email of the person to unsubscribe
     *
     * @throws PersonSubscriptionClientException if there was a problem
     *                                           unsubscribing the user
     */
    public void unsubscribeUser(String email) throws PersonSubscriptionClientException {
        PersonSubscriptionDetails personSubscriptionDetails = personSubscriptionClient.getPersonByMarketingEmail(email);

        personSubscriptionDetails.setMailingEmail(null);
        personSubscriptionDetails.setSubscribed(false);

        personSubscriptionClient.savePerson(personSubscriptionDetails);
    }
}
