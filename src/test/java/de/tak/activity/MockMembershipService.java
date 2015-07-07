package de.tak.activity;

import de.tak.member.Invoice;
import de.tak.member.Member;
import de.tak.member.MembershipService;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Heiko Braun
 * @since 07/07/15
 */
public class MockMembershipService implements MembershipService {

    private Member johnDoe = new Member(UUID.randomUUID().toString(), "john", "doe");

    @Override
    public void chargeActivity(Opportunity opportunity, Member member, double amount) {
        Invoice invoice = new Invoice(
                opportunity.getId(),
                member.getId(),
                amount
        );

        member.addInvoice(invoice);
    }

    @Override
    public Optional<Member> findMember(String id) {
        if(johnDoe.getId().equals(id))
        {
            return Optional.of(johnDoe);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Member> findMember(String firstname, String lastname) {
        if(johnDoe.getFirstname().equals(firstname)
                && johnDoe.getLastname().equals(lastname))
        {
            return Optional.of(johnDoe);
        }

        return Optional.empty();
    }
}
