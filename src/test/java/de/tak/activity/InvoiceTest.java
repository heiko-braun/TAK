package de.tak.activity;

import de.tak.member.Member;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Heiko Braun
 * @since 22/07/15
 */
public class InvoiceTest {

    @Test
    public void testDefaultInvoiceStrategy() {

        InvoiceStrategy strategy = new DefaultInvoiceStrategy();

        double baseFee = 10.00;
        Activity activity = new Activity(strategy, baseFee);

        Member fullMember = new Member("1234", "Full", "Member", true);
        Member guestMember = new Member("5678", "Guest", "Member", false);

        // member fees
        double fullMemberFee = activity.getFee(fullMember);

        Assert.assertEquals(
                "Expected regular fee for full members",
                baseFee,
                fullMemberFee
        );

        // guest member fees
        double guestMemberFee = activity.getFee(guestMember);

        Assert.assertEquals(
                "Expected 25% higher fee for guest members",
                baseFee*1.25,
                guestMemberFee
        );
    }
}
