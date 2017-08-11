package uk.ac.stfc.facilities.mailing.api.exceptions;

/**
 * A MailingListClientException that describes the situation where a
 * resource was not found.
 */
public class NotFoundMailingListClientException extends MailingListClientException {
    public NotFoundMailingListClientException(String s) {
        super(s);
    }

    public NotFoundMailingListClientException(String s, Throwable e) {
        super(s, e);
    }
}
