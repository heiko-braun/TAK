package de.tak.activity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Heiko Braun
 * @since 06/07/15
 */
public class Activity {

    private ActivityFee fee;

    private List<Opportunity> opportunities = new ArrayList<>();

    public Activity(double fee) {
        this.fee = new ActivityFee(fee);
    }

    public ActivityFee getFee() {
        return this.fee;
    }

    public Opportunity createOpportunity(DateTime dateFrom, DateTime dateTo, Instructor instructor) {
        Opportunity opportunity = new Opportunity(UUID.randomUUID().toString(), this, dateFrom, dateTo);
        opportunity.setInstructor(instructor);
        this.opportunities.add(opportunity);
        return opportunity;
    }

    public List<Opportunity> getOpportunities() {
        return opportunities;
    }
}
