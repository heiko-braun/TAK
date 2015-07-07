package de.tak.activity;

import de.tak.member.Member;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Heiko Braun
 * @since 06/07/15
 */
public class Opportunity implements Comparable<Opportunity> {

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
    private final DateTime from;
    private final DateTime to;
    private Map<String, Member> participants = new HashMap<>();
    private String id;

    private ParticipationConstraint constraint = NO_CONSTRAINT;
    private Instructor instructor;

    public Opportunity(String id, Activity activity, DateTime from, DateTime to) {
        this.id = id;
        this.activity = activity;
        this.from = from;
        this.to = to;
    }

    public Opportunity(String id, Activity activity) {
        this(id, activity, null, null);
    }

    public DateTime getFrom() {
        return from;
    }

    public DateTime getTo() {
        return to;
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

    public void setInstructor(Instructor instructor) {

        this.instructor = instructor;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    @Override
    public int compareTo(Opportunity o) {
        return this.from.compareTo(o.getFrom());
    }

    @Override
    public String toString() {
        return "Opportunity{" +
                "id='" + id + '\'' +
                ", from=" + from +
                '}';
    }
}
