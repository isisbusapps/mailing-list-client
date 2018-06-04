package uk.ac.stfc.facilities.mailing.mailchimp;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
class MailchimpListMemberRequest {

    private static final String SUBSCRIBED = "subscribed";
    private static final String UNSUBSCRIBED = "unsubscribed";

    public static MailchimpListMemberRequest whichSubscribes(String email) {
        MailchimpListMemberRequest request = new MailchimpListMemberRequest();

        request.email = email;
        request.statusIfNew = SUBSCRIBED;
        return request;
    }

    public static MailchimpListMemberRequest whichForceSubscribes(String email) {

        MailchimpListMemberRequest request = new MailchimpListMemberRequest();

        request.email = email;
        request.statusIfNew = SUBSCRIBED;
        request.status = SUBSCRIBED;
        return request;
    }

    public static MailchimpListMemberRequest whichUnsubscribes(String email) {

        MailchimpListMemberRequest requests = new MailchimpListMemberRequest();

        requests.email = email;
        requests.statusIfNew = UNSUBSCRIBED;
        return requests;
    }

    public static MailchimpListMemberRequest whichForceUnsubscribes(String email) {

        MailchimpListMemberRequest request = new MailchimpListMemberRequest();

        request.email = email;
        request.statusIfNew = UNSUBSCRIBED;
        request.status = UNSUBSCRIBED;
        return request;
    }

    @SerializedName("email_address")
    private String email;

    @SerializedName("status_if_new")
    private String statusIfNew;

    @SerializedName("status")
    private String status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatusIfNew() {
        return statusIfNew;
    }

    public void setStatusIfNew(String statusIfNew) {
        this.statusIfNew = statusIfNew;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
