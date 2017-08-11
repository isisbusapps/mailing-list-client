package uk.ac.stfc.facilities.mailing.api.data;

import java.util.Set;

/**
 * Describes the changes made to the segment members
 */
public interface MailingListSegmentMemberChanges {

    /**
     * @return the set of members added to the segment
     */
    Set<? extends MailingListMember> getAddedMembers();

    /**
     * @return the set of members removed from the segment
     */
    Set<? extends MailingListMember> getRemovedMembers();

}
