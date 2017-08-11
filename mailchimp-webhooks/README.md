## Mailchimp Webhook Service

A proof of concept Spring Boot service used create a simple webhook
service for MailChimp. Currently accepts only "changed email" hooks.

On recieving a webhook, it will write the `data[old_email]` and the
`data[new_url]` to the logs.

### Configuration

Requires `${mailing.webhook.key}` to be set in the Spring
`application.properties`, [providing some security to the
application](https://developer.mailchimp.com/documentation/mailchimp/guides/about-webhooks/#securing-webhooks).

The URL for the webhook will then be `${base.uri}/${mailing.webhook.key}`.

