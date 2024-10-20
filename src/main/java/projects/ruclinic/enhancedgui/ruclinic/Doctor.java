package ruclinic;

/**
 * Class to represent Doctor object, a type of Provider.
 *
 * @author Melissa Xuan
 */
public class Doctor extends Provider {
    private final int NOT_COMPARABLE = -1;

    private Specialty specialty;
    private String npi;

    /**
     * Default constructor for Doctor.
     */
    public Doctor() {
        super();
        this.specialty = null;
        this.npi = "";
    }

    /**
     * Argument constructor for Doctor.
     */
    public Doctor(String fname, String lname, String dob, String location, String specialty, String npi) {
        super(fname, lname, dob, location);
        this.specialty = Specialty.getSpecialty(specialty);
        this.npi = npi;
    }

    /**
     * Return rate per visit for this Technician
     * @return rate per visit
     */
    public int rate()
    {
        return this.specialty.getCharge();
    }

    /**
     * Returns String with all details related to this Doctor object.
     * @return String with this Doctor's first name, last name, date of birth, location, specialty, and npi
     */
    @Override
    public String toString() {
        return super.toString() + "[" + this.specialty.name() + ", #" + this.npi + "]";
    }

    /**
     * Checks if the input object is equal to this object
     * @param p the object to be compared
     * @return true if the objects are equal, false if the objects are not
     */
    @Override
    public boolean equals(Object p) {
        if (p instanceof Doctor) {
            Doctor d = (Doctor) p;
            return this.npi.equals(d.getNpi());
        }

        return false;
    }

    /**
     * Getter method to get this Doctor object's npi value
     * @return this Doctor object's npi value
     */
    public String getNpi() {
        return npi;
    }
}
