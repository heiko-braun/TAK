package de.tak.activity;

import de.tak.member.MembershipService;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Heiko Braun
 * @since 07/07/15
 */
public class ScheduleOpportunityTest {

    private static final Qualification QUALIFICATION_ANY = new Qualification() {
        @Override
        boolean matches(Activity activity) {
            return true;
        }
    };

    private MembershipService membershipService;
    private Catalogue catalogue;
    private Activity activity;
    private Instructor instructor;

    @Before
    public void setup() {
        //fixture declarations
        membershipService = new MockMembershipService();
        catalogue = new Catalogue(membershipService);

        // simplified fixture: any qualification matches
        instructor = new Instructor("peter", "post");
        instructor.addQualification(QUALIFICATION_ANY);

        activity = new Activity(20.00);
    }

    /**
     * Test the opportunity creation and linking
     * to activities and instructors.
     */
    @Test
    public void testOpportunityCreation() {

        /*
            Postconditions:
            -- instructor does qualify for activity

         */

        Assert.assertTrue(
                "Instructor does not qualify for activity",
                instructor.doesQualifyFor(activity)
        );

        DateTime from = new DateTime().plusWeeks(1);
        DateTime to = new DateTime().plusWeeks(2);

        Opportunity opportunity = catalogue.scheduleOpportunity(
                activity, from, to, instructor
        );

        // -- new opportunity is created
        // -- and has status Open
        // -- and the correct dates
        Assert.assertNotNull(opportunity);
        Assert.assertEquals(ParticipationState.ID.OPEN, opportunity.getState());
        Assert.assertEquals(opportunity.getFrom(), from);
        Assert.assertEquals(opportunity.getTo(), to);


        // -- and linked to the activity
        Assert.assertEquals(
                "Activity not linked to opportunity",
                opportunity.getActivity(), activity
        );

        // -- and has the instructor set to the specified one
        Assert.assertEquals(
                "Instructor not not linked to opportunity",
                opportunity.getInstructor(), instructor
        );

    }

    /**
     * Test the (data) constraints when creating opportunities.
     */
    @Test
    public void testOpportunityConstraints() {

        // illegal date supplied
        DateTime from = new DateTime().plusWeeks(1);
        DateTime to = from.minusWeeks(2);

        boolean yieldsException = false;
        try {
            catalogue.scheduleOpportunity(
                    activity, from, to, instructor
            );
        } catch (Throwable t) {
            yieldsException = true;
        }

        Assert.assertTrue(
                "Did not raise exception upon illegal parameters",
                yieldsException
        );

        // linked opportunities are ordered by date
        // for this test we add two opportunities in reverse order

        DateTime fromA = new DateTime().plusWeeks(1);
        DateTime toA = fromA.plusWeeks(2);

        DateTime fromB= fromA.plusWeeks(1);
        DateTime toB = fromB.plusWeeks(2);

        // B added before A
        Opportunity opportunityB = catalogue.scheduleOpportunity(
                activity, fromB, toB, instructor
        );

        // A added after B
        Opportunity opportunityA = catalogue.scheduleOpportunity(
                activity, fromA, toA, instructor
        );

        Assert.assertTrue(opportunityA.getFrom().isBefore(opportunityB.getFrom()));

        Assert.assertEquals(
                "Expected A before B although created in reverse order",
                opportunityA,
                activity.getOpportunities().iterator().next()
        );

    }
}
