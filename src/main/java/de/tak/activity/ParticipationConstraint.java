package de.tak.activity;

import de.tak.member.Member;

/**
 * @author Heiko Braun
 * @since 07/07/15
 */
public interface ParticipationConstraint {
    boolean doesAccept(Member member);
    OpportunityStatus nextState(Member member);
}
