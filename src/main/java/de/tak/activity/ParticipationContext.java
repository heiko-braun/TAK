package de.tak.activity;

import de.tak.member.Member;

/**
 * @author Heiko Braun
 * @since 21/07/15
 */
public abstract class ParticipationContext {

    abstract public ParticipationState.ID getState();

    abstract void setState(ParticipationState next);

    abstract void addMember(Member member);

    abstract void addWaitlistMember(Member member);

    abstract int getNumParticipants();

    abstract int getNumWaitlist();
}
