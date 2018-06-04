package uk.ac.stfc.facilities.mailing.mailchimp;

import uk.ac.stfc.facilities.mailing.api.data.MailingListMembers;

import java.util.Set;

/**
 *
 */
class MailchimpListMembers implements MailingListMembers {

    Set<MailchimpListMember> members;

    public MailchimpListMembers(Set<MailchimpListMember> members) {
        this.members = members;
    }

    @Override
    public Set<MailchimpListMember> getMembers() {
        return members;
    }

    public void setMembers(Set<MailchimpListMember> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "MailchimpListMembers{" +
                "members=" + members +
                '}';
    }
}
