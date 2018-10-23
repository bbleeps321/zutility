package zcalendar;

import java.io.PrintStream;
import java.util.Scanner;
import utilities.ZReadWrite;

/**
 * Stores properties of an event (Date, name, description, etc.).
 * @author Carey Zhang
 */
public class Event implements Comparable<Event>
{
    private static int count = 0;       // Counter for events. Incremented in constructor.
    private Date date;                  // Date of event.
    private String name;                // Name of event.
    private String description;         // Description of event.

    /**
     * Creates a new Event with values defaulted.
     */
    public Event()
    {
        date = new Date();
        name = "Event " + count;
        description = "";
        count++;
    }
    
    /**
     * Creates a new Event with values defaulted.
     */
    public Event(Date d)
    {
        date = d;
        name = "Event " + count;
        description = "";
        count++;
    }

    /**
     * Creates a new event with given properties.
     */
    public Event(Date date, String name, String description)
    {
//        if (date != null)
//            this.date = CalendarComponent.convertDate(date);
        this.date = date;
        this.name = name;
        this.description = description;
        count++;        // still increment?
    }

    /**
     * Returns the date.
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the date.
     */
    public void setDate(java.util.Date date)
    {
        this.date = new Date(date);
//        if (date != null)
//            this.date = CalendarComponent.convertDate(date);
    }

    /**
     * Returns the name.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the description.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Compares this event to another by date order.
     */
    public int compareTo(Event that)
    {
        // only compare by date for now.
        return this.getDate().compareTo(that.getDate());
    }

    public String toString()
    {
        return "Name: " + getName() + "\n" +
               "Date: " + getDate() + "\n" +
               "Description: " + "\n" +
               getDescription();
    }

    public void write(PrintStream out)
    {
        ZReadWrite.writeString(name, out, "name");
        ZReadWrite.writeString(date.toString(), out, "date");
        ZReadWrite.writeString(description, out, "description");
    }

    public void readEvent(Scanner in)
    {
        setName(ZReadWrite.readString(in, "name"));
        setDate(Date.parseDate(ZReadWrite.readString(in, "date")));
        setName(ZReadWrite.readString(in, "description"));
    }

    /**
     * Only works if events on same day.
     */
    public static Event mergeEvents(Event e1, Event e2)
    {
        if (!e1.getDate().equals(e2.getDate()))
            return null;
        String name = e1.getName() + "; " + e2.getName();
        Date d = e1.getDate();  // should be same so doesn't matter which one
        String description = e1.getDescription() + ";\n\n" + e2.getDescription();
        return new Event(d, name, description);
    }

}
