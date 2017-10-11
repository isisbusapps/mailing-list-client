package uk.ac.stfc.facilities.mailing.person.client;

/**
 * Contains details of a person's subscription.
 */
public interface PersonSubscriptionDetails {

    /**
     * Gets the mailing email for the person.
     *
     * @return the mailing email for the person.
     */
    String getMailingEmail();

    /**
     * Gets whether the user is subscribed.
     *
     * @return whether the user is subscribed
     */
    boolean isSubscribed();

    /**
     * Sets the mailing email for the person.
     *
     * @param email the mailing email to set
     */
    void setMailingEmail(String email);

    /**
     * Sets the subscription status for the person.
     *
     * @param subscribed whether the users should be subscribed
     */
    void setSubscribed(boolean subscribed);

    /**
     * Gets the person's identifier.
     *
     * @return the person's identifier.
     */
    Object getId();

}
