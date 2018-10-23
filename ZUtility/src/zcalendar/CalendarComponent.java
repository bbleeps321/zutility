/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zcalendar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.jfree.ui.DateChooserPanel;

/**
 *
 * @author Carey Zhang
 */
public class CalendarComponent extends DateChooserPanel
{
    private ArrayList<ActionListener> listeners;

    public CalendarComponent()
    {
        super(new GregorianCalendar(), false);
        listeners = new ArrayList<ActionListener>();
        setChosenDateButtonColor(Color.green);
    }

    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);

        if (listeners == null)  // occurs before constructor
            return;
        
        ActionEvent event = new ActionEvent(this, e.getID(), e.getActionCommand());
        for (ActionListener listener : listeners)
            listener.actionPerformed(event);
    }

    public void addActionListener(ActionListener listener)
    {
        listeners.add(listener);
    }

    public Date getDate()
    {
        return new Date(super.getDate());
    }
    

//    /**
//     * Converts a date to be only to the day specificity level.
//     */
//    public static Date convertDate(Date d)
//    {
//        Calendar cal = new GregorianCalendar();
//        cal.setTime(d);
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        int date = cal.get(Calendar.DATE);
//        cal.set(year, month, date, 0, 0, 1);
//        return cal.getTime();
//    }
}
