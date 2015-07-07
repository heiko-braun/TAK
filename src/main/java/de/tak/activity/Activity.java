package de.tak.activity;

/**
 * @author Heiko Braun
 * @since 06/07/15
 */
public class Activity {

    private ActivityFee fee;

    public Activity(double fee) {
        this.fee = new ActivityFee(fee);
    }

    public ActivityFee getFee() {
        return this.fee;
    }
}
