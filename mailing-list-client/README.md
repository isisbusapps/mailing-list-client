## `mailing-list-api`

Contains an API for connecting to mailing lists -
`uk.ac.stfc.facilities.mailing.api`.

Contains an implementation of the mailing lists API for Mailchimp -
`uk.ac.stfc.facilities.mailing.mailchimp`

### Using the `MailchimpClient`

1. Create an instance of the `MailchimpClientConfiguration`, with the API key
given as an argument to the constructor.
```java
MailchimpClientConfiguration config = new MailchimpClientConfiguration(API_KEY);
```
2. Get a new instance of the `MailchimpClient` using the factory method. This
takes the configuration in as an argument.
```java
MailingListClient client = MailchimpClient.getInstance(config);
```

The client is now ready to use.

### Running the integration tests for the `MailchimpClient`.

An [`applications.test.properties`](mailing-list-api/application.test.properties)
file must exist for the mailchimp client.

**NB:** please ensure these tests are ran against an account with no
billing information attatched to it.

