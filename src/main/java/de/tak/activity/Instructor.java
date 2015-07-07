package de.tak.activity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Heiko Braun
 * @since 06/07/15
 */
public class Instructor {

    private final String firstname;
    private final String lastname;
    private Set<Qualification> qualifications = new HashSet<>();

    public Instructor(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public boolean doesQualifyFor(Activity activity) {
        Optional<Qualification> match = qualifications.stream()
                .filter(q -> q.matches(activity))
                .findFirst();

        return match.isPresent();
    }

    public void addQualification(Qualification qualification) {
        this.qualifications.add(qualification);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
