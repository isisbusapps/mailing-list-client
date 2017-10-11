package uk.ac.stfc.facilities.mailing.person.client;

/**
 * A client for updating a Person's subscription. It allows for finding
 * a person by marketing email and saving that person.
 */
public interface PersonSubscriptionClient<E extends PersonSubscriptionDetails> {

    /**
     * Retrieves a Person's subscription details by their marketing
     * email.
     *
     * @param marketingEmail the marketing email to find a person
     *                       subscription by
     *
     * @return the person with the given marketing email
     *
     * @throws PersonSubscriptionClientException when there's a problem
     *                                           retrieving the details
     */
    E getPersonByMarketingEmail(String marketingEmail) throws PersonSubscriptionClientException;

    /**
     * Saves the given person's subscription details.
     *
     * @param personSubscriptionDetails the details to save
     *
     * @throws PersonSubscriptionClientException when there's a problem
     *                                           saving the details
     */
    void savePerson(E personSubscriptionDetails) throws PersonSubscriptionClientException;

}
