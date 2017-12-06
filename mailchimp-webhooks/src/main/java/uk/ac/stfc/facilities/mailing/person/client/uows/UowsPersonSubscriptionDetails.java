package uk.ac.stfc.facilities.mailing.person.client.uows;

import uk.ac.stfc.facilities.mailing.person.client.PersonSubscriptionDetails;
import uk.stfc.userOfficeClient.PersonDTO;

/**
 * Person subscription details that link up to a
 * <code>UserOfficeWebService</code> <code>PersonDTO</code>.
 */
public class UowsPersonSubscriptionDetails implements PersonSubscriptionDetails {
    private final PersonDTO personDTO;

    /**
     * Creates a <code>UowsPersonSubscriptionDetails</code> with the
     * given person as its ID.
     *
     * @param personDTO the underlying person to connect the details to
     */
    public UowsPersonSubscriptionDetails(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }

    @Override
    public String getMailingEmail() {
        return personDTO.getMarketingEmail();
    }

    @Override
    public boolean isSubscribed() {
        return personDTO.isSubscribedToMarketingEmails();
    }

    @Override
    public void setMailingEmail(String email) {
        personDTO.setMarketingEmail(email);
    }

    @Override
    public void setSubscribed(boolean subscribed) {
        personDTO.setSubscribedToMarketingEmails(subscribed);
    }

    @Override
    public Object getId() {
        return personDTO.getUserNumber();
    }

    /**
     * Gets the underlying person that the details are connected to.
     *
     * @return the underlying person that the details are connected to
     */
    public PersonDTO getPersonDTO() {
        return personDTO;
    }
}
