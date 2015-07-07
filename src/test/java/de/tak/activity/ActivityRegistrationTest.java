package de.tak.activity;

import de.tak.member.Member;
import de.tak.member.MembershipService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
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
        membershipService = new MockMembershipService();
        catalogue = new Catalogue(membershipService);

        member = membershipService.findMember("john", "doe").get();

        activity = new Activity(20.00);
        opportunity = new Opportunity(UUID.randomUUID().toString(), activity);

    }

    /**
     * Test the registration of participants
     * (using a simplified text fixture for participation constraints)
     *
     * @see #testOpportunityStateChanges()
     */
    @Test
    public void testParticipantRegistration() {

        // extended fixture: limit participation to a single member
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

        Opportunity updatedOpportunity = catalogue.registerForActivity(
                this.opportunity, member
        );

        Assert.assertTrue(
                "Member not registered with opportunity",
                updatedOpportunity.getParticipantKeys().contains(member.getId())
        );


        // -- invoice is created and linked to member

        Optional<Member> memberResult =
                membershipService.findMember(this.member.getId());
        Assert.assertTrue(memberResult.isPresent());

        Member updatedMember = memberResult.get();
        boolean matchingInvoice  =
                updatedMember.getInvoices().stream()
                        .anyMatch(i -> i.getOpportunityId()
                                        .equals(opportunity.getId())
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

    /**
     * Verify the state changes that occur to opportunity instances
     * as a result of registration of participants.
     *
     * @see ParticipationConstraint
     */
    @Test
    public void testOpportunityStateChanges() {

        // test open -> wait list transition

        opportunity.setConstraint(new ParticipationConstraint() {

            private int numParticipants = 0;
            private OpportunityStatus nextState = OpportunityStatus.OPEN;

            @Override
            public boolean doesAccept(Member member) {

                // only accepts one participant
                if(0==numParticipants) {
                    numParticipants = 1;
                    nextState = OpportunityStatus.WAITLIST;
                    return true;
                }

                return false;
            }

            @Override
            public OpportunityStatus nextState(Member member) {
                return nextState;
            }
        });

        // transition to wait list
        opportunity.registerParticipant(member);
        Assert.assertEquals(
                OpportunityStatus.WAITLIST,
                opportunity.getStatus()
        );

        // member already registered and participation limit 1
        boolean yieldsException = false;
        try {
            opportunity.registerParticipant(member);
        } catch (Throwable t) {
            yieldsException = true;
        }

        Assert.assertTrue(
                "Expected an exception to be raised",
                yieldsException
        );

    }

}
