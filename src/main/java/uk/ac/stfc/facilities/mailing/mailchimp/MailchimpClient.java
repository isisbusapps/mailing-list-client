package uk.ac.stfc.facilities.mailing.mailchimp;

import org.apache.commons.codec.digest.DigestUtils;
import uk.ac.stfc.facilities.mailing.api.MailingListClient;
import uk.ac.stfc.facilities.mailing.api.data.*;
import uk.ac.stfc.facilities.mailing.api.exceptions.MailingListClientException;

import java.util.Set;

/**
 * Implementation of <code>MailingListClient</code> that retrieves
 * mailing list data from Mailchimp. It is best to have only one
 * instance of this client per application. The implementation is
 * thread safe.
 * <p>
 * Creating a mailchimp client, where <code>API_KEY</code> is
 * the Mailchimp API key:
 * <pre>
 * MailchimpClientConfiguration config = new MailchimpClientConfiguration(API_KEY);
 * MailingListClient client = MailchimpClient.getInstance(configuration);
 * </pre>
 */
public class MailchimpClient implements MailingListClient {

    /**
     * Generates an instance of the MailchimpClient with the given
     * configuration.
     *
     * @param configuration the Mailchimp client configuration
     * @return a new instance of the Mailchimp client
     */
    public static MailchimpClient getInstance(MailchimpClientConfiguration configuration) {
        return new MailchimpClient(MailchimpInternalHttpClient.getInstance(configuration));
    }

    private static String convertEmailToId(String email) {
        return DigestUtils.md5Hex(email.toLowerCase());
    }

    private final MailchimpInternalHttpClient httpClient;

    MailchimpClient(MailchimpInternalHttpClient internalHttpClient) {
        this.httpClient = internalHttpClient;
    }

    @Override
    public MailingListDescriptors getAllListDescriptors() throws MailingListClientException {
        return httpClient.get(
                "lists",
                MailchimpListDescriptors.class);
    }

    @Override
    public MailingListDescriptor getListDescriptor(String listId) throws MailingListClientException {
        return httpClient.get(
                "lists/" + listId,
                MailchimpListDescriptor.class);
    }

    @Override
    public MailingListMembers getMembersByList(String listId) throws MailingListClientException {
        return httpClient.get(
                "lists/" + listId + "/members",
                MailchimpListMembers.class);
    }

    @Override
    public MailingListMember getMemberOfList(String listId, String email) throws MailingListClientException {
        return httpClient.get(
                "lists/" + listId + "/members/" + convertEmailToId(email),
                MailchimpListMember.class);
    }

    @Override
    public MailingListMember subscribeMember(String listId, String email) throws MailingListClientException {
        if (email == null) {
            throw new IllegalArgumentException("The email argument must not be null.");
        }

        return httpClient.put("lists/" + listId + "/members/" + convertEmailToId(email),
                MailchimpListMemberRequest.whichSubscribes(email),
                MailchimpListMember.class);
    }

    @Override
    public MailingListMember unsubscribeMember(String listId, String email) throws MailingListClientException {
        if (email == null) {
            throw new IllegalArgumentException("The email argument must not be null.");
        }

        return httpClient.put("lists/" + listId + "/members/" + convertEmailToId(email),
                MailchimpListMemberRequest.whichUnsubscribes(email),
                MailchimpListMember.class);
    }

    @Override
    public MailingListMember forceSubscribeMember(String listId, String email) throws MailingListClientException {
        if (email == null) {
            throw new IllegalArgumentException("The email argument must not be null.");
        }

        return httpClient.put("lists/" + listId + "/members/" + convertEmailToId(email),
                MailchimpListMemberRequest.whichForceSubscribes(email),
                MailchimpListMember.class);
    }

    @Override
    public MailingListMember forceUnsubscribeMember(String listId, String email) throws MailingListClientException {
        if (email == null) {
            throw new IllegalArgumentException("The email argument must not be null.");
        }

        return httpClient.put("lists/" + listId + "/members/" + convertEmailToId(email),
                MailchimpListMemberRequest.whichForceUnsubscribes(email),
                MailchimpListMember.class);
    }

    @Override
    public void deleteMemberFromList(String listId, String email) throws MailingListClientException {
        httpClient.delete("lists/" + listId + "/members/" + convertEmailToId(email),
                Void.class);
    }

    @Override
    public MailingListSegmentDescriptors getSegmentDescriptors(String listId) throws MailingListClientException {
        return httpClient.get(
                "lists/" + listId + "/segments",
                MailchimpSegmentDescriptors.class);
    }

    @Override
    public MailingListSegmentDescriptor getSegmentDescriptor(String listId, String segmentId) throws MailingListClientException {
        return httpClient.get(
                "lists/" + listId + "/segments/" + segmentId,
                MailchimpSegmentDescriptor.class);
    }

    @Override
    public MailingListMembers getMembersOfSegment(String listId, String segmentId) throws MailingListClientException {
        return httpClient.get(
                "lists/" + listId + "/segments/" + segmentId + "/members",
                MailchimpListMembers.class);
    }

    @Override
    public MailingListSegmentMemberChanges addMembersToSegment(String listId, String segmentId, Set<String> emails) throws MailingListClientException {
        return httpClient.post(
                "lists/" + listId + "/segments/" + segmentId,
                MailchimpSegmentMemberRequest.whichAddsMembers(emails),
                MailchimpSegmentMemberChanges.class
        );
    }

    @Override
    public MailingListSegmentMemberChanges removeMembersFromSegment(String listId, String segmentId, Set<String> emails) throws MailingListClientException {
        return httpClient.post(
                "lists/" + listId + "/segments/" + segmentId,
                MailchimpSegmentMemberRequest.whichRemovesMembers(emails),
                MailchimpSegmentMemberChanges.class
        );
    }

    @Override
    public MailingListSegmentDescriptor createSegment(String listId, String segmentName) throws MailingListClientException {
        return httpClient.post("lists/" + listId + "/segments",
                MailchimpSegmentCreateRequest.whichCreatesASegment(segmentName),
                MailchimpSegmentDescriptor.class);
    }

    @Override
    public void deleteSegment(String listId, String segmentId) throws MailingListClientException {
        httpClient.delete("lists/" + listId + "/segments/" + segmentId,
                Void.class);
    }
}
