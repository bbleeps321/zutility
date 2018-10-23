package main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import zcalc.gui.ZCalc;
import zcalendar.ZCalendar;

/**
 *
 * @author Carey Zhang
 */
public class ZUtility extends JPanel
{
    private static final int WIDTH = 250;
    private static final int HEIGHT = 100;

    private ZCalc zcalc;
    private ZCalendar zcalendar;

    /**
     * Creates the content pane.
     */
    private Container createContentPane()
    {
        JPanel contentPane = new JPanel(new GridLayout(1,2));

        JButton zcalcButton = new JButton("ZCalc");
        zcalcButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (zcalc == null)
                    zcalc = new ZCalc();

                zcalc.setVisible(true);
                zcalc.requestFocus();
            }
        });
        contentPane.add(zcalcButton);

        JButton zcalendarButton = new JButton("ZCalendar");
        zcalendarButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (zcalendar == null)
                    zcalendar = new ZCalendar();

                zcalendar.setVisible(true);
                zcalendar.requestFocus();
            }
        });
        contentPane.add(zcalendarButton);

        return contentPane;
    }

    /**
     * Create the zcalc.gui and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        // Create and set up the window.
        JFrame frame = new JFrame("ZUtility");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        frame.setResizable(false);
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage("zutility.gif"));

        // Create and set up the content pane.
        ZUtility util = new ZUtility();
        util.requestFocusInWindow();

//	frame.setJMenuBar(calc.createMenuBar());
        frame.setContentPane(new JScrollPane(util.createContentPane()));
        frame.setFocusable(true);
//      frame.addKeyListener(util);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        // Set System Look and Feel
//		try
//		{
//			// Set System L&F
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		}
//		catch (Exception e)
//		{
//			System.err.println("Exception " + e + " when setting the L&F!");
//		}

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's zcalc.gui.
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }
}
