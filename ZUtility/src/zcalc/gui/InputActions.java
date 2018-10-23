package zcalc.gui;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.text.Keymap;

/**
 * Stores maps for input and actions.
 */
public class InputActions
{
	private ExprLineEdit editor;
	private ActionMap actionMap;
	private InputMap inputMap;
	// names of actions, use to access an action w/ getAction().
	public static final String DEFAULT = "Default";	// just add char to expression
	
	private void initializeActions()
	{
//		actionMap = new ActionMap();
//		
//		// plus key
//		actionMap.put(DEFAULT, new AbstractAction()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				editor.getExprString().add(e.toString());
//			}
//		});
	}
	
	private void initializeInputs()
	{
		inputMap = new InputMap();
		
		// plus key
//		inputMap.
	}
	
	public Action getAction(String key)
	{
		return actionMap.get(key);
	}
	
	public ActionMap getActionMap()
	{
		return actionMap;
	}
	
	public InputMap getInputMap()
	{
		return inputMap;
	}
	
	public Keymap toKeyMap(Keymap map)
	{
		// start from scratch
		map.removeBindings();
		
		KeyStroke[] keys = inputMap.allKeys();
		for (int i = 0; i < keys.length; i++)
			map.addActionForKeyStroke(keys[i], actionMap.get(inputMap.get(keys[i])));
		
		return map;
	}
	
	public InputActions()
	{
		editor = null;
		initializeActions();
		initializeInputs();
	}
	
	public InputActions(ExprLineEdit editor)
	{
		this.editor = editor;
		initializeActions();
		initializeInputs();
	}
}
