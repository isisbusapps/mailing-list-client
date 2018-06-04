package uk.ac.stfc.facilities.mailing.mailchimp;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;

/**
 *
 */
public class MailchimpSegmentCreateRequest {


    @SerializedName("name")
    private String name;

    @SerializedName("static_segment")
    private HashSet<String> emails;

    public static MailchimpSegmentCreateRequest whichCreatesASegment(String segmentName) {
        MailchimpSegmentCreateRequest createRequest = new MailchimpSegmentCreateRequest();
        createRequest.name = segmentName;
        createRequest.emails = new HashSet<>();

        return createRequest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<String> getEmails() {
        return emails;
    }

    public void setEmails(HashSet<String> emails) {
        this.emails = emails;
    }
}
