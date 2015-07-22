package de.tak.activity;

import de.tak.member.Member;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Heiko Braun
 * @since 06/07/15
 */
public class Opportunity extends ParticipationContext implements Comparable<Opportunity> {

    private Activity activity;
    private final DateTime from;
    private final DateTime to;
    private Map<String, Member> participants = new HashMap<>();
    private Map<String, Member> waitList = new HashMap<>();
    private String id;

    private ParticipationState state;
    private Instructor instructor;

    public Opportunity(String id, Activity activity, DateTime from, DateTime to) {
        this.id = id;
        this.activity = activity;
        this.from = from;
        this.to = to;
        this.state = new StateOpen();
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

    public void registerParticipant(Member member) {
        state.registerParticipant(this, member);
    }

    public ParticipationState.ID getState() {
        return state.getId();
    }

    void setState(ParticipationState next) {
        this.state = next;
    }

    void addMember(Member member) {
        this.participants.put(member.getId(), member);
    }

    @Override
    void addWaitlistMember(Member member) {
        this.waitList.put(member.getId(), member);
    }

    // -- end participation state --

    public Activity getActivity() {
        return activity;
    }

    public Set<String> getParticipantKeys() {
        return participants.keySet();
    }

    public String getId() {
        return id;
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
    int getNumParticipants() {
        return participants.size();
    }


    @Override
    int getNumWaitlist() {
        return waitList.size();
    }

    @Override
    public String toString() {
        return "Opportunity{" +
                "id='" + id + '\'' +
                ", from=" + from +
                '}';
    }
}
