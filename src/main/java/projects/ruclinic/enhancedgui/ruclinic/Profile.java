package projects.ruclinic.enhancedgui.ruclinic;

import projects.ruclinic.enhancedgui.util.Date;

/**
 * Profile for Patient including Patient's first name, last name, and date of birth.
 *
 * @author Melissa Xuan
 */
public class Profile implements Comparable<Profile> {
    private final String fname;
    private final String lname;
    private final Date dob;

    /**
     *  Default constructor for Profile.
     */
    public Profile () {
        this.fname = "";
        this.lname = "";
        this.dob = new Date();
    }


    /**
     * Constructor for Profile: takes in patient's first name, last name, and date of birth.
     * @param fname first name
     * @param lname last name
     * @param dob date of birth
     */
    public Profile (String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Checks equality between two Profile objects according to first name, last name, and date of birth.
     * @param obj the object to be compared
     * @return true if objects are equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile profile) {
            return this.fname.equalsIgnoreCase(profile.fname) && this.lname.equalsIgnoreCase(profile.lname)
                    && this.dob.equals(profile.dob);
        }

        return false;
    }

    /**
     * Returns the first name, last name, and date of birth of this Profile as a String.
     * @return String with Profile's first name, last name, and date of birth
     */
    @Override
    public String toString() {
        return this.fname + " " + this.lname + " " + this.dob.toString();
    }

    /**
     * Compares the input object by last name, first name, then date of birth to this object lexicographically.
     * @param obj the Profile object to be compared.
     * @return int indicates 0 for exactly equal Profiles,
     *         positive if this Profile is greater than the other lexicographically,
     *         negative if this Profile is smaller than the other lexicographically
     */
    @Override
    public int compareTo(Profile obj) {
        if (this.lname.compareTo(obj.lname) != 0) {
            return this.lname.compareTo(obj.lname);
        }
        else if (this.fname.compareTo(obj.fname) != 0) {
            return this.fname.compareTo(obj.fname);
        }
        else {
            return this.dob.compareTo(obj.dob);
        }
    }

    /**
     * Getter method to get the first name of the current profile.
     * @return first name of the current profile
     */
    public String getFname() {
        return fname;
    }

    /**
     * Getter method to get the last name of the current profile.
     * @return last name of the current profile
     */
    public String getLname() {
        return lname;
    }

    /**
     * Getter method to get the date of birth of the current profile.
     * @return date of birth of the current profile
     */
    public Date getDob() {
        return dob;
    }
}
