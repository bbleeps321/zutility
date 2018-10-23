package zcalc.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Calculator for ZUtility.
 */
public class ZCalc extends JFrame implements KeyListener, FocusListener
{
    private static final int WIDTH = 500;
    private static final int HEIGHT = 750;
    private static final int GAP = 20;
    // components initialized in createContentPane()
    private ChartComponent chart;
    private CalculatorComponent calculator;
    private OptionsComponent options;
    private InputComponent input;

    // Tab component for all tabs.
    // Should only contain OutputComponents.
    // dialogs initialized when first displayed.
    private JTabbedPane tabbedPane;
    
    private VariableEditPanel variableEdit;

    public ZCalc()
    {
        // Create and set up the window.
        super("ZCalc");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().createImage("zcalc.gif"));

        requestFocusInWindow();

//	frame.setJMenuBar(calc.createMenuBar());
        setContentPane(new JScrollPane(createContentPane()));
        setFocusable(true);
        addKeyListener(this);

        pack();
        setVisible(false);
    }

    private Container createContentPane()
    {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(WIDTH - 2 * GAP, HEIGHT - 2 * GAP));
        contentPane.setFocusable(true);

        // initialize tabbed pane
        tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        tabbedPane.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                // update editor connected to InputComponent
                OutputComponent tab = (OutputComponent) tabbedPane.getSelectedComponent();

                input.setOutput(tab);
            }
        });
        tabbedPane.setFocusable(true);
        tabbedPane.addKeyListener(this);

        // initialize all components
        chart = new ChartComponent();
        calculator = new CalculatorComponent();
        options = new OptionsComponent();
        input = new InputComponent();

        chart.addKeyListener(this);
        calculator.addKeyListener(this);
        options.addKeyListener(this);
        input.addKeyListener(this);

        // add all components to tabbed pane
        tabbedPane.add(calculator.getTitle(), calculator);
        tabbedPane.add(chart.getTitle(), chart);
        tabbedPane.add(options.getTitle(), options);

        // set up split
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, input);
        split.setOneTouchExpandable(true);

        // setup content pane
        contentPane.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        contentPane.add(split, BorderLayout.CENTER);

        this.setFocusTraversalKeysEnabled(false);

        return contentPane;
    }

//	private JMenuBar createMenuBar()
//	{
//		JMenuBar bar = new JMenuBar();
//		JMenu menu = null;
//		JMenuItem item = null;
//		
//		// "data" menu
//		menu = new JMenu("Data");
//		bar.add(menu);
//		
//		// "variables" item 
//		item = new JMenuItem("Variables");
//		item.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				if (variableEdit == null)
//					variableEdit = new VariableEditTable((JFrame)getTopLevelAncestor());
//				variableEdit.setVisible(true);
//			}
//		});
//		menu.add(item);
//		
//		return bar;
//	}
    public void keyPressed(KeyEvent e)
    {
        OutputComponent output = (OutputComponent) tabbedPane.getSelectedComponent();
        ExprLineEdit editor = output.getEditor();
        if (editor == null)
            return;

        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            output.doAction();
        else
            editor.keyPressed(e);
    }

    /**
     * Pass focus to current tab.
     */
    public void focusGained(FocusEvent e)
    {
        OutputComponent tab = (OutputComponent) tabbedPane.getSelectedComponent();
        tab.requestFocusInWindow();
    }

    /**
     * Create the zcalc.gui and show it.  For thread safety,
     * this method should be invoked from the 
     * event-dispatching thread.
     */
    private static ZCalc createAndShowGUI()
    {
        // Create and set up the window.
        ZCalc calc = new ZCalc();
        calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display the window.
        calc.setVisible(true);

        return calc;
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

    public void keyTyped(KeyEvent e)
    {
    }

    public void keyReleased(KeyEvent e)
    {
    }

    public void focusLost(FocusEvent e)
    {
    }
}
