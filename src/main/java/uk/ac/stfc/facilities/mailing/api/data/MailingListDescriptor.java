package uk.ac.stfc.facilities.mailing.api.data;

/**
 * Describes a mailing list.
 */
public interface MailingListDescriptor {

    /**
     * @return the ID of the mailing list
     */
    String getId();

    /**
     * @return the name of the mailing list
     */
    String getName();
}
