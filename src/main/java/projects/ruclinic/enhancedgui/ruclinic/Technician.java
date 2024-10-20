package projects.ruclinic.enhancedgui.ruclinic;

/**
 * Class to represent Technician object, a type of Provider.
 *
 * @author Melissa Xuan
 */
public class Technician extends Provider {
    private int ratePerVisit;

    /**
     * Default Technician constructor that sets ratePerVisit to zero.
     */
    public Technician() {
        super();
        this.ratePerVisit = 0;
    }

    /**
     * Parameter constructor for Technician.
     * @param fname first name of Technician
     * @param lname last name of Technician
     * @param dob date of birth of Technician
     * @param location location of Technician's office
     * @param rate unique identifier for Technician
     */
    public Technician(String fname, String lname, String dob, String location, String rate) {
        super(fname, lname, dob, location);
        this.ratePerVisit = Integer.parseInt(rate);
    }

    /**
     * Return rate per visit for this Technician.
     * @return rate per visit
     */
    public int rate() {
        return this.ratePerVisit;
    }

    /**
     * toString for the Technician class
     * @return Providers toString + [Rate: $ + rate)]
     */
    @Override
    public String toString() {
        return super.toString() + "[rate: $" + String.format("%,d", rate()) + ".00]";
    }

    /**
     * Equals method for the Technician class
     * checks if object t is an instance of technician then calls Providers equals and checks ratepervisit
     * @param t the object to be compared
     * @return true if they are equal, false if not
     */
    @Override
    public boolean equals(Object t) {
        if (t instanceof Technician)
            return super.equals(t) && this.ratePerVisit == ((Technician) t).getRatePerVisit();

        return false;
    }

    /**
     * Getter method to get the ratePerVisit of the current object
     * @return current objects ratePerVisit
     */

    public int getRatePerVisit() {
        return ratePerVisit;
    }
}
