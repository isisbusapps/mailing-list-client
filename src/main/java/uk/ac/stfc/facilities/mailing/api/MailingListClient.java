package uk.ac.stfc.facilities.mailing.api;

import uk.ac.stfc.facilities.mailing.api.data.*;
import uk.ac.stfc.facilities.mailing.api.exceptions.MailingListClientException;
import uk.ac.stfc.facilities.mailing.api.exceptions.NotFoundMailingListClientException;

import java.util.Set;

/**
 * A client for mailing lists service with the following functionality:
 * <ul>
 * <li>retrieving lists and list members</li>
 * <li>managing members’ subscriptions to lists</li>
 * <li>managing list segments &amp; list segment members</li>
 * </ul>
 */
public interface MailingListClient {

    /**
     * Retrieves all lists.
     *
     * @return the retrieved lists
     *
     * @throws MailingListClientException if the resource is unavailable
     */
    MailingListDescriptors getAllListDescriptors()
            throws MailingListClientException;

    /**
     * Retrieves the list with the given list ID.
     *
     * @param listId the ID of the list to retrieve
     *
     * @return the list with the given list ID
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist
     */
    MailingListDescriptor getListDescriptor(String listId)
            throws MailingListClientException;

    /**
     * Retrieves all members of the list by the given list ID.
     *
     * @param listId the ID of the list from which the members are
     *               retrieved
     *
     * @return all members of the list by the given list ID
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist
     */
    MailingListMembers getMembersByList(String listId)
            throws MailingListClientException;

    /**
     * Retrieves a member by their email.
     *
     * @param listId the ID of the list from which the members is
     *               retrieved
     * @param email  the email of the member to retrieve
     *
     * @return the member of the list with the given email
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the ID does
     *                                            not exist or the member of the
     *                                            list does not exist
     */
    MailingListMember getMemberOfList(String listId, String email)
            throws MailingListClientException;

    /**
     * Subscribes the member of the given email to list with the given
     * list ID. If the member has already specified their subscription
     * status this will not change the status but the request will be
     * successful. The response will show the actual status of the
     * member.
     *
     * @param listId the ID of the list to which the member will be
     *               subscribed
     * @param email  the email of the member which will be subscribed
     *
     * @return the member of the list for which the subscription was
     * requested
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist
     */
    MailingListMember subscribeMember(String listId, String email)
            throws MailingListClientException;

    /**
     * Unsubscribes the member of the given email from the list with
     * the given list ID. If the member has already specified their
     * subscription status, this will not change the status but the
     * request will be successful. The response will show the actual
     * status of the member.
     *
     * @param listId the ID of the list from which the member will be
     *               unsubscribed
     * @param email  the email of the member which will be unsubscribed
     *
     * @return the member of the list for which the unsubscription was
     * requested
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist
     */
    MailingListMember unsubscribeMember(String listId, String email)
            throws MailingListClientException;

    /**
     * Forces the subscription of the member with the given email
     * from the list with the given list ID. This overrides any
     * previous preference the member has.
     *
     * @param listId the ID of the list to which the member will be
     *               subscribed
     * @param email  the email of the member which will be subscribed
     *
     * @return the member of the list for which the subcription was
     * requested
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist
     */
    MailingListMember forceSubscribeMember(String listId, String email)
            throws MailingListClientException;

    /**
     * Forces the unsubscription of the member with the given email
     * from the list with the given list ID. This overrides any
     * previous preference the member has.
     *
     * @param listId the ID of the list from which the member will
     *               be unsubcribed
     * @param email  the email of the member which will be unsubscribed
     *
     * @return the member of the list for which the unsubscription was
     * requested
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist
     */
    MailingListMember forceUnsubscribeMember(String listId, String email)
            throws MailingListClientException;

    /**
     * Removes the member with the given email from the list entirely,
     * this will remove any additional data stored about the member
     * in the mailing list service.
     *
     * @param listId the ID of the list from which the member will be
     *               removed from
     * @param email  the email of the member who will be removed
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist or the member
     *                                            of the list does not exist
     */
    void deleteMemberFromList(String listId, String email)
            throws MailingListClientException;

    /**
     * Retrieves the segments of the list with the given list ID.
     *
     * @param listId the ID of the list from which to retrieve the
     *               segment
     *
     * @return the segments of the list with the given ID
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist
     */
    MailingListSegmentDescriptors getSegmentDescriptors(String listId)
            throws MailingListClientException;

    /**
     * Retrieves the segment of the list with the given list ID.
     *
     * @param listId    the ID of the list from which to retrieve the
     *                  segment
     * @param segmentId the ID of the segment to retrieve
     *
     * @return the segment of the list with the given ID
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist or if the
     *                                            segment with the given ID does
     *                                            not exist
     */
    MailingListSegmentDescriptor getSegmentDescriptor(String listId, String segmentId)
            throws MailingListClientException;

    /**
     * Retrieves the members of the segment with the given segment ID
     * within the list with the given list ID.
     *
     * @param listId    the ID of the list from which to retrieve the
     *                  segment members
     * @param segmentId the ID of the segment to retrieve the segment
     *                  members
     *
     * @return the members for the segment with the given ID
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist or if the
     *                                            segment with the given ID does
     *                                            not exist
     */
    MailingListMembers getMembersOfSegment(String listId, String segmentId)
            throws MailingListClientException;

    /**
     * Adds the members who have the given email addresses from the
     * specified segment in the specified list. If the member does
     * not exist in the list they will not be added to the list.
     *
     * @param listId    the ID of the list in which the segment is
     * @param segmentId the ID of the segment to add the members to
     * @param emails    the emails of the members to add
     *
     * @return the successfully added members
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist or if the
     *                                            segment with the given ID does
     *                                            not exist
     */
    MailingListSegmentMemberChanges addMembersToSegment(String listId, String segmentId, Set<String> emails)
            throws MailingListClientException;

    /**
     * Removes the members who have the given email addresses to the
     * specified segment in the specified list.
     *
     * @param listId    the ID of the list in which the segment is
     * @param segmentId the ID of the segment to remove the members from
     * @param emails    the emails of the members to remove
     *
     * @return the successfully removed members
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with given ID does
     *                                            not exist or if the segment
     *                                            with the given ID does not
     *                                            exist
     */
    MailingListSegmentMemberChanges removeMembersFromSegment(String listId, String segmentId, Set<String> emails)
            throws MailingListClientException;

    /**
     * Creates a segment with the given name in the given list.
     *
     * @param listId      the ID of the list in which to create the
     *                    segment
     * @param segmentName the name of the segment
     *
     * @return the descriptor of the segment
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist
     */
    MailingListSegmentDescriptor createSegment(String listId, String segmentName)
            throws MailingListClientException;

    /**
     * Deletes the segment with the given ID in the given list.
     *
     * @param listId    this ID of the list in which to create the segment
     * @param segmentId the ID of the segment
     *
     * @throws MailingListClientException         if the resource is unavailable
     * @throws NotFoundMailingListClientException if the list with the given ID
     *                                            does not exist or if the
     *                                            segment with the given ID does
     *                                            not exist
     */
    void deleteSegment(String listId, String segmentId)
            throws MailingListClientException;
}
