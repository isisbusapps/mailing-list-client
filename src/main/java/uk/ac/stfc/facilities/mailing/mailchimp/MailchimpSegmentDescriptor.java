package uk.ac.stfc.facilities.mailing.mailchimp;

import uk.ac.stfc.facilities.mailing.api.data.MailingListSegmentDescriptor;

/**
 *
 */
class MailchimpSegmentDescriptor implements MailingListSegmentDescriptor {
    private String id;
    private String name;

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
        return "MailchimpSegmentDescriptor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
