package de.tak.member;

import de.tak.activity.Opportunity;

import java.util.Optional;

/**
 * @author Heiko Braun
 * @since 07/07/15
 */
public interface MembershipService {
    void chargeActivity(Opportunity opportunity, Member member, double amount);

    Optional<Member> findMember(String id);

    Optional<Member> findMember(String firstname, String lastname);

}
