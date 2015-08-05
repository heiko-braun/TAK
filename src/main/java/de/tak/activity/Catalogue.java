package de.tak.activity;

import de.tak.member.Member;
import de.tak.member.MembershipService;
import org.joda.time.DateTime;

/**
 * @author Heiko Braun
 * @since 06/07/15
 * @version
 */
public class Catalogue {

    private MembershipService membershipService;

    public Catalogue(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    /**
     * Registers a member for an opportunity and creates an invoice
     */
    public Opportunity registerForActivity(Opportunity opportunity, Member member) {

        /*
            Precondition:
            -- member is a member
            -- opportunity is recorded in the system
            -- opportunity status is either open or waitlist
        */

        assert (ParticipationState.ID.OPEN == opportunity.getState()
                || ParticipationState.ID.WAITLIST == opportunity.getState());

        // delegates to opportunity for registration
        // and update of the participation state
        opportunity.registerParticipant(member);

        // calculate effective fee
        Activity activity = opportunity.getActivity();
        double amount = activity.getEffectiveFee(member);

        // process payment using the adjacent system
        membershipService.chargeActivity(opportunity, member, amount);

        /*
            Postcondition:
            -- member is registered for opportunity
            -- invoice is created and linked to member
            -- opportunity status is updated to reflect to participation constraints
        */

        return opportunity;

    }

    /**
     * Creates a new opportunity instance
     */
    public Opportunity scheduleOpportunity(Activity activity, DateTime dateFrom,
                                           DateTime dateTo, Instructor instructor) {

        /*
            Preconditions:
            -- instructor is recorded in the system
            -- and does qualify for the activity
            -- activity is recorded in the system
            -- instructor is not already assigned
            -- date > current date
         */

        // delegate to instructor to verify the qualifications
        // it's a pre-condition, but the assertion doubles checks the clients contract
        assert instructor.doesQualifyFor(activity);


        // create the actual opportunity
        return activity.createOpportunity(dateFrom, dateTo, instructor);

        /*
            Postconditions:
            -- new opportunity is created
            -- and all opportunities are ordered
            -- and linked to the activity
            -- and has the instructor set to the specified one
         */
    }
}
