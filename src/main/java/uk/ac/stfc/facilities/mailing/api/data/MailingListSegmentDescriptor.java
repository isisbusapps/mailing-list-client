package uk.ac.stfc.facilities.mailing.api.data;

/**
 * Describes a mailing list segment.
 */
public interface MailingListSegmentDescriptor {

    /**
     * @return the ID of the segment
     */
    String getId();

    /**
     * @return the name of the segment
     */
    String getName();

}
