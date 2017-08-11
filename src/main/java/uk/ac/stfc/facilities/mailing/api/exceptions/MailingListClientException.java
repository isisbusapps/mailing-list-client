package uk.ac.stfc.facilities.mailing.api.exceptions;

/**
 * Describes errors that occurred trying to get a resource from the
 * mailing list client.
 */
public class MailingListClientException extends Exception {

    public MailingListClientException(String s) {
        super(s);
    }

    public MailingListClientException(String s, Throwable e) {
        super(s, e);
    }
}
