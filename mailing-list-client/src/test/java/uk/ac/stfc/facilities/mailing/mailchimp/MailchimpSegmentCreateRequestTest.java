package uk.ac.stfc.facilities.mailing.mailchimp;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class MailchimpSegmentCreateRequestTest {

    @Test
    public void whichCreatesASegment() {
        MailchimpSegmentCreateRequest request = MailchimpSegmentCreateRequest.whichCreatesASegment("test");

        assertThat(request.getName()).isEqualTo("test");
    }

}
