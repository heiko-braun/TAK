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

        activity = new Activity(new DefaultInvoiceStrategy(), 20.00);
        opportunity = new Opportunity(UUID.randomUUID().toString(), activity);

    }

    /**
     * Test the registration of participants
     * (using a simplified text fixture for participation constraints)
     *
     * @see #testDefaultStateChanges()
     */
    @Test
    public void testParticipantRegistration() {

        // extended fixture: limit participation to a single member
        opportunity.setState(new StateOpen(1));

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
                ParticipationState.ID.WAITLIST,
                updatedOpportunity.getState()
        );

    }

    /**
     * Verify the state changes that occur to opportunity instances
     * as a result of registration of participants.
     * Does verify the state pattern with it's default values.
     *
     * @see ParticipationState
     */
    @Test
    public void testDefaultStateChanges() {

        // test open -> wait list transition

        StateOpen initialState = new StateOpen();
        opportunity.setState(initialState);

        // register max participants
        for (int limit = DefaultConstraints.MAX_PARTICIPANTS; limit > 0; limit--) {
            opportunity.registerParticipant(
                    createMember()
            );
        }

        // should have transitioned to transition to wait list
        Assert.assertEquals(
                ParticipationState.ID.WAITLIST,
                opportunity.getState()
        );


        // register max waitlist
        for (int limit = DefaultConstraints.MAX_WAITLIST; limit > 0; limit--) {
            opportunity.registerParticipant(
                    createMember()
            );
        }

        // should have transitioned to transition to wait list
        Assert.assertEquals(
                ParticipationState.ID.FULL,
                opportunity.getState()
        );

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

    private Member createMember() {
        return new Member(
                UUID.randomUUID().toString(),
                "John", "Doe"
        );
    }

}
