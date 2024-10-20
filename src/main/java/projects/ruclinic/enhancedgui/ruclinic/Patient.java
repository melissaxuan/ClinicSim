package projects.ruclinic.enhancedgui.ruclinic;

/**
* Contains Patient's Profile, list of completed visits, and charge() calculates charge accrued.
*
* @author Michael Ehresman
*/
public class Patient extends Person {
    private final int NO_CHARGE = 0;
    private Visit visits;

    /**
    * Default constructor for the Patient class
    */
    public Patient() {
        super();
        this.visits = null;
    }

    /**
    * Parameter constructor for the Patient class
    * @param profile
    */
    public Patient(Profile profile) {
        super(profile);
        this.visits = null;
    }

    /**
    * Copy constructor for the Patient class
    * @param p the patient object to be copied
    */
    public Patient(Patient p) {
        super(p.profile);
        this.visits = p.visits;
    }

    /**
    * Charge method that adds up all the charges of all the patients visits.
    * Checks if the patient has no visits.
    * Iterates through the patients visit list and adds up the charges.
    *
    * @return totalCharge
    */
    public int charge() {
        if (visits == null) {
            return NO_CHARGE;
        }

        int totalCharge = 0;

        Visit current = visits;

        while (current != null) {
            totalCharge += current.getAppointment().getProvider().rate();

            current = current.getNext();
        }
        return totalCharge;
    }



    /**
    * Adds an appointment under the patients profile to their visits list.
    * Checks if this is the patients first visit.
    * Iterates through the patients visit list to add the visit at the end.
    *
    * @param app appointment to be added as a visit
    */
    public void addVisit(Appointment app) {
        Visit newVisit = new Visit(app);

        if (this.visits == null) {
            this.visits = newVisit;
        } else {
            Visit current = this.visits;
            while (current.getNext() != null) {
                current = current.getNext();
            }

            current.setNext(newVisit);
        }
    }


    /**
    * Removes an appointment under the patients visit list.
    * Checks if the list is empty or if the head of the list is the visit to be removed.
    * Traverses the list until it reaches the end or finds the visit to be removed.
    *
    * @param app visit to be removed from the list
    */
    public String removeVisit(Appointment app) {
        if (this.visits == null) {
            return "This patient has no visits";
        }
        if (this.visits.getAppointment().equals(app)) {
            this.visits = this.visits.getNext();
            return "";
        }
        Visit current = this.visits;
        Visit previous = null;
        while (current != null && !(current.getAppointment().equals(app))) {
            previous = current;

            current = current.getNext();
        }
        if (current == null) {
            return "This patient does not have this visit to remove";
        }

        previous.setNext(current.getNext());
        return "";
    }

    /**
    * Checks if otherPatient is an instance of patient.
    * Creates a temp variable to compare the patients profile and charge.
    *
    * @param otherPatient the patient to be compared to the current patient object
    * @return true/false
    */

    @Override
    public boolean equals(Object otherPatient) {
        if (!(otherPatient instanceof Patient temp)) {
         return false;
        }
        return this.profile.equals(temp.profile);// && this.charge() == temp.charge();
    }


    /**
    * Prints Patient details including patient Profile details and charge accrued.
    * @return profiles toString with the patients total charge
    */

    @Override
    public String toString() {
        return this.profile + " [amount due: $" + String.format("%,d", this.charge()) + ".00]";
    }


    /**
    * Compares the patients total charge and if equal then compares their profiles calling Profiles compareTo.
    *
    //* @param otherPatient the object to be compared
    * @return current object is greater than (+), less than(-), or equal to other(0)
    */

    public int compareTo(Patient otherPatient) {
        int chargeCompare = this.charge() - otherPatient.charge();

        if (this.profile.compareTo(otherPatient.profile) != 0) {
         return this.profile.compareTo(otherPatient.profile);
        }

        return chargeCompare;
    }

    /**
     * Return profile of patient.
     * @return patient profile
     */
    public Profile getProfile() {
        return this.profile;
    }
}
