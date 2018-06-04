package uk.ac.stfc.facilities.mailing.api.data;

/**
 * The subscription status of a member.
 */
public enum SubscriptionStatus {

    /**
     * The status when a member is subscribed.
     */
    SUBSCRIBED,

    /**
     * The status when a member is unsubscribed.
     */
    UNSUBSCRIBED,

    /**
     * The status when the state of subscription for a member is unknown.
     */
    UNKNOWN
}
