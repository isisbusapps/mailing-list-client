package uk.ac.stfc.facilities.mailing.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.stfc.facilities.mailing.person.client.PersonSubscriptionClientException;

/**
 * A Spring controller that listens to webhooks from Mailchimp, it
 * currently handles the webhook handshake and updates to the users
 * email. It refers to the <code>mailing.webhook.key</code> key in
 * config to create the mapping.
 */
@Controller
@RequestMapping("/${mailing.webhook.key}")
public class MailchimpWebhookController {

    private static final Logger LOGGER = LogManager.getLogger();

    private final MarketingSubscriptionManager marketingSubscriptionManager;

    public MailchimpWebhookController(
            @Autowired MarketingSubscriptionManager marketingSubscriptionManager) {
        this.marketingSubscriptionManager = marketingSubscriptionManager;
    }

    /**
     * A simple method to respond with a successful status which is
     * required for Mailchimp to accept a webhook client.
     */
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void handshake() {
    }

    /**
     * A webhook that listens to changes to a user&apos;s email
     * address change from MailChimp.
     *
     * @param oldEmail the old email for the user
     * @param newEmail the new email for the user
     */
    @PostMapping(params = "type=upemail")
    @ResponseBody
    public void changeEmail(
            @RequestParam("data[old_email]") String oldEmail,
            @RequestParam("data[new_email]") String newEmail
    ) throws PersonSubscriptionClientException {

        marketingSubscriptionManager.updateEmail(oldEmail, newEmail);

    }

    /**
     * A webhook that listens to changes to an unsubscription request
     * from users in MailChimp
     *
     * @param email the user&apos;s email
     */
    @PostMapping(params = "type=unsubscribe")
    @ResponseBody
    public void unsubscribe(
            @RequestParam("data[email]") String email
    ) throws PersonSubscriptionClientException {
        marketingSubscriptionManager.unsubscribeUser(email);

    }
}
