package uk.ac.stfc.facilities.mailing.mailchimp;

import com.google.gson.annotations.SerializedName;
import uk.ac.stfc.facilities.mailing.api.data.MailingListSegmentDescriptors;

import java.util.Iterator;
import java.util.Set;

/**
 *
 */
class MailchimpSegmentDescriptors implements MailingListSegmentDescriptors {

    @SerializedName("segments")
    Set<MailchimpSegmentDescriptor> segmentDescriptors;

    @Override
    public Set<MailchimpSegmentDescriptor> getSegmentDescriptors() {
        return segmentDescriptors;
    }

    public Set<MailchimpSegmentDescriptor> setSegmentDescriptors() {
        return segmentDescriptors;
    }

    public Iterator<MailchimpSegmentDescriptor> iterator() {
        return segmentDescriptors.iterator();
    }

    @Override
    public String toString() {
        return "MailchimpSegmentDescriptors{" +
                "segmentDescriptors=" + segmentDescriptors +
                '}';
    }
}
