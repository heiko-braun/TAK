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

        Opportunity opportunity = catalogue.scheduleOpportunity(activity, from, to, instructor);

        // -- new opportunity is created
        Assert.assertNotNull(opportunity);
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
}
