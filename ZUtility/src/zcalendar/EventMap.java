
package zcalendar;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Map of dates to events.
 * @author Carey Zhang
 */
public class EventMap
{
    private Map<Date, Event> map;

    public EventMap()
    {
        map = new TreeMap<Date, Event>();
    }

    public void addEvent(Event e)
    {
        map.put(e.getDate(), e);
    }

    public void removeEvent(Event e)
    {
        map.remove(e.getDate());
    }

    public Event getEvent(Date d)
    {
        Set<Date> keys = map.keySet();
        for (Date date : keys)
            if (date.equals(d))
                return map.get(date);
        return null;
    }

    public void clear()
    {
        map.clear();
    }

    public ArrayList<Event> toEventList()
    {
        return new ArrayList<Event>(map.values());
    }

    public String toString()
    {
        String s = "";
        Set<Date> keys = map.keySet();
        for (Date d : keys)
            s += d + ", " + getEvent(d) + "\n";
        return s;
    }
}
