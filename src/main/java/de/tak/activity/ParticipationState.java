package de.tak.activity;

import de.tak.member.Member;

/**
 * @author Heiko Braun
 * @since 07/07/15
 */
public interface ParticipationState {
    enum ID { OPEN, WAITLIST, FULL, CONFIRMED, CANCELLED}

    ID getId();
    void registerParticipant(ParticipationContext context, Member member);
    void confirm(ParticipationContext context);
    void cancel(ParticipationContext context);
}
