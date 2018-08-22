package uk.ac.stfc.facilities.mailing.mailchimp;

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

    private interface MailchimpFunction {
        Object apply(MailchimpClient client) throws MailingListClientException;
    }

    public static Collection<Arguments> generateNullArgumentFunctions() {

        MailchimpFunction subscribe = client -> client.subscribeMember("a list", null);
        MailchimpFunction unsubscribe = client -> client.unsubscribeMember("a list", null);
        MailchimpFunction forceSubscribe = client -> client.forceSubscribeMember("a list", null);
        MailchimpFunction forceUnsubscribe = client -> client.forceUnsubscribeMember("a list", null);

        return Arrays.asList(
                Arguments.of("subscribe", subscribe),
                Arguments.of("unsubscribe", unsubscribe),
                Arguments.of("forceSubscribe", forceSubscribe),
                Arguments.of("forceUnsubscribe", forceUnsubscribe)
        );
    }

    @DisplayName("throw an IllegalArgumentException when email is null")
    @ParameterizedTest(name = "for {0}")
    @MethodSource("generateNullArgumentFunctions")
    public void forSubscriptions(String type, MailchimpFunction function) throws MailingListClientException {

        MailchimpClient client = new MailchimpClient(mock(MailchimpInternalHttpClient.class));

        assertThrows(IllegalArgumentException.class, () -> function.apply(client));
    }

}
