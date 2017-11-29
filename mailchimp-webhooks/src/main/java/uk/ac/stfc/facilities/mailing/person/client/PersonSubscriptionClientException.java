package uk.ac.stfc.facilities.mailing.person.client;

/**
 * When errors occur in the {@link PersonSubscriptionClient} this exception
 * is thrown.
 */
public class PersonSubscriptionClientException extends Exception {

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public PersonSubscriptionClientException(String message, Throwable cause) {
        super(message, cause);
    }

}
