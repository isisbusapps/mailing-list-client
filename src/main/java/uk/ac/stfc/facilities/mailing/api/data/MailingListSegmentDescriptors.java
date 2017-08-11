package uk.ac.stfc.facilities.mailing.api.data;

import java.util.Set;

/**
 * Contains a set of mailing list segment descriptors.
 */
public interface MailingListSegmentDescriptors {

    /**
     * @return the set of mailing list segment descriptors
     */
    Set<? extends MailingListSegmentDescriptor> getSegmentDescriptors();

}
