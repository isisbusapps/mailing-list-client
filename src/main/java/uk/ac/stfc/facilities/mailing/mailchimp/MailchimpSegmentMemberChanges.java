package uk.ac.stfc.facilities.mailing.mailchimp;

import com.google.gson.annotations.SerializedName;
import uk.ac.stfc.facilities.mailing.api.data.MailingListSegmentMemberChanges;

import java.util.Set;

/**
 *
 */
class MailchimpSegmentMemberChanges implements MailingListSegmentMemberChanges {
    @SerializedName("members_added")
    private Set<MailchimpListMember> addedMembers;
    @SerializedName("members_removed")
    private Set<MailchimpListMember> removedMembers;

    @Override
    public Set<MailchimpListMember> getAddedMembers() {
        return addedMembers;
    }

    @Override
    public Set<MailchimpListMember> getRemovedMembers() {
        return removedMembers;
    }

    public void setAddedMembers(Set<MailchimpListMember> addedMembers) {
        this.addedMembers = addedMembers;
    }

    public void setRemovedMembers(Set<MailchimpListMember> removedMembers) {
        this.removedMembers = removedMembers;
    }

    @Override
    public String toString() {
        return "MailchimpSegmentMemberChanges{" +
                "addedMembers=" + addedMembers +
                ", removedMembers=" + removedMembers +
                '}';
    }

}
