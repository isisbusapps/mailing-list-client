package uk.ac.stfc.facilities.mailing.api.data;

import java.util.Set;

/**
 * Contains a set of mailing list members.
 */
public interface MailingListMembers {

    /**
     * @return the set of members
     */
    Set<? extends MailingListMember> getMembers();
}
