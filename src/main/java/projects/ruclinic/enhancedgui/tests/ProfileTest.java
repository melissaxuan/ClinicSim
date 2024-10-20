package tests;
import org.junit.Test;
import ruclinic.Profile;
import util.Date;

import static org.junit.Assert.assertTrue;
/**
 * Testclass for the class profiles compareTo method
 * @author Michael Ehresman
 */
public class ProfileTest {

    /**
     * Tests the compareTo() method in the Profile class
     */
    @Test
    public void compareTo()
    {
        Profile p1 = new Profile("James", "Smith", new Date("01/01/2000"));
        Profile p2 = new Profile("John", "Doe", new Date("12/13/1989"));
        Profile p3 = new Profile("James", "Smith", new Date("01/01/2000"));
        Profile p4 = new Profile("John", "Smith", new Date("12/13/1989"));
        Profile p5 = new Profile("John", "Smith", new Date("12/14/1989"));
        Profile p6 = new Profile("John", "Doe", new Date("1/13/1989"));

        assertTrue(p1.compareTo(p2)>0); //Smith comes after Doe
        assertTrue(p2.compareTo(p1)<0); //Doe comes before Smith
        assertTrue(p1.compareTo(p4)<0); //James comes before John
        assertTrue(p4.compareTo(p1)>0); //John comes after James
        assertTrue(p2.compareTo(p6)>0); //12/13/1989 is later than 1/13/1989
        assertTrue(p4.compareTo(p5)<0); //12/13/1989 is earlier than 12/14/1989
        assertTrue(p3.compareTo(p1)==0); //Same profile details

    }


}