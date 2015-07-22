package de.tak.activity;

import de.tak.member.Member;
import org.joda.time.DateTime;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

/**
 * @author Heiko Braun
 * @since 06/07/15
 */
public class Activity {

    private InvoiceStrategy invoiceStrategy;
    private double baseFee;

    private SortedSet<Opportunity> opportunities = new TreeSet<>();

    public Activity(InvoiceStrategy invoiceStrategy, double baseFee) {
        this.invoiceStrategy = invoiceStrategy;
        this.baseFee = baseFee;
    }

    public double getBaseFee() {
        return baseFee;
    }

    public Opportunity createOpportunity(DateTime dateFrom, DateTime dateTo, Instructor instructor) {

        assert dateFrom.isBefore(dateTo) : "dateFrom needs to be before dateTo";

        Opportunity opportunity = new Opportunity(UUID.randomUUID().toString(), this, dateFrom, dateTo);
        opportunity.setInstructor(instructor);
        this.opportunities.add(opportunity);
        return opportunity;
    }

    public Set<Opportunity> getOpportunities() {
        return opportunities;
    }

    public double getFee(Member member) {
        return invoiceStrategy.calculateFee(member, this);
    }
}
