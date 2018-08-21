package uk.ac.stfc.facilities.mailing.mailchimp;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
@DisplayName("The MailChimp client when managing subscriptions to lists")
public class MailchimpListMemberRequestTest {

    @DisplayName("when called with a non-null argument should be successful")
    public class Success {
        @Test
        @DisplayName("for subscribing")
        public void whichSubscribes() {
            MailchimpListMemberRequest request = MailchimpListMemberRequest.whichSubscribes("test");

            assertThat(request.getEmail()).isEqualTo("test");
            assertThat(request.getStatusIfNew()).isEqualTo("subscribed");
            assertThat(request.getStatus()).isNull();

        }

        @Test
        @DisplayName("for unsubscribing")
        public void whichUnsubscribes() {
            MailchimpListMemberRequest request = MailchimpListMemberRequest.whichUnsubscribes("test");

            assertThat(request.getEmail()).isEqualTo("test");
            assertThat(request.getStatusIfNew()).isEqualTo("unsubscribed");
            assertThat(request.getStatus()).isNull();

        }

        @Test
        @DisplayName("for forcing subscribing")
        public void whichForceSubscribes() {
            MailchimpListMemberRequest request = MailchimpListMemberRequest.whichForceSubscribes("test");

            assertThat(request.getEmail()).isEqualTo("test");
            assertThat(request.getStatusIfNew()).isEqualTo("subscribed");
            assertThat(request.getStatus()).isEqualTo("subscribed");

        }

        @Test
        @DisplayName("for forcing unsubscribing")
        public void whichForceUnsubscribes() {
            MailchimpListMemberRequest request = MailchimpListMemberRequest.whichForceUnsubscribes("test");

            assertThat(request.getEmail()).isEqualTo("test");
            assertThat(request.getStatusIfNew()).isEqualTo("unsubscribed");
            assertThat(request.getStatus()).isEqualTo("unsubscribed");

        }

    }

    @DisplayName("when called with a null email arguments should throw an IllegalArgumentException")
    public class Failure {

        @Test
        @DisplayName("for subscribing")
        public void whichSubscribes() {
            assertThrows(IllegalArgumentException.class, () -> MailchimpListMemberRequest.whichSubscribes("test"));
        }

        @Test
        @DisplayName("for unsubscribing")
        public void whichUnsubscribes() {
            assertThrows(IllegalArgumentException.class, () -> MailchimpListMemberRequest.whichUnsubscribes("test"));
        }

        @Test
        @DisplayName("for forcing subscribing")
        public void whichForceSubscribes() {
            assertThrows(IllegalArgumentException.class, () -> MailchimpListMemberRequest.whichForceSubscribes("test"));
        }

        @Test
        @DisplayName("for forcing unsubscribing")
        public void whichForceUnsubscribes() {
            assertThrows(IllegalArgumentException.class, () -> MailchimpListMemberRequest.whichForceUnsubscribes("test"));
        }
    }

}
