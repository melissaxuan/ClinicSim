package ruclinic;

import util.Date;

/**
 * Class to represent Person object.
 * @author Michael Ehresman 
 */
public abstract class Person implements Comparable<Person> {
    protected Profile profile;

    /**
     * Default constructor for the Person class
     */
    public Person() {
        this.profile = new Profile();
    }

    /**
     * Parameter constructor for the Person class that takes in details for Profile object
     */
    public Person(String fname, String lname, Date dob) {
        this.profile = new Profile(fname,lname,dob);
    }

    /**
     * Parameter constructor for the Person class that takes in Profile object
     * @param profile
     */
    public Person(Profile profile) {
        this.profile = profile;
    }

    /**
     * Copy constructor for the Person class
     */
    public Person(Person copy) {
        this.profile = copy.profile;
    }

    /**
     * Checks equality between two Person objects according to their profile by calling Profiles equals() method
     * @param o the object to be compared
     * @return true if objects are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Person person) {
            return this.profile.equals(person.profile);
        }

        return false;
    }

    /**
     * compareTo method that calls the compareTo in profile
     * @param p the object to be compared.
     * @return int indicates 0 for exactly equal Profiles,
     *   positive if this Profile is greater than the other lexicographically,
     *   negative if this Profile is smaller than the other lexicographically
     */
    @Override
    public int compareTo(Person p) {
       return this.profile.compareTo(p.profile);
    }

    /**
     * Getter method to get the person profile
     * @return the current objects profile
     */
    public Profile getProfile(){return this.profile;}
}
