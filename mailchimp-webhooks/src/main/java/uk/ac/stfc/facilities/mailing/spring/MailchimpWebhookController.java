package uk.ac.stfc.facilities.mailing.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/${mailing.webhook.key}")
public class MailchimpWebhookController {

    private static final Logger LOGGER = LogManager.getLogger();

    @GetMapping()
    @ResponseBody
    public void handshake() {
    }

    @PostMapping()
    @ResponseBody
    public void hook(
            @RequestParam("data[old_email]") String oldEmail,
            @RequestParam("data[new_email]") String newEmail
    ) {
        LOGGER.info("old: " + oldEmail + " to " + newEmail);
    }
}
