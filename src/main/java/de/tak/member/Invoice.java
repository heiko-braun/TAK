package de.tak.member;

/**
 * @author Heiko Braun
 * @since 06/07/15
 */
public class Invoice {

    private String opportunityId;
    private String memberId;
    private double amount;

    public Invoice(String opportunityId, String memberId, double amount) {
        this.opportunityId = opportunityId;
        this.memberId = memberId;
        this.amount = amount;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public String getMemberId() {
        return memberId;
    }
}
