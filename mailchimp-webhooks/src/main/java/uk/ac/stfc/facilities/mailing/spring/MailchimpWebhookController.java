package uk.ac.stfc.facilities.mailing.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    /**
     * A simple method to respond with a successful status which is
     * required for Mailchimp to accept a webhook client.
     */
    @GetMapping
    @ResponseBody
    public void handshake() {
    }

    /**
     * A webhook that listens to changes to a user&apos;s email
     * address change.
     * @param oldEmail the old email for the user
     * @param newEmail the new email for the user
     */
    @PostMapping
    @ResponseBody
    public void hook(
            @RequestParam("data[old_email]") String oldEmail,
            @RequestParam("data[new_email]") String newEmail
    ) {
        LOGGER.info("old: " + oldEmail + " to " + newEmail);
    }
}
