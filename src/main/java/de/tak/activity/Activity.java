package de.tak.activity;

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

    private ActivityFee fee;

    private SortedSet<Opportunity> opportunities = new TreeSet<>();

    public Activity(double fee) {
        this.fee = new ActivityFee(fee);
    }

    public ActivityFee getFee() {
        return this.fee;
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
}
