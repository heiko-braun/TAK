package de.tak.activity;

import de.tak.member.Member;

/**
 * @author Heiko Braun
 * @since 22/07/15
 */
public class DefaultInvoiceStrategy implements InvoiceStrategy {
    @Override
    public double calculateFee(Member member, Activity activity) {
        if(member.isFullMember())
            return activity.getBaseFee();
        else
            return activity.getBaseFee()*1.25;
    }
}
