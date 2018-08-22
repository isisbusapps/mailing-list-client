package uk.ac.stfc.facilities.mailing.mailchimp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sun.net.www.http.HttpClient;
import uk.ac.stfc.facilities.mailing.api.data.MailingListMember;
import uk.ac.stfc.facilities.mailing.api.exceptions.MailingListClientException;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@DisplayName("The MailchimpClient should")
public class MailchimpClientTest {

    private static final String LIST = "a list";
    private static final String NULL_EMAIL = null;

    @Nested
    @DisplayName("throw an IllegalArgumentException when email is null")
    public class NullEmailExceptions {

        private MailchimpClient client;

        @BeforeEach
        public void setup() {
            client = new MailchimpClient(mock(MailchimpInternalHttpClient.class));

        }

        @Test
        @DisplayName("when subscribing")
        public void subscribes() {
            assertThrows(IllegalArgumentException.class, () -> client.subscribeMember(LIST, NULL_EMAIL));
        }

        @Test
        @DisplayName("when unsubscribing")
        public void unsubscribes() {
            assertThrows(IllegalArgumentException.class, () -> client.unsubscribeMember(LIST, NULL_EMAIL));
        }

        @Test
        @DisplayName("when force subscribing")
        public void forceSubscribes() {
            assertThrows(IllegalArgumentException.class, () -> client.forceSubscribeMember(LIST, NULL_EMAIL));
        }

        @Test
        @DisplayName("when force unsubscribing")
        public void forceUnsubscribes() {
            assertThrows(IllegalArgumentException.class, () -> client.forceUnsubscribeMember(LIST, NULL_EMAIL));
        }

    }


}
