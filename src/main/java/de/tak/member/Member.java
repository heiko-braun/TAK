package de.tak.member;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Heiko Braun
 * @since 06/07/15
 */
public class Member {

    private final String id;
    private final String firstname;
    private final String lastname;
    private Set<Invoice> invoices = new HashSet<>();

    public Member(String id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getId() {
        return this.id;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
    }
}
