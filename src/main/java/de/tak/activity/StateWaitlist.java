package de.tak.activity;

import de.tak.member.Member;

/**
 * @author Heiko Braun
 * @since 22/07/15
 */
public class StateWaitlist extends AbstractState implements ParticipationState {

    private final int limit;

    public StateWaitlist() {
        this.limit = DefaultConstraints.MAX_WAITLIST;
    }

    @Override
    public ID getId() {
        return ID.WAITLIST;
    }

    @Override
    public void registerParticipant(ParticipationContext context, Member member) {
        // member registration
        if(context.getNumWaitlist()<limit)
        {
            context.addWaitlistMember(member);
        }

        // state transition
        if(context.getNumWaitlist()==limit)
            context.setState(new StateFull());
    }
}
