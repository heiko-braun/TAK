package de.tak.activity;

import de.tak.member.Member;
import de.tak.member.MembershipService;

/**
 * @author Heiko Braun
 * @since 06/07/15
 */
public class Catalogue {

    private MembershipService membershipService;

    public Catalogue(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    public Opportunity registerForActivity(Opportunity opportunity, Member member) {

        /*
            Precondition:
            -- member is a member
            -- opportunity is recorded in the system
            -- opportunity status is either open or waitlist
        */

        assert opportunity.getStatus() == OpportunityStatus.OPEN;

        // participation
        opportunity.registerParticipant(member);

        // payment
        Activity activity = opportunity.getActivity();
        double amount = activity.getFee().getMemberPrice();
        membershipService.chargeActivity(opportunity, member, amount);

        /*
            Postcondition:
            -- member is registered for opportunity
            -- invoice is created and linked to member
            -- opportunity status is updated to reflect to participation constraints
        */


        return opportunity;

    }
}
