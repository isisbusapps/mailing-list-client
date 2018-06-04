package uk.ac.stfc.facilities.mailing.mailchimp;

import uk.ac.stfc.facilities.mailing.api.data.MailingListDescriptors;

import java.util.Set;

/**
 *
 */
class MailchimpListDescriptors implements MailingListDescriptors {

    Set<MailchimpListDescriptor> lists;

    public MailchimpListDescriptors(Set<MailchimpListDescriptor> lists) {
        this.lists = lists;
    }

    @Override
    public Set<MailchimpListDescriptor> getLists() {
        return lists;
    }

    public void setLists(Set<MailchimpListDescriptor> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "MailchimpListDescriptors{" +
                "lists=" + lists +
                '}';
    }
}
