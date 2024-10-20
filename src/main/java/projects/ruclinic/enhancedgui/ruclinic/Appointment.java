package projects.ruclinic.enhancedgui.ruclinic;

import projects.ruclinic.enhancedgui.util.Date;

/**
 * This class defines the Appointment data type that has the instance variables Date, Timeslot, patient, provider.
 *
 * @author Michael Ehresman, Melissa Xuan
 */
public class Appointment implements Comparable<Appointment> {
    private final String DOCTOR = "D";
    private final String TECH = "T";

    private Date date;
    private Timeslot timeslot;
    private Person patient;
    private Person provider;

    /**
     * Default constructor that sets the date and profile as their default values, sets Timeslot to slot 1, and provider as PATEL.
     */
    public Appointment() {
        this.date = new Date();
        this.timeslot = new Timeslot();
        this.patient = new Patient();
        this.provider = new Doctor();
    }
   
        /**
        * Argument constructor that takes in variables that don't need to be converted.
        * @param date the date of the patient
        * @param time the timeslot of the appointment
        * @param profile the profile of the patient
        * @param provider the provider for the patient
        */
        public Appointment(Date date, Timeslot time, Person profile, Provider provider)
        {
           this.date = date;
           this.timeslot = time;
           this.patient = profile;
           this.provider = provider;
        }
   
        /**
        * A Copy constructor that clones passed in Appointment object.
        * @param app appointment to be copied
        */
        public Appointment(Appointment app)
        {
           this.date = app.date;
           this.timeslot = app.timeslot;
           this.patient = app.patient;
           this.provider = app.provider;
        }


       /**
        * Compares appointments in order of date, time, and provider.
        * @param a Appointment object to be compared with current Appointment
        * @return int indicates 0 for exactly equal Appointments,
        *         positive if this Appointment is greater than the other lexicographically,
        *         negative if this Appointment is smaller than the other lexicographically
        */
   
       public int compareByAppointment(Appointment a) {
           if (this.date.compareTo(a.date) != 0) {
               return this.date.compareTo(a.date);
           }
           else if (this.timeslot.compareTo(a.timeslot) != 0) {
               return this.timeslot.compareTo(a.timeslot);
           }
           else if (this.provider.getProfile().getLname().compareTo(a.provider.getProfile().getLname()) != 0){
               return this.provider.getProfile().getLname().compareTo(a.provider.getProfile().getLname());
           }
           else {
               return this.provider.getProfile().getFname().compareTo(a.provider.getProfile().getFname());
           }
       }
   
   
       /**
        * Compares appointments in order of patient's last name, first name, date of birth, then appointment date/time.
        * @param a Appointment object to be compared with current Appointment
        * @return int indicates 0 for exactly equal Appointments,
        *         positive if this Appointment is greater than the other lexicographically,
        *         negative if this Appointment is smaller than the other lexicographically
        */
       public int compareByPatient(Appointment a) {
           if (this.patient.getProfile().getLname().compareTo(a.patient.getProfile().getLname()) != 0) {
               return this.patient.getProfile().getLname().compareTo(a.patient.getProfile().getLname());
           }
           else if (this.patient.getProfile().getFname().compareTo(a.patient.getProfile().getFname()) != 0) {
               return this.patient.getProfile().getFname().compareTo(a.patient.getProfile().getFname());
           }
           else if (this.patient.getProfile().getDob().compareTo(a.patient.getProfile().getDob()) != 0) {
               return this.patient.getProfile().getDob().compareTo(a.patient.getProfile().getDob());
           }
           else if (this.date.compareTo(a.date) != 0) {
               return this.date.compareTo(a.date);
           }
           else {
               return this.timeslot.compareTo(a.timeslot);
           }
       }
   
   
   
       /**
        * Compares appointments in order of county, then appointment date and time.
        * @param a Appointment object to be compared with current Appointment
        * @return int indicates 0 for exactly equal Appointments,
        *         positive if this Appointment is greater than the other lexicographically,
        *         negative if this Appointment is smaller than the other lexicographically
        */
         public int compareByLocation(Appointment a) {
             Provider curr = this.getProvider();
             Provider other = a.getProvider();
           if (curr.getLocation().getCounty().
                   compareTo(other.getLocation().getCounty()) != 0) {
               return curr.getLocation().getCounty().
                       compareTo(other.getLocation().getCounty());
           }
           else if (this.date.compareTo(a.date) != 0) {
               return this.date.compareTo(a.date);
           }
           else {
               return this.timeslot.compareTo(a.timeslot);
           }
       }
   
       /**
        * Creates a temp variable to compare the instance variables of both Appointment objects: checks date, timeslot, and patient profile.
        * @param otherApp object to be checked if equals current object
        * @return true if equal, false if not
        */
   
       @Override
       public boolean equals(Object otherApp)
       {
           if(!(otherApp instanceof Appointment temp))
           {
               return false;
           }

           return this.date.equals(temp.date) && this.timeslot.equals(temp.timeslot) && this.patient.getProfile().equals(temp.patient.getProfile());
       }
   
       /**
        * Prints String containing Appointment details.
        * @return String of Appointment details including Date, Timeslot, patient Profile details, and Provider
        */
    @Override
    public String toString() {
        return this.date.toString() + " " + this.timeslot.toString() + " " +
            this.patient.getProfile().toString() + " " + this.provider.toString();
    }
   
       /**
        * Compares date first and if the dates are the same compares the timeslots.
        * @param otherApp the object to be compared.
        * @return current object is greater than (+), less than (-), or equal to other (0)
        */

       @Override
    public int compareTo(Appointment otherApp)
    {
        return this.patient.getProfile().compareTo(otherApp.getPatient().getProfile());
    }

       /**
        * Getter method to get the Provider of the current Appointment object.
        * @return the current Appointment object's Patient
        */
       public Person getPatient() {
           return patient;
       }
   
       /**
        * Getter method to get the Date of the current Appointment object.
        * @return the current Appointment object's Date
        */
       public Date getDate() {
           return date;
       }
   
       /**
        * Getter method to get the provider of the current appointment object.
        * @return the current Appointment object's Provider
        */
       public Provider getProvider()
       {
           return (Provider) this.provider;
       }
   
       /**
        * Getter method to get the Timeslot of the current Appointment object.
        * @return the current Appointment object's Timeslot
        */
       public Timeslot getTimeslot() {
           return timeslot;
       }
   
       /**
        * Setter method to set the Timeslot for the current Appointment object.
        * @param timeslot the Timeslot to set for the current Appointment's Timeslot
        */
       public void setTimeslot(Timeslot timeslot) {
           this.timeslot = timeslot;
       }

}