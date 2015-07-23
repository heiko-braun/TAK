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

        // participation
        opportunity.registerParticipant(member);

        // payment
        Activity activity = opportunity.getActivity();
        double amount = activity.getEffectiveFee(member);
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

        assert instructor.doesQualifyFor(activity);


        return activity.createOpportunity(dateFrom, dateTo, instructor);

        /*
            Postconditions:
            -- instructor does qualify for activity
            -- new opportunity is created
            -- and linked to the activity
            -- and has the instructor set to the specified one
         */
    }
}
