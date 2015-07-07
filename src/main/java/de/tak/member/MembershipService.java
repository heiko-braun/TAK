package de.tak.member;

import de.tak.activity.Opportunity;

/**
 * @author Heiko Braun
 * @since 07/07/15
 */
public interface MembershipService {
    void chargeActivity(Opportunity opportunity, Member member, double amount);

    Member findMember(String id);
}
