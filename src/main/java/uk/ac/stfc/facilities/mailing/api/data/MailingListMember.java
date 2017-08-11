package uk.ac.stfc.facilities.mailing.api.data;

/**
 * Describes a member of a mailing list.
 */
public interface MailingListMember {

    /**
     * @return the ID of the mailing list member
     */
    String getId();

    /**
     * @return the email address of the list member
     */
    String getEmailAddress();

    /**
     * @return a global identifier for the member
     */
    String getUniqueEmailId();

    /**
     * @return the status of the list member
     */
    SubscriptionStatus getSubscriptionStatus();

    /**
     * @return the ID of the list
     */
    String getListId();
}
