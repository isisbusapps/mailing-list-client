package uk.ac.stfc.facilities.mailing.mailchimp;

import org.junit.jupiter.api.*;
import uk.ac.stfc.facilities.mailing.api.data.*;
import uk.ac.stfc.facilities.mailing.api.exceptions.MailingListClientException;
import uk.ac.stfc.facilities.mailing.api.exceptions.NotFoundMailingListClientException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

@DisplayName("The Mailchimp client")
public class MailchimpClientIT {

    private static final String NAME_FIELD_NAME = "name";
    private static final String ID_FIELD_NAME = "id";
    private static final String EMAIL_ADDRESS_FIELD_NAME = "emailAddress";
    private static final String SUBSCRIPTION_STATUS_FIELD_NAME = "subscriptionStatus";

    private static final String EXPECTED_SEGMENT_NAME = "test";

    private static final SubscriptionStatus PERMENANT_SUBSCRIPTION_STATUS_1 = SubscriptionStatus.SUBSCRIBED;
    private static final SubscriptionStatus PERMENANT_SUBSCRIPTION_STATUS_2 = SubscriptionStatus.SUBSCRIBED;
    private static final SubscriptionStatus PERMENANT_SUBSCRIPTION_STATUS_3 = SubscriptionStatus.UNSUBSCRIBED;

    private static String expectedUserEmail;

    private static String listId;
    private static String listName;

    private static String testDomain;

    private static String noSuchMemberEmail;

    private static String permenantEmail1;
    private static String permenantEmail2;
    private static String permenantEmail3;

    private static MailchimpClient mailchimpClient;

    private static String loadProperty(Properties properties, String key) {
        final String EXCEPTION_MESSAGE = ""
                + "\"{}\" was not specified in the application.test.properties file. This key is required to run these"
                + " tests. This file should be placed in the directory that the tests are running from.";

        String value = properties.getProperty(key);

        if (value == null || value.isEmpty()) {
            throw new RuntimeException(EXCEPTION_MESSAGE.replace("{}", key));
        }

        return value;
    }

    @BeforeAll
    public static void setupClass() throws IOException {
        Properties properties = new Properties();

        File file = new File("application.test.properties");

        properties.load(new FileInputStream(file));

        String mailchimpApiKey =  loadProperty(properties, "mailchimp.api.key");

        mailchimpClient = MailchimpClient.getInstance(new MailchimpClientConfiguration(mailchimpApiKey));

        listId = loadProperty(properties, "mailchimp.list.id");

        listName = loadProperty(properties, "mailchimp.list.name");

        testDomain = loadProperty(properties, "mailchimp.test.domain");

        permenantEmail1 = "permenant.test.1@" + testDomain;
        permenantEmail2 = "permenant.test.2@" + testDomain;
        permenantEmail3 = "permenant.test.3@" + testDomain;

        noSuchMemberEmail = "no.such.member@" + testDomain;

    }

    @BeforeEach
    public void generateRandomUser() throws IOException {
        Random random = new Random();

        expectedUserEmail = random.ints(20)
                .map(i -> Math.abs(i % 26) + 97)
                .mapToObj(i -> Character.toString((char) i))
                .reduce("", (s, i) -> s + i) + "@test.stfc.co.uk";

    }

    @Nested
    @DisplayName("for lists")
    public class Lists {

        @Test
        @DisplayName("is able to get all lists")
        public void getAllListDescriptors() throws MailingListClientException {

            MailingListDescriptors descriptors = mailchimpClient.getAllListDescriptors();

            assertThat(descriptors.getLists())
                    .extracting(ID_FIELD_NAME, NAME_FIELD_NAME)
                    .contains(tuple(listId, listName));
        }

        @Test
        @DisplayName("is able to get a list by ID")
        public void getListDescriptor() throws MailingListClientException {
            MailingListDescriptor descriptor = mailchimpClient.getListDescriptor(listId);
            assertThat(descriptor.getId()).isEqualTo(listId);
            assertThat(descriptor.getName()).isEqualTo(listName);

        }

        @Nested
        @DisplayName("for members")
        public class Members {

            @Nested
            @DisplayName("when retrieving")
            public class Retriving {
                @BeforeEach
                public void ensureRandomUserExists() throws MailingListClientException {

                    addExpectedUser();
                }

                @AfterEach
                public void ensureRandomUserIsRemoved() throws MailingListClientException {
                    removeExpectedUser();
                }

                @Test
                @DisplayName("is able to get all members of a list by list ID")
                public void getMembersByList() throws MailingListClientException {
                    MailingListMembers mailingListMembers = mailchimpClient.getMembersByList(listId);
                    assertThat(mailingListMembers.getMembers())
                            .extracting(EMAIL_ADDRESS_FIELD_NAME, SUBSCRIPTION_STATUS_FIELD_NAME)
                            .contains(
                                    tuple(permenantEmail1, PERMENANT_SUBSCRIPTION_STATUS_1),
                                    tuple(permenantEmail2, PERMENANT_SUBSCRIPTION_STATUS_2),
                                    tuple(permenantEmail3, PERMENANT_SUBSCRIPTION_STATUS_3)
                            );

                }

                @Test
                @DisplayName("is able to get a member of a list by list ID and member email")
                public void getMemberOfList() throws MailingListClientException {
                    MailingListMember member = mailchimpClient.getMemberOfList(listId, expectedUserEmail);

                    assertThat(member.getEmailAddress()).isEqualTo(expectedUserEmail);
                }

                @Test
                @DisplayName("throws a NotFoundMailingListClientException when a member cannot be found in a list")
                public void getMemberOfList_notFound() throws MailingListClientException {
                    assertThatThrownBy(() -> mailchimpClient.getMemberOfList(listId, noSuchMemberEmail))
                            .isInstanceOf(NotFoundMailingListClientException.class);
                }

            }

            @Nested
            @DisplayName("when deleting")
            public class Deleting {

                @Nested
                @DisplayName("can delete")
                public class Successfully {

                    @BeforeEach
                    public void ensureRandomUserExists() throws MailingListClientException {

                        addExpectedUser();
                    }

                    @Test
                    @DisplayName("members from a list")
                    public void deleteMemberFromList() throws MailingListClientException {
                        mailchimpClient.deleteMemberFromList(listId, expectedUserEmail);
                    }
                }

                @Nested
                @DisplayName("a non-existant member")
                public class NonExistantMember {

                    @Test
                    @DisplayName("can't be deleted")
                    public void deleteMemberFromList() throws MailingListClientException {
                        assertThatThrownBy(() -> mailchimpClient.deleteMemberFromList(listId, expectedUserEmail))
                                .isInstanceOf(NotFoundMailingListClientException.class);
                    }
                }
            }



        }
    }

    @Nested
    @DisplayName("when changing the subscription status of")
    public class Subscribing {

        @Nested
        @DisplayName("a new user")
        public class New {

            @Test
            @DisplayName("to be subscribed their status should be subscribed")
            public void subscribeUser() throws MailingListClientException {
                MailingListMember member = mailchimpClient.subscribeMember(listId, expectedUserEmail);
                assertThat(member.getSubscriptionStatus()).isEqualTo(SubscriptionStatus.SUBSCRIBED);

                removeExpectedUser();
            }

            @Test
            @DisplayName("to be unsubscribed their status should be unsubscribed")
            public void unsubscribeUser() throws MailingListClientException {
                MailingListMember member = mailchimpClient.unsubscribeMember(listId, expectedUserEmail);
                assertThat(member.getSubscriptionStatus()).isEqualTo(SubscriptionStatus.UNSUBSCRIBED);

                removeExpectedUser();
            }

        }

        @Nested
        @DisplayName("an existing user")
        public class Existing {

            @BeforeEach
            public void ensureUserExists() throws MailingListClientException {
                addExpectedUser();
            }

            @AfterEach
            public void ensureUserRemoved() throws MailingListClientException {
                removeExpectedUser();
            }

            @Test
            @DisplayName("to be force subscribed their status should be subscribed")
            public void forceSubscribeUser() throws MailingListClientException {
                MailingListMember member = mailchimpClient.forceSubscribeMember(listId, expectedUserEmail);
                assertThat(member.getSubscriptionStatus()).isEqualTo(SubscriptionStatus.SUBSCRIBED);
            }

            @Test
            @DisplayName("to be force unsubscribed their status should be unsubscribed")
            public void forceUnsubscribeUser() throws MailingListClientException {
                MailingListMember member = mailchimpClient.forceUnsubscribeMember(listId, expectedUserEmail);
                assertThat(member.getSubscriptionStatus()).isEqualTo(SubscriptionStatus.UNSUBSCRIBED);
            }
        }

    }

    @Nested
    @DisplayName("for segment")
    public class Segments {


        @Nested
        @DisplayName("creation")
        public class New {

            private String segmentId;

            @AfterEach
            public void ensureSegmentDeleted() throws MailingListClientException {
                mailchimpClient.deleteSegment(listId, segmentId);
            }

            @Test
            @DisplayName("the segment is created successfully")
            public void createSegment() throws MailingListClientException {
                MailingListSegmentDescriptor descriptor = mailchimpClient.createSegment(
                        listId, EXPECTED_SEGMENT_NAME);

                segmentId = descriptor.getId();
            }
        }

        @Nested
        @DisplayName("retrieval")
        public class Existing {

            private String segmentId;

            @BeforeEach
            public void ensureSegmentExists() throws MailingListClientException {
                segmentId = mailchimpClient.createSegment(listId, EXPECTED_SEGMENT_NAME).getId();

            }

            @AfterEach
            public void ensureSegmentDeleted() throws MailingListClientException {
                mailchimpClient.deleteSegment(listId, segmentId);
            }

            @Test
            @DisplayName("all of the segment descriptors are returned correctly")
            public void getSegmentDescriptors() throws MailingListClientException {
                MailingListSegmentDescriptors descriptors = mailchimpClient.getSegmentDescriptors(listId);

                assertThat(descriptors.getSegmentDescriptors())
                        .extracting(NAME_FIELD_NAME, ID_FIELD_NAME)
                        .contains(tuple(EXPECTED_SEGMENT_NAME, segmentId));
            }

            @Test
            @DisplayName("the correct segment, by given ID, is returned correctly")
            public void getSegmentDescriptor() throws MailingListClientException {
                mailchimpClient.getSegmentDescriptor(listId, segmentId);
                MailingListSegmentDescriptor descriptor = mailchimpClient.getSegmentDescriptor(listId, segmentId);

                assertThat(descriptor.getId()).isEqualTo(segmentId);
                assertThat(descriptor.getName()).isEqualTo(EXPECTED_SEGMENT_NAME);
            }
        }

        @Nested
        @DisplayName("members")
        public class Members {

            MailingListSegmentDescriptor descriptor;

            @BeforeEach
            public void ensureUserExists() throws MailingListClientException {
                addExpectedUser();

                descriptor = mailchimpClient.createSegment(listId, EXPECTED_SEGMENT_NAME);

            }

            @AfterEach
            public void ensureUserRemoved() throws MailingListClientException {
                removeExpectedUser();

                mailchimpClient.deleteSegment(listId, descriptor.getId());
            }

            @Nested
            @DisplayName("which are new")
            public class New {

                @Test
                @DisplayName("the members are correctly added to the segment")
                public void addMembersToSegment() throws MailingListClientException {

                    MailingListSegmentMemberChanges changes = mailchimpClient.addMembersToSegment(
                            listId,
                            descriptor.getId(),
                            new HashSet<>(Arrays.asList(expectedUserEmail))
                    );

                    assertThat(changes.getAddedMembers())
                            .extracting(EMAIL_ADDRESS_FIELD_NAME)
                            .contains(expectedUserEmail);

                }
            }

            @Nested
            @DisplayName("which are existing")
            public class Existing {

                @BeforeEach
                public void ensureMemberIsAddedToSegment() throws MailingListClientException {
                    mailchimpClient.addMembersToSegment(
                            listId,
                            descriptor.getId(),
                            new HashSet<>(Arrays.asList(expectedUserEmail))
                    );
                }

                @Test
                @DisplayName("they can be retrieved")
                public void getMembersOfSegment() throws MailingListClientException {

                    MailingListMembers members = mailchimpClient.getMembersOfSegment(
                            listId,
                            descriptor.getId()
                    );

                    assertThat(members.getMembers())
                            .extracting(EMAIL_ADDRESS_FIELD_NAME)
                            .contains(expectedUserEmail);
                }

                @Test
                @DisplayName("they can be removed")
                public void removeMembersFromSegment() throws MailingListClientException {

                    MailingListSegmentMemberChanges changes = mailchimpClient.removeMembersFromSegment(
                            listId,
                            descriptor.getId(),
                            new HashSet<>(Arrays.asList(expectedUserEmail))
                    );

                    assertThat(changes.getRemovedMembers())
                            .extracting(EMAIL_ADDRESS_FIELD_NAME)
                            .contains(expectedUserEmail);

                }
            }
        }

    }

    private static void addExpectedUser() throws MailingListClientException {
        mailchimpClient.subscribeMember(listId, expectedUserEmail);
    }

    private static void removeExpectedUser() throws MailingListClientException {
        mailchimpClient.deleteMemberFromList(listId, expectedUserEmail);
    }


}
