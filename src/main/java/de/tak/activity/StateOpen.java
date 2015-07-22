package de.tak.activity;

import de.tak.member.Member;

/**
 * @author Heiko Braun
 * @since 22/07/15
 */
public class StateOpen extends AbstractState implements ParticipationState {

    private final int limit;

    public StateOpen() {
        this.limit = DefaultConstraints.MAX_PARTICIPANTS;
    }

    public StateOpen(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public ID getId() {
        return ID.OPEN;
    }

    @Override
    public void registerParticipant(ParticipationContext context, Member member) {

        // member registration
        if(context.getNumParticipants()< limit)
        {
            context.addMember(member);
        }

        // state transition
        if(context.getNumParticipants()== limit)
            context.setState(new StateWaitlist());

    }
}
