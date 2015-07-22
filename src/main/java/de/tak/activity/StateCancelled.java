package de.tak.activity;

import de.tak.member.Member;

/**
 * @author Heiko Braun
 * @since 22/07/15
 */
public class StateCancelled extends AbstractState implements ParticipationState {

    
    @Override
    public ID getId() {
        return ID.CANCELLED;
    }

    @Override
    public void registerParticipant(ParticipationContext context, Member member) {
        throw new RuntimeException("State CANCELLED doesn't accept further participants");
    }
}
