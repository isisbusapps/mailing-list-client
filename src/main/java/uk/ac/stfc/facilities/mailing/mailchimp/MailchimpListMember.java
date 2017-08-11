package uk.ac.stfc.facilities.mailing.mailchimp;

import com.google.gson.annotations.SerializedName;
import uk.ac.stfc.facilities.mailing.api.data.MailingListMember;
import uk.ac.stfc.facilities.mailing.api.data.SubscriptionStatus;

import java.time.LocalDateTime;

/**
 *
 */
class MailchimpListMember implements MailingListMember {


    private String id;
    @SerializedName("email_address")
    private String emailAddress;
    @SerializedName("unique_email_id")
    private String uniqueEmailId;
    @SerializedName("email_type")
    private String emailType;
    @SerializedName("status")
    private String status;

    @SerializedName("timestamp_opt")
    private LocalDateTime timestampOpt;
    @SerializedName("last_change")
    private LocalDateTime lastChange;

    @SerializedName("language")
    private String language;
    @SerializedName("list_id")
    private String listId;

    public MailchimpListMember() {
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String getUniqueEmailId() {
        return uniqueEmailId;
    }

    @Override
    public SubscriptionStatus getSubscriptionStatus() {
        switch (status) {
            case "subscribed":
                return SubscriptionStatus.SUBSCRIBED;
            case "unsubscribed":
                return SubscriptionStatus.UNSUBSCRIBED;
            default:
                return SubscriptionStatus.UNKNOWN;
        }
    }

    public void setUniqueEmailId(String uniqueEmailId) {
        this.uniqueEmailId = uniqueEmailId;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestampOpt() {
        return timestampOpt;
    }

    public void setTimestampOpt(LocalDateTime timestampOpt) {
        this.timestampOpt = timestampOpt;
    }

    public LocalDateTime getLastChange() {
        return lastChange;
    }

    public void setLastChange(LocalDateTime lastChange) {
        this.lastChange = lastChange;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    @Override
    public String toString() {
        return "MailchimpListMember{" +
                "id='" + id + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", uniqueEmailId='" + uniqueEmailId + '\'' +
                ", emailType='" + emailType + '\'' +
                ", status='" + status + '\'' +
                ", timestampOpt=" + timestampOpt +
                ", lastChange=" + lastChange +
                ", language='" + language + '\'' +
                ", listId='" + listId + '\'' +
                '}';
    }
}
