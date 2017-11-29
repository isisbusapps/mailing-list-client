package uk.ac.stfc.facilities.mailing.mailchimp;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class MailchimpListMemberRequestTest {

    @Test
    public void whichSubscribes() {
        MailchimpListMemberRequest request = MailchimpListMemberRequest.whichSubscribes("test");

        assertThat(request.getEmail()).isEqualTo("test");
        assertThat(request.getStatusIfNew()).isEqualTo("subscribed");
        assertThat(request.getStatus()).isNull();

    }

    @Test
    public void whichUnsubscribes() {
        MailchimpListMemberRequest request = MailchimpListMemberRequest.whichUnsubscribes("test");

        assertThat(request.getEmail()).isEqualTo("test");
        assertThat(request.getStatusIfNew()).isEqualTo("unsubscribed");
        assertThat(request.getStatus()).isNull();

    }

    @Test
    public void whichForceSubscribes() {
        MailchimpListMemberRequest request = MailchimpListMemberRequest.whichForceSubscribes("test");

        assertThat(request.getEmail()).isEqualTo("test");
        assertThat(request.getStatusIfNew()).isEqualTo("subscribed");
        assertThat(request.getStatus()).isEqualTo("subscribed");

    }

    @Test
    public void whichForceUnsubscribes() {
        MailchimpListMemberRequest request = MailchimpListMemberRequest.whichForceUnsubscribes("test");

        assertThat(request.getEmail()).isEqualTo("test");
        assertThat(request.getStatusIfNew()).isEqualTo("unsubscribed");
        assertThat(request.getStatus()).isEqualTo("unsubscribed");

    }
}
