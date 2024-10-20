package ruclinic;

import java.util.Calendar;

/**
 * Contains (int) Hour and (int) Minute for the start time of each.
 *
 * @author Michael Ehresman, Melissa Xuan
 */
public class Timeslot implements Comparable<Timeslot> {
    protected static final int EARLIEST_TIMESLOT = 1;
    protected static final int LATEST_TIMESLOT = 12;
    private final int SLOT1 =1;
    private final int SLOT2 =2;
    private final int SLOT3 =3;
    private final int SLOT4 =4;
    private final int SLOT5 =5;
    private final int SLOT6 =6;
    private final int SLOT7 =7;
    private final int SLOT8 =8;
    private final int SLOT9 =9;
    private final int SLOT10 =10;
    private final int SLOT11 =11;
    private final int SLOT12=12;
    private final int HALFHOUR = 30;
    private final int HOURS = 00;

    private int hour;
    private int minute;


    /**
     * default constructor for the timeslot class
     */
    public Timeslot() {
        this.hour =0;
        this.minute-=0;
    }
    /**
     * parameter constructor for the timeslot class
     * Calls the timeHelper method to set the correct timeslot
     * @param time
     */
    public Timeslot(int time) {
        this.timeHelper(time);
    }

    /**
     * copy constructor for the timeslot class
     * @param copytime object to be copied
     */
    public Timeslot(Timeslot copytime) {
        this.hour = copytime.hour;
        this.minute = copytime.minute;
    }



    /**
     * Creates a temp variable to compare the instance variables hour and minute
     * @param otherTime object to be checked if equals current object
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object otherTime)
    {
        if(!(otherTime instanceof Timeslot temp))
        {
            return false;
        }

        return this.hour == temp.hour && this.minute == temp.minute;
    }


    /**
     * compareTo method for the timeslot Class
     * @param o the object to be compared.
     * @return current object is greater than (+), less than (-), or equal to other (0)
     */
    @Override
    public int compareTo(Timeslot o)
    {
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR_OF_DAY, this.hour);
        c1.set(Calendar.MINUTE, this.minute);
        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.HOUR_OF_DAY, o.hour);
        c2.set(Calendar.MINUTE, this.minute);

        if (this.hour != o.hour) {
            if (o.hour >= SLOT9 && this.hour >= SLOT9)
                return this.hour - o.hour;
            else if (o.hour < SLOT9 && this.hour < SLOT9)
                return this.hour - o.hour;
            else if (o.hour >= SLOT9)
                return this.hour;
            else
                return -this.hour;
        }
        else {
            return this.minute - o.minute;
        }
    }

    /**
     * toString method for the timeSlot class
     * @return hour : hour minute: minute
     */
    @Override
    public String toString()
    {
        String s = this.hour + ":" + (this.minute / SLOT10) + "0";
        if (this.hour >= SLOT9)
            s = s.concat(" AM");
        else
            s = s.concat( " PM");
        return s;
    }

    /**
     * Method to help give the timeslot its correct hours and minutes
     * @param slot the timeslot of the appointment
     */
    private void timeHelper(int slot) {
        switch (slot) {
            case (SLOT1):
                this.hour = SLOT9;
                this.minute = HOURS;
                break;
            case (SLOT2):
                this.hour = SLOT9;
                this.minute = HALFHOUR;
                break;
            case (SLOT3):
                this.hour = SLOT10;
                this.minute = HOURS;
                break;
            case (SLOT4):
                this.hour = SLOT10;
                this.minute = HALFHOUR;
                break;
            case (SLOT5):
                this.hour = SLOT11;
                this.minute = HOURS;
                break;
            case (SLOT6):
                this.hour = SLOT11;
                this.minute = HALFHOUR;
                break;
            case (SLOT7):
                this.hour = SLOT2;
                this.minute = HOURS;
                break;
            case (SLOT8):
                this.hour = SLOT2;
                this.minute = HALFHOUR;
                break;
            case (SLOT9):
                this.hour = SLOT3;
                this.minute = HOURS;
                break;
            case (SLOT10):
                this.hour = SLOT3;
                this.minute = HALFHOUR;
                break;
            case (SLOT11):
                this.hour = SLOT4;
                this.minute = HOURS;
                break;
            case (SLOT12):
                this.hour = SLOT4;
                this.minute = HALFHOUR;
                break;
            default:
                this.hour = SLOT9;
                this.minute = HOURS;
                break;
        }
    }

    /**
     *
     * getter method to get the hour of the timeslot object
     * @return hour of the current object
     */
    public int getHour(){return this.hour;}

    /**
     * getter method to get the minute of the timeslot object
     * @return minute of the current object
     */
    public int getMinute(){return this.minute;}


}
