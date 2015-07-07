package de.tak.activity;

import de.tak.member.Member;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Heiko Braun
 * @since 06/07/15
 */
public class Opportunity {

    private static final ParticipationConstraint NO_CONSTRAINT = new ParticipationConstraint() {
        @Override
        public boolean doesAccept(Member member) {
            return true;
        }

        @Override
        public OpportunityStatus nextState(Member member) {
            return OpportunityStatus.OPEN;
        }
    };

    private OpportunityStatus status = OpportunityStatus.OPEN;
    private Activity activity;
    private Map<String, Member> participants = new HashMap<>();
    private String id;

    private ParticipationConstraint constraint = NO_CONSTRAINT;

    public Opportunity(String id, Activity activity) {
        this.id = id;
        this.activity = activity;
    }

    public OpportunityStatus getStatus() {
        return status;
    }

    public void registerParticipant(Member member) {

        assert constraint.doesAccept(member);
        participants.put(member.getId(), member);
        this.status = constraint.nextState(member);
    }

    public Activity getActivity() {
        return activity;
    }

    public Set<String> getParticipantKeys() {
        return participants.keySet();
    }

    public String getId() {
        return id;
    }

    public void setConstraint(ParticipationConstraint constraint) {
        this.constraint = constraint;
    }

    public ParticipationConstraint getConstraint() {
        return constraint;
    }
}
