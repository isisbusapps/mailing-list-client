package uk.ac.stfc.facilities.mailing.mailchimp;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class MailchimpSegmentMemberRequestTest {


    @Test
    public void whichAddsMembers() {
        MailchimpSegmentMemberRequest request = MailchimpSegmentMemberRequest.whichAddsMembers(
                new HashSet<>(Arrays.asList("test", "email"))
        );

        assertThat(request.getMembersToAdd()).contains("test", "email");
        assertThat(request.getMembersToRemove()).isNullOrEmpty();
    }

    @Test
    public void whichRemovesMembers() {
        MailchimpSegmentMemberRequest request = MailchimpSegmentMemberRequest.whichRemovesMembers(
                new HashSet<>(Arrays.asList("test", "email"))
        );

        assertThat(request.getMembersToRemove()).contains("test", "email");
        assertThat(request.getMembersToAdd()).isNullOrEmpty();
    }

}
