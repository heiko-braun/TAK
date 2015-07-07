package de.tak.activity;

import de.tak.member.Invoice;
import de.tak.member.Member;
import de.tak.member.MembershipService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * @author Heiko Braun
 * @since 07/07/15
 */
public class ActivityRegistrationTest {


    private MembershipService membershipService;
    private Catalogue catalogue;
    private Opportunity opportunity;
    private Member member;
    private Activity activity;

    @Before
    public void setup() {
        //fixture declarations
        membershipService = new MockMemberShipService();
        catalogue = new Catalogue(membershipService);

        member = new Member(UUID.randomUUID().toString(), "firstname", "lastname");
        activity = new Activity(20.00);
        opportunity = new Opportunity(UUID.randomUUID().toString(),activity);

    }

    /**
     * Test the registration on an Opportunity with on OpportunityStatus.OPEN slot.
     * After successful registration the status should be changed to OpportunityStatus.WAITLIST
     */
    @Test
    public void testRegistrationOpen() {

        // extended fixture
        opportunity.setConstraint(new ParticipationConstraint() {

            private int numParticipants = 0;
            private OpportunityStatus nextState = OpportunityStatus.OPEN;

            @Override
            public boolean doesAccept(Member member) {

                // only accepts one participant
                if(0==numParticipants) {
                    numParticipants = 1;
                    nextState = OpportunityStatus.FULL;
                    return true;
                }

                return false;
            }

            @Override
            public OpportunityStatus nextState(Member member) {
                return nextState;
            }
        });

         /*
            Postcondition:
            -- member is registered for opportunity
        */

        Opportunity updatedOpportunity = catalogue.registerForActivity(this.opportunity, member);

        Assert.assertTrue(
                "Member not registered with opportunity",
                updatedOpportunity.getParticipantKeys().contains(member.getId())
        );


        // -- invoice is created and linked to member

        Member updatedMember = membershipService.findMember(member.getId());
        boolean matchingInvoice  =
                updatedMember.getInvoices().stream()
                .anyMatch(i -> i.getOpportunityId().equals(opportunity.getId())
                );

        Assert.assertTrue(
                "Invoice has not been created and linked to member",
                matchingInvoice
        );

        // -- opportunity status is updated to reflect to participation constraints
        Assert.assertEquals(
                "The opportunity status has not been correctly updated",
                OpportunityStatus.FULL,
                updatedOpportunity.getStatus()
        );

    }

    private class MockMemberShipService implements MembershipService {
        @Override
        public void chargeActivity(Opportunity opportunity, Member member, double amount) {
            Invoice invoice = new Invoice(
                    opportunity.getId(),
                    member.getId(),
                    amount
            );

            member.addInvoice(invoice);
        }

        @Override
        public Member findMember(String id) {
            if(ActivityRegistrationTest.this.member.getId().equals(id))
                return ActivityRegistrationTest.this.member;
            else
                return null;
        }
    }
}