package tests;
import org.junit.Test;
import ruclinic.Doctor;
import ruclinic.Technician;
import ruclinic.Provider;
import org.junit.Before;
import util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the List classes add and remove methods
 * @author Michael Ehresman
 */
public class ListTest {
    private List<Provider> testList;
    private Doctor testDoc1;
    private Technician testTech1;

    @Before
    public void setup() throws Exception {
        testList = new List<Provider>();
        testDoc1 = new Doctor();
        testTech1 = new Technician();
    }
    /**
     * Tests the add() method in the List class
     */
    @Test
    public void testAdd() {
        testList.add(testDoc1);
        assertTrue(testList.contains(testDoc1));//test list should contain testDoc1
        testList.add(testTech1);
        assertTrue(testList.contains(testTech1));//test list should contain testTech1
    }
    /**
     * Tests the remove() method in the List class
     */
    @Test
    public void testRemove() {
        testList.add(testDoc1);
        assertTrue(testList.contains(testDoc1));//test list should contain testDoc1
        testList.add(testTech1);
        assertTrue(testList.contains(testTech1));//test list should contain testTech1
        testList.remove(testDoc1);
        assertTrue(!(testList.contains(testDoc1)));//test list should NOT contain testDoc1
        testList.remove(testTech1);
        assertTrue(!(testList.contains(testTech1)));//test list should NOT contain testTech1
    }
}