package projects.ruclinic.enhancedgui.ruclinic;

/**
 * Defines a node in a singly linked list that maintains the list of Visits per Patient valued with Appointments.
 *
 * @author Michael Ehresman
 */
public class Visit {
    private final Appointment appointment;
    private Visit next;

    /**
     * Default constructor that instantiates (Appointment) appointment and (Visit) next.
     */
    public Visit() {
        this.appointment = new Appointment();
        this.next = null;
    }

    /**
     * Argument constructor that sets appointment to argument Appointment object.
     * @param app the Appointment object to be added as the value to this Visit node
     */
    public Visit(Appointment app)
    {
        this.appointment = app;
        this.next = null;
    }

    /**
     * Getter method to get the current nodes appointment.
     * @return the appointment at the current node
     */
    public Appointment getAppointment()
    {
        return this.appointment;
    }

    /**
     * Getter method to get the next node in the list.
     * @return the next node in the linked list
     */
    public Visit getNext()
    {
        return this.next;
    }

    /**
     * Setter method to change the next node in the list.
     * @param next the node to be set as the next node in the list
     */
    public void setNext(Visit next)
    {
        this.next = next;
    }
}

