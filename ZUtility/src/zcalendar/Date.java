package zcalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Date class. Only concerned about year, month, day.
 * @author Carey Zhang
 */
public class Date extends java.util.Date
{
    public Date()
    {
        super();
    }
    
    public Date(long t)
    {
        super(t);
    }

    public Date(int year, int month, int day)
    {
        Calendar cal = new GregorianCalendar();
        cal.set(year, month, day);
        this.setTime(cal.getTimeInMillis());
    }

    public Date(java.util.Date d)
    {
        super(d.getTime());
    }

    public int compareTo(Date that)
    {
        if (this.getYear() < that.getYear())
        {
            return -1;
        }
        else if (this.getYear() > that.getYear())
        {
            return 1;
        }
        else
        {
            if (this.getMonth() < that.getMonth())
                return -1;
            else if (this.getMonth() > that.getMonth())
                return 1;
            else
            {
                if (this.getDay() < that.getDay())
                    return -1;
                else if (this.getDay() > that.getDay())
                    return 1;
                else
                    return 0;
            }
        }
    }

    public int getYear()
    {
        Config.CALENDAR.setTime(this);
        return Config.CALENDAR.get(Calendar.YEAR);
    }

    public int getMonth()
    {
        Config.CALENDAR.setTime(this);
        return Config.CALENDAR.get(Calendar.MONTH);
    }

    public int getDay()
    {
        Config.CALENDAR.setTime(this);
        return Config.CALENDAR.get(Calendar.DATE);
    }

    public boolean equals(Date that)
    {
        if (this.getYear() != that.getYear())
            return false;
        if (this.getMonth() != that.getMonth())
            return false;
        if (this.getDay() != that.getDay())
            return false;
        return true;
    }

    public String toString()
    {
        return getMonth() + "/" + getDay() + "/" + getYear();
    }

    // from form mm/dd/yyyy
    public static Date parseDate(String s)
    {
        String[] parts = s.split("/");
        int month = Integer.parseInt(parts[0].trim());
        int day = Integer.parseInt(parts[1].trim());
        int year = Integer.parseInt(parts[2].trim());
        return new Date(year, month, day);
    }

    public int hashCode()
    {
        return getYear()*13 + getMonth()*17 + getDay()*23;
    }
}
