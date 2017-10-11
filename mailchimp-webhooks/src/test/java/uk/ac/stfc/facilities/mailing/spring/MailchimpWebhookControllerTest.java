package uk.ac.stfc.facilities.mailing.spring;

import org.junit.jupiter.api.Test;
import uk.ac.stfc.facilities.mailing.person.client.PersonSubscriptionClientException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 *
 */
public class MailchimpWebhookControllerTest {

    @Test
    public void hook_emailChanged_delegatesChangeToSubscriptionManager() throws PersonSubscriptionClientException {

        MarketingSubscriptionManager subManager = mock(MarketingSubscriptionManager.class);

        MailchimpWebhookController controller = new MailchimpWebhookController(subManager);
        controller.changeEmail("old email", "new email");

        verify(subManager).updateEmail("old email", "new email");
    }

    @Test
    public void hook_emailUnsubscribed_delegatesUnsubscriptionToSubscriptionManager() throws PersonSubscriptionClientException {

        MarketingSubscriptionManager subManager = mock(MarketingSubscriptionManager.class);

        MailchimpWebhookController controller = new MailchimpWebhookController(subManager);
        controller.unsubscribe("unsubscribe email");

        verify(subManager).unsubscribeUser("unsubscribe email");
    }

}
