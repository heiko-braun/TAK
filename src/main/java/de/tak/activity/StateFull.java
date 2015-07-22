package de.tak.activity;

import de.tak.member.Member;

/**
 * @author Heiko Braun
 * @since 22/07/15
 */
public class StateFull extends AbstractState implements ParticipationState {
    @Override
    public ID getId() {
        return ID.FULL;
    }

    @Override
    public void registerParticipant(ParticipationContext context, Member member) {
        throw new RuntimeException("State FULL doesn't accept further participants");
    }
}
