package de.tak.activity;

/**
 * @author Heiko Braun
 * @since 06/07/15
 */
public class ActivityFee {

    private final double memberPrice;

    public ActivityFee(double memberPrice) {

        this.memberPrice = memberPrice;
    }

    public double getMemberPrice() {
        return memberPrice;
    }
}
