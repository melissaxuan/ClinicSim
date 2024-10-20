package projects.ruclinic.enhancedgui.ruclinic;

import projects.ruclinic.enhancedgui.util.Date;

/**
 * Contains (enum) Location and (enum) Specialty for each Provider.
 *
 * @author Michael Ehresman, Melissa Xuan
 */
public abstract class Provider extends Person {
   private Location location;

   /**
    * Default constructor for Provider.
    */
   public Provider() {
      super();
   }

   /**
    * Argument constructor for Provider, takes in first name, last name, and date of birth of Provider.
    * @param fname first name of Provider
    * @param lname last name of Provider
    * @param dob date of birth of Provider
    */
   public Provider(String fname, String lname, String dob, String location) {
      super(fname, lname, new Date(dob));
      this.location = Location.getLocation(location);
   }

   /**
    * Abstract method to return rate per visit of Provider.
    * @return rate per visit
    */
   public abstract int rate();

   /**
    * ToString method for the Provider class
    * @return profile to string + name + county + zip
    */
   @Override
   public String toString() {
      return "[" + profile.toString() + ", " + location.name() + ", " + location.getCounty() + " " + location.getZip() + "]";
   }

   /**
    * getter method for location
    * @return current objects location
    */
   public Location getLocation() {
      return this.location;
   }
}
