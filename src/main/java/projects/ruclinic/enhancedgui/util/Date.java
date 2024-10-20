package util;

import java.util.Calendar;

/**
 * This class defines the Date data type that has the instance variables year, month, and day.
 *
 * @author Michael Ehresman, Melissa Xuan
 */
public class Date implements Comparable<Date>
{
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int THIRTY_ONE_DAY_MONTHS=31;
    public static final int THIRTY_DAY_MONTHS = 30;
    public static final int FEBUARY_DAYS =28;
    public static final int FEBUARY_DAYS_LEAPYEAR =29;
    public static final int INVALID_MONTH=-1;
    public static final int MINIMUM_DAYS =1;

    private final int year;
    private final int month;
    private final int day;

    /**
     * A parameter constructor that takes in a string formatted as mm/dd/yyyy:
     * the date passed in is split into tokens then parsed into an Int and stored into the instance variables.
     * @param date String date to be converted to int values
     * **/
    public Date(String date)
    {
        String[] dateFormat = date.split("/");
        this.month = Integer.parseInt(dateFormat[0]);
        this.day = Integer.parseInt(dateFormat[1]);
        this.year = Integer.parseInt(dateFormat[2]);
    }

    /**
     * A default constructor to create the date as today's date.
     */
    public Date()
    {
        Calendar todays_Date = Calendar.getInstance();
        this.month = todays_Date.get(Calendar.MONTH)+1;
        this.day = todays_Date.get(Calendar.DAY_OF_MONTH);
        this.year = todays_Date.get(Calendar.YEAR);
    }
    /**
     * A copy constructor that clones a passed date object.
     * @param date date to be copied
     */
    public Date(Date date)
    {
        this.month = date.month;
        this.day = date.day;
        this.year= date.year;

    }

    /**
     * Helper method to isValid() to determine the amount of days in the month.
     * @param year year to be checked if leap year
     * @param month to be checked how many days
     * @return THIRTY_ONE_DAY_MONTHS=31, THIRTY_DAY_MONTHS=30, FEBUARY_DAYS =28 or FEBUARY_DAYS_LEAPYEAR = 29
     */
    private int dayChecker(int year, int month)
    {
        switch(month)
        {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return THIRTY_ONE_DAY_MONTHS;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return THIRTY_DAY_MONTHS;
            case Calendar.FEBRUARY:
                return leapYearCheck(year);
            default: return INVALID_MONTH;

        }

    }

    /**
     * Helper method to dayChecker() to figure out if it's a leap year or not.
     * @param year the year to be checked
     * @return FEBUARY_DAYS =28 or FEBUARY_DAYS_LEAPYEAR = 29
     */
    private int leapYearCheck(int year)
    {
        if(year % QUADRENNIAL == 0)
        {
            if(year % CENTENNIAL == 0)
            {
                if(year % QUATERCENTENNIAL == 0)
                {
                    return FEBUARY_DAYS_LEAPYEAR;
                }
                else {return FEBUARY_DAYS;}
            }
            else {return FEBUARY_DAYS_LEAPYEAR;}
        }
        else {return FEBUARY_DAYS;}
    }

    /**
     * A method that determines if the date is a valid calendar date by calling helper method dayChecker() to check the amount of days in the month.
     * @return true if date is a valid calendar date, false if not
     */
    public boolean isValid()
    {
        int submonth = this.month -1;

        if(submonth <Calendar.JANUARY|| submonth >Calendar.DECEMBER)
        {
            return false;
        }

        return this.day >= MINIMUM_DAYS && this.day <= dayChecker(this.year, submonth);

    }

    /**
     * A method to check if the appointment date is today.
     * @return true if date is today, false if not
     */
    public boolean isToday() {
        Calendar date = convertDate();

        return date.compareTo(Calendar.getInstance()) == 0;
    }

    /**
     * A method to check if the appointment date is before today.
     * @return true if date is before today, false if not
     */
    public boolean isPast() {
        Calendar date = convertDate();
        return date.compareTo(Calendar.getInstance()) < 0;
    }

    /**
     * A method to check if the appointment date is after today
     * @return true if date is after today, false if not
     */

    public boolean isFuture() {
        Calendar date = convertDate();
        return date.compareTo(Calendar.getInstance()) > 0;
    }

    /**
     * A method to check if the appointment date is on the weekend.
     * @return true if date is on a weekend false if not
     */
    public boolean isWeekend() {
        Calendar date = convertDate();
        return (date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
    }

    /**
     * A method to check if the appointment is within 6 months in the future from current date.
     * @return true if it is within 6 month false if not
     */
    public boolean isInSixMonths() {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.MONTH, 6);
        Calendar date = Calendar.getInstance();
        date.set(this.year, this.month - 1, this.day);
        return (date.compareTo(today) < 0);
    }

    /**
     * Checks year then month then date to determine which date is greater.
     * @param d the object to be compared to the current object
     * @return current object is greater than (+), less than(-), or equal to other(0)
     */
    @Override
    public int compareTo(Date d) {

        if (this.year != d.year) {
            return this.year - d.year;
        }
        if (this.month != d.month) {
            return this.month - d.month;
        }
        return this.day - d.day;
    }

    /**
     * Checks if otherDate is an instance of Date then creates a tempDate variable and casts it to Date then compares.
     * @param d the date to be checked if it equals the current object
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object d)
    {
        if(!(d instanceof Date tempDate))
        {
            return false;
        }
        return this.year == tempDate.year && this.month == tempDate.month && this.day == tempDate.day;
    }

    /**
     * Returns String of date in MM/DD/YYYY format
     * @return String of date in MM/DD/YYYY format
     */
    @Override
    public String toString()
    {
        return this.month +"/" +this.day + "/"+this.year;
    }

    /**
     * Converts this Date object to a Calendar object.
     * @return Calendar object with the details of this Date
     */
    private Calendar convertDate() {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, this.day);
        date.set(Calendar.MONTH, this.month - 1);
        date.set(Calendar.YEAR, this.year);
        return date;
    }
}