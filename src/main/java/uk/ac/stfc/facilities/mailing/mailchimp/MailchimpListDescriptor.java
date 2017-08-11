package uk.ac.stfc.facilities.mailing.mailchimp;

import uk.ac.stfc.facilities.mailing.api.data.MailingListDescriptor;

class MailchimpListDescriptor implements MailingListDescriptor {
    private String id;
    private String name;

    public MailchimpListDescriptor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MailchimpListDescriptor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
