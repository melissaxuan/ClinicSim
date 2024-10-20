package ruclinic;

import util.Date;

/**
 *Class to represent imaging objects which contain a radiology variable
 * @author Michael Ehresman
 */
public class Imaging extends Appointment
{
    private Radiology room;

    /**
    * Default constructor for the imaging class
    */
    public Imaging() {
        super();
        this.room = Radiology.XRAY;
    }

    /**
     * Parameter constructor for the imaging class
     * @param imagingType string holding the name of the imaging room
     */
    public Imaging(Date date, Timeslot timeslot, Person profile, Provider provider, String imagingType) {
        super(date, timeslot, profile, provider);
        this.room = Radiology.getRadiology(imagingType);
    }

    /**
     * Copy constructor for the imaging class
     * @param copyimage object to be copied
     */
    public Imaging(Imaging copyimage) {
        this.room = copyimage.room;
    }

    /**
     * Getter method to return room for Imaging appointment.
     * @return room for Imaging appointment
     */
    public Radiology getRoom() {
        return room;
    }

    /**
     * Checks if O is an instance of imaging object and creates a temp variable to compare instance variables
     * @param o object to be checked if equals current object
     * @return true if they are the same, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Imaging temp)) {
            return super.equals(o);
        } else {
            return this.getDate().equals(temp.getDate()) && this.getTimeslot().equals(temp.getTimeslot()) &&
                    this.getPatient().getProfile().equals(temp.getPatient().getProfile());

        }
    }

    /**
     * toString method for the imaging class
     * Calls the Appointment classes toString
     * @return Appointments toString + [currentobjects room]
     */
    @Override
    public String toString() {
        return super.toString() + "[" + this.room + "]";
    }
}
