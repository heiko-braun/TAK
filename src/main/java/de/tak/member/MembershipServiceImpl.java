package de.tak.member;

import de.tak.activity.Opportunity;

import java.util.Optional;

/**
 * @author Heiko Braun
 * @since 06/07/15
 */
public class MembershipServiceImpl implements MembershipService {

    @Override
    public void chargeActivity(Opportunity opportunity, Member member, double amount) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public Optional<Member> findMember(String id) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public Optional<Member> findMember(String firstname, String lastname) {
        throw new IllegalStateException("Not implemented");
    }
}
