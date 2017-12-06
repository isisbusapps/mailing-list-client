package uk.ac.stfc.facilities.mailing.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.ac.stfc.facilities.mailing.person.client.PersonSubscriptionClient;
import uk.ac.stfc.facilities.mailing.person.client.PersonSubscriptionClientException;
import uk.ac.stfc.facilities.mailing.person.client.PersonSubscriptionDetails;
import uk.ac.stfc.facilities.mailing.person.client.uows.UowsPersonSubscriptionDetails;
import uk.stfc.userOfficeClient.PersonDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 *
 */
public class MarketingSubscriptionManagerTest {

    private static final String OLD_EMAIL = "old email";
    private static final String NEW_EMAIL = "new email";

    private static final String UNSUBSCRIBE_EMAIL = "unsubscribe email address";

    private static final String PERSON_FIRST_NAME = "Hello";
    private static final String PERSON_FAMILY_NAME = "World";

    private PersonSubscriptionClient<PersonSubscriptionDetails> personSubscriptionClientMock;
    private MarketingSubscriptionManager marketingSubscriptionManager;

    @BeforeEach
    public void setupMarketingSubscriptionManager() {

        personSubscriptionClientMock = mock(PersonSubscriptionClient.class);

        marketingSubscriptionManager = new MarketingSubscriptionManager(personSubscriptionClientMock);

    }

    @Test
    public void updateEmail_validChange_updatesEmailUsingThePersonDetailsClient() throws PersonSubscriptionClientException {

        when(personSubscriptionClientMock.getPersonByMarketingEmail(OLD_EMAIL)).thenReturn(buildOldPerson());

        marketingSubscriptionManager.updateEmail(OLD_EMAIL, NEW_EMAIL);

        ArgumentCaptor<UowsPersonSubscriptionDetails> argument = ArgumentCaptor.forClass(UowsPersonSubscriptionDetails.class);

        verify(personSubscriptionClientMock).savePerson(argument.capture());

        assertThat(argument.getValue()).isEqualToComparingFieldByFieldRecursively(buildSavedChangedEmailPerson());

    }

    @Test
    public void unsubscribeUser_validUnsubscription_unsubscribesPersonUsingThePersonDetailsClient() throws PersonSubscriptionClientException {
        when(personSubscriptionClientMock.getPersonByMarketingEmail(UNSUBSCRIBE_EMAIL)).thenReturn(buildSubscribedPerson());

        marketingSubscriptionManager.unsubscribeUser(UNSUBSCRIBE_EMAIL);

        ArgumentCaptor<UowsPersonSubscriptionDetails> argument = ArgumentCaptor.forClass(UowsPersonSubscriptionDetails.class);

        verify(personSubscriptionClientMock).savePerson(argument.capture());

        assertThat(argument.getValue()).isEqualToComparingFieldByFieldRecursively(buildSavedUnsubscribedPerson());
    }

    private UowsPersonSubscriptionDetails buildSavedUnsubscribedPerson() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName(PERSON_FIRST_NAME);
        personDTO.setFamilyName(PERSON_FAMILY_NAME);
        personDTO.setMarketingEmail(null);
        personDTO.setSubscribedToMarketingEmails(false);

        return new UowsPersonSubscriptionDetails(personDTO);
    }

    private UowsPersonSubscriptionDetails buildSubscribedPerson() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName(PERSON_FIRST_NAME);
        personDTO.setFamilyName(PERSON_FAMILY_NAME);
        personDTO.setMarketingEmail(UNSUBSCRIBE_EMAIL);
        personDTO.setSubscribedToMarketingEmails(true);

        return new UowsPersonSubscriptionDetails(personDTO);
    }

    private UowsPersonSubscriptionDetails buildOldPerson() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName(PERSON_FIRST_NAME);
        personDTO.setFamilyName(PERSON_FAMILY_NAME);
        personDTO.setMarketingEmail(OLD_EMAIL);
        return new UowsPersonSubscriptionDetails(personDTO);
    }

    private UowsPersonSubscriptionDetails buildSavedChangedEmailPerson() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName(PERSON_FIRST_NAME);
        personDTO.setFamilyName(PERSON_FAMILY_NAME);
        personDTO.setMarketingEmail(NEW_EMAIL);
        return new UowsPersonSubscriptionDetails(personDTO);
    }

}
