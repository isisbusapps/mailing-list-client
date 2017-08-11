package uk.ac.stfc.facilities.mailing.mailchimp;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 *
 */
class MailchimpSegmentMemberRequest {

    @SerializedName("members_to_add")
    private Set<String> membersToAdd;
    @SerializedName("members_to_remove")
    private Set<String> membersToRemove;

    public static MailchimpSegmentMemberRequest whichAddsMembers(Set<String> email) {
        MailchimpSegmentMemberRequest request = new MailchimpSegmentMemberRequest();
        request.membersToAdd = email;
        return request;
    }

    public static MailchimpSegmentMemberRequest whichRemovesMembers(Set<String> email) {
        MailchimpSegmentMemberRequest request = new MailchimpSegmentMemberRequest();
        request.membersToRemove = email;
        return request;
    }

    public Set<String> getMembersToAdd() {
        return membersToAdd;
    }

    public void setMembersToAdd(Set<String> membersToAdd) {
        this.membersToAdd = membersToAdd;
    }

    public Set<String> getMembersToRemove() {
        return membersToRemove;
    }

    public void setMembersToRemove(Set<String> membersToRemove) {
        this.membersToRemove = membersToRemove;
    }
}
