package uk.ac.stfc.facilities.mailing.api.data;

import java.util.Set;

/**
 * Contains a set of mailing list descriptors.
 */
public interface MailingListDescriptors {

    /**
     * @return the set of mailing list descriptors
     */
    Set<? extends MailingListDescriptor> getLists();
}
