package uk.ac.stfc.facilities.mailing.person.client;

/**
 * When errors occur in the {@link PersonSubscriptionClient} this exception
 * is thrown.
 */
public class PersonSubscriptionClientException extends Exception {

    /**
     * @see Exception#Exception()
     */
    public PersonSubscriptionClientException() {
    }

    /**
     * @see Exception#Exception(String)
     */
    public PersonSubscriptionClientException(String message) {
        super(message);
    }

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public PersonSubscriptionClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @see Exception#Exception(Throwable)
     */
    public PersonSubscriptionClientException(Throwable cause) {
        super(cause);
    }

    /**
     * @see Exception#Exception(String, Throwable, boolean, boolean)
     */
    public PersonSubscriptionClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
