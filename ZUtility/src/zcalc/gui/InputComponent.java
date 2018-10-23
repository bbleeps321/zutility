package zcalc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import utilities.Symbol;
import zcalc.math.Operator;

public class InputComponent extends JPanel implements ActionListener, KeyListener
{
	private static final int GAP = 0;
	private static final Font FONT = new Font("", Font.BOLD, 12);
	
	private static final String ENTER = "Enter";
	private static final String CLEAR = "Clear";
	private static final String REMOVE = "Remove";
	private static final String DELETE = "Delete";
	
	private OutputComponent output;	// output component this is currently connected to.
	private HashMap<JButton, Input> commandMap;	// map of buttons and their commands.
	private JTabbedPane tabbedPane;
	
	public InputComponent()
	{
		super(new BorderLayout());
				
		commandMap = new HashMap<JButton, Input>();
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		
//		// create individual tabs
//		JPanel basic = createBasicPanel();
//		JPanel trig = createAdvancedPanel();
		
		// add tabs to tabbed pane
		tabbedPane.add("Basic", createBasicPanel());
		tabbedPane.add("Trig", createTrigPanel());
		tabbedPane.add("Vars", createVariablePanel());
		
		tabbedPane.setToolTipTextAt(0, "Basic operators and symbols");
		tabbedPane.setToolTipTextAt(1, "Trigonometric functions");
		tabbedPane.setToolTipTextAt(2, "Variables and values");
		
		// create main button panel that is separate from the tabs
		JPanel mainButtons = createMainButtonPanel();

		add(tabbedPane, BorderLayout.CENTER);
		add(mainButtons, BorderLayout.EAST);
		
		// set up listeners
		this.setFocusable(true);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (!validOutput())
			return;
		JButton button = (JButton)e.getSource();
		
		// enter button
		if (button.getText().equals(ENTER))
			output.doAction();
		// clear button
		else if (button.getText().equals(CLEAR))
			output.getEditor().clear();
		
		// most cases...
		else
		{
			Input input = commandMap.get(e.getSource());
			if (input != null)
				InputHandler.handleInput(input, output.getEditor());
			else
				System.err.println("Unmapped button!");
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		if (!validOutput())
			return;
		
		// evaluate
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			output.doAction();
		else	// pass to editor
			output.getEditor().keyPressed(e);
		
//		
	}
	
	public void setOutput(OutputComponent output)
	{
		this.output = output;
	}
	
	public OutputComponent getOutput()
	{
		return output;
	}
	
	/**
	 * Returns whether or not the current output is valid
	 * (object and editor != null).
	 */
	public boolean validOutput()
	{
		return output != null && output.getEditor() != null;
	}
	
	private JPanel createMainButtonPanel()
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(145, -1));
		/*
		 * <Arrows>
		 * Clear
		 * Remove
		 * Delete
		 * Enter (multiple)
		 */
		JPanel top = new JPanel(new BorderLayout());	// arrows and small buttons
		top.add(createArrowPanel(), BorderLayout.CENTER);
		JPanel buttonGrid = new JPanel(new GridLayout(3,1));

		buttonGrid.add(createButton(CLEAR, new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!validOutput())
					return;
				output.getEditor().clear();
			}
		}, "Clear all input text"));
		
		buttonGrid.add(createButton(REMOVE, KeyEvent.VK_BACK_SPACE, '\u0000', "Remove previous symbol"));
		buttonGrid.add(createButton(DELETE, KeyEvent.VK_DELETE, '\u0000', "Remove next symbol"));
		
		top.add(buttonGrid, BorderLayout.SOUTH);
		panel.add(top, BorderLayout.NORTH);
		
		panel.add(createButton(ENTER, KeyEvent.VK_ENTER, '\u0000', "Evaluate input"), BorderLayout.CENTER);
		return panel;
		
	}
	
	/**
	 * Creates the arrow panel.
	 */
	private JPanel createArrowPanel()
	{
		JPanel panel = new JPanel(new GridLayout(3,3,GAP,GAP));
		JButton button = null;
		
		// filler
		panel.add(new JLabel());
		
		// up arrow\u039B
		panel.add(createButton("\u25B2", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (output == null)
					return;
				output.doUp();
			}
		}, "Move to start of input"));
		
		// filler
		panel.add(new JLabel());
		
		// left arrow
		panel.add(createButton("\u25C4", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (output == null)
					return;
				output.doLeft();
			}
		}, "Move left one symbol"));
		
		// filler
		panel.add(new JLabel());
		
		// right arrow
		panel.add(createButton("\u25BA", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (output == null)
					return;
				output.doRight();
			}
		}, "Move right one symbol"));
		
		// filler
		panel.add(new JLabel());
		
		// down arrow
		panel.add(createButton("\u25BC", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (output == null)
					return;
				output.doDown();
			}
		}, "Move to end of input"));
		
		// filler
		panel.add(new JLabel());
		
		return panel;
	}
	
	/**
	 * Creates and returns the "basic" panel.
	 * Consists of numbers, basic operators.
	 */
	private JPanel createBasicPanel()
	{
		JPanel panel = new JPanel(new GridLayout(9,4,GAP,GAP));
		
		/*
		 * round, X, abs, rem
		 * !, inf, e, pi
		 * log, ln, 10^x, e^x
		 * EE, x^-1, ^, rt
		 * (, ), x^2, sqrt
		 * 7, 8, 9, /
		 * 4, 5, 6, *
		 * 1, 2, 3, -
		 * 0, ., -, +,
		 */
		panel.add(new JLabel());
//		panel.add(createButton("", KeyEvent.VK_7, '7'));
		panel.add(createButton("X", KeyEvent.VK_X, 'x'));
		panel.add(createButton("abs", KeyEvent.VK_A, 'a', InputEvent.ALT_DOWN_MASK));
		panel.add(new JLabel());
//		panel.add(createButton("rem", KeyEvent.VK_5, '%'));
		
		panel.add(createButton(Operator.FACTORIAL.symbol(), KeyEvent.VK_7, '7'));
		panel.add(createButton(Symbol.INFINITY.symbol(), KeyEvent.VK_I, 'I', InputEvent.ALT_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
		panel.add(createButton(Symbol.E.symbol(), KeyEvent.VK_E, 'e', InputEvent.ALT_DOWN_MASK));
		panel.add(createButton(Symbol.PI.symbol(), KeyEvent.VK_P, 'p', InputEvent.ALT_DOWN_MASK));
		
		panel.add(createButton("log", KeyEvent.VK_L, 'l', InputEvent.ALT_DOWN_MASK));
		panel.add(createButton("ln", KeyEvent.VK_N, 'n', InputEvent.ALT_DOWN_MASK));
		panel.add(createButton("10\u207F", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!validOutput())
					return;
				output.getEditor().append(Operator.POWER_TEN.symbol());
			}
		}));
		panel.add(createButton("e\u207F", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!validOutput())
					return;
				output.getEditor().append(Operator.POWER_E.symbol());
			}
		}));
		
		panel.add(createButton("EE", KeyEvent.VK_E, 'E', InputEvent.ALT_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
		panel.add(createButton("X"+Operator.INVERSE.symbol(), new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!validOutput())
					return;
				output.getEditor().append(Operator.INVERSE.symbol());
			}
		}));
		panel.add(createButton("^", KeyEvent.VK_6, '^'));
		panel.add(createButton(Operator.ROOT.symbol(), KeyEvent.VK_R, 'R', InputEvent.ALT_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
		
		panel.add(createButton("(", KeyEvent.VK_9, '('));
		panel.add(createButton(")", KeyEvent.VK_0, ')'));
		panel.add(createButton("X"+Operator.SQUARED.symbol(), new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!validOutput())
					return;
				output.getEditor().append(Operator.SQUARED.symbol());
			}
		}));
		panel.add(createButton(Operator.SQRT.symbol(), KeyEvent.VK_R, 'r', InputEvent.ALT_DOWN_MASK));
		
		panel.add(createButton("7", KeyEvent.VK_7, '7'));
		panel.add(createButton("8", KeyEvent.VK_8, '8'));
		panel.add(createButton("9", KeyEvent.VK_9, '9'));
		panel.add(createButton("\u00F7", KeyEvent.VK_DIVIDE, '/'));
		
		panel.add(createButton("4", KeyEvent.VK_4, '4'));
		panel.add(createButton("5", KeyEvent.VK_5, '5'));
		panel.add(createButton("6", KeyEvent.VK_6, '6'));
		panel.add(createButton("\u00D7", KeyEvent.VK_MULTIPLY, '*'));
		
		panel.add(createButton("1", KeyEvent.VK_1, '1'));
		panel.add(createButton("2", KeyEvent.VK_2, '2'));
		panel.add(createButton("3", KeyEvent.VK_3, '3'));
		panel.add(createButton("-", KeyEvent.VK_MINUS, '-'));
		
		panel.add(createButton("0", KeyEvent.VK_0, '0'));
		panel.add(createButton(".", KeyEvent.VK_DECIMAL, '.'));
		panel.add(createButton("(-)", KeyEvent.VK_MINUS, '-'));
		panel.add(createButton("+", KeyEvent.VK_PLUS, '+'));
		
		return panel;
	}
	
	/**
	 * Creates and returns the "trig" panel.
	 * Consists of trig functions.
	 */
	private JPanel createTrigPanel()
	{
		JPanel panel = new JPanel(new GridLayout(6,2,GAP,GAP));
		
		/*
		 * sin arcsin
		 * cos arccos
		 * tan arctan
		 * csc arccsc
		 * sec arcsec
		 * cot arccot
		 */
		
		panel.add(createButton("sin", KeyEvent.VK_S, 's', InputEvent.ALT_DOWN_MASK));
		panel.add(createButton("sin\u207B\u00B9", KeyEvent.VK_S, 'S', InputEvent.ALT_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
		
		panel.add(createButton("cos", KeyEvent.VK_C, 'c', InputEvent.ALT_DOWN_MASK));
		panel.add(createButton("cos\u207B\u00B9", KeyEvent.VK_C, 'C', InputEvent.ALT_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
		
		panel.add(createButton("tan", KeyEvent.VK_T, 't', InputEvent.ALT_DOWN_MASK));
		panel.add(createButton("tan\u207B\u00B9", KeyEvent.VK_T, 'T', InputEvent.ALT_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
		
		panel.add(createButton("csc", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!validOutput())
					return;
				output.getEditor().append(Operator.CSC.symbol());
			}
		}));
		panel.add(createButton("csc\u207B\u00B9", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!validOutput())
					return;
				output.getEditor().append(Operator.ARCCSC.symbol());
			}
		}));
		
		panel.add(createButton("sec", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!validOutput())
					return;
				output.getEditor().append(Operator.SEC.symbol());
			}
		}));
		panel.add(createButton("sec\u207B\u00B9", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!validOutput())
					return;
				output.getEditor().append(Operator.ARCSEC.symbol());
			}
		}));
		
		panel.add(createButton("cot", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!validOutput())
					return;
				output.getEditor().append(Operator.COT.symbol());
			}
		}));
		panel.add(createButton("cot\u207B\u00B9", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!validOutput())
					return;
				output.getEditor().append(Operator.ARCCOT.symbol());
			}
		}));

		return panel;
	}
	
	/**
	 * Returns a panel with a VariableEditTable
	 */
	private JPanel createVariablePanel()
	{
//		JPanel panel = new JPanel();
		
		VariableEditPanel panel = new VariableEditPanel();
		
		// set up table in panel to add variable to editor if double-clicked.
		panel.getTable().addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() <= 1)
					return;
				JTable table = (JTable)e.getSource();
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				
				if (row == -1 || col == -1)	// invalid
					return;
				
				// only add var if double click on variable column
				if (col == VariableEditPanel.VARIABLE)
					output.getEditor().append((String)table.getValueAt(row, col));
			}
		});
		
		return panel;
	}
	
	/**
	 * Overloaded. Defaults modifiers to zero.
	 */
	private JButton createButton(String label, int keyCode, char keyChar)
	{
		return createButton(label, keyCode, keyChar, 0);
	}
	
	/**
	 * Overloaded. Defaults modifiers to zero.
	 */
	private JButton createButton(String label, int keyCode, char keyChar, String toolTip)
	{
		return createButton(label, keyCode, keyChar, 0, toolTip);
	}
	
	/**
	 * Creates and returns a button that is 
	 * set up and mapped to a Input with the
	 * specified Action info.
	 */
	private JButton createButton(String label, Action action)
	{
		return createButton(label, action, label);
	}
	
	private JButton createButton(String label, Action action, String toolTip)
	{
		JButton button = new JButton(label);
		button.addActionListener(this);
		button.setFont(FONT);
		button.setToolTipText(toolTip);
		button.addKeyListener(this);
		// create appropriate data from info
		Input input = new Input(action);
		
		commandMap.put(button, input);
		return button;
	}
	
	/**
	 * Creates and returns a button that is 
	 * set up and mapped to an Input with the
	 * specified KeyEvent info.
	 */
	private JButton createButton(String label, int keyCode, char keyChar, int modifiers)
	{
		return createButton(label, keyCode, keyChar, modifiers, label);
	}
	
	private JButton createButton(String label, int keyCode, char keyChar, int modifiers, String toolTip)
	{
		JButton button = new JButton(label);
		button.addActionListener(this);
		button.setFont(FONT);
		button.setToolTipText(toolTip);
		button.addKeyListener(this);
		// create appropriate data from info
		Input input = new Input(new KeyEvent(button, 0, 0L, modifiers, keyCode, keyChar));
		
		commandMap.put(button, input);
		return button;
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
