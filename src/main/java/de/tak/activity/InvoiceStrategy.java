package de.tak.activity;

import de.tak.member.Member;

/**
 * @author Heiko Braun
 * @since 22/07/15
 */
public interface InvoiceStrategy {

    double calculateFee(Member member, Activity activity);
}
