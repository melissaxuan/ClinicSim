package projects.ruclinic.enhancedgui.tests;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import projects.ruclinic.enhancedgui.util.Date;

/**
 * Test class for the dates class isValid() method
 * @author Michael Ehresman
 */

public class DateTest {

    /**
     * Test the isValid() method in the Date class
     */
    @Test
    public void testisValid() {
        Date dateTest1 = new Date("2/29/2023");
        assertFalse(dateTest1.isValid());//should be false cause not leap year
        Date dateTest2 = new Date("13/8/2016");
        assertFalse(dateTest2.isValid());//should be false cause no 13th month
        Date dateTest3 = new Date("4/0/2020");//should be false no 0th day
        assertFalse(dateTest3.isValid());
        Date dateTest4 = new Date("9/31/2020");
        assertFalse(dateTest4.isValid());//should be false september has 30 days
        Date dateTest5 = new Date("9/29/2022");//should be true
        assertTrue(dateTest5.isValid());
        Date dateTest6 = new Date("2/29/2020");//should be true cause leap year
        assertTrue(dateTest6.isValid());
    }

}

