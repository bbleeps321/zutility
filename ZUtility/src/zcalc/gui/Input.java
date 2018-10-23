package zcalc.gui;

import java.awt.event.KeyEvent;

import javax.swing.Action;

/**
 * Stores data on an input.
 * Can be from a key event.
 * Can be associated with an Action.
 * 
 * @author Carey Zhang, Copyright 2008
 */
public class Input
{
	private KeyEvent keyEvent;
	
	private Action action;	// associated
	
	public Input(KeyEvent e)
	{
		keyEvent = e;
		action = null;
	}
	
	public Input(Action a)
	{
		keyEvent = null;
		action = a;
	}
	
	public Input(KeyEvent e, Action a)
	{
		keyEvent = e;
		action = a;
	}
	
	public KeyEvent getKeyEvent()
	{
		return keyEvent;
	}

	public Action getAction()
	{
		return action;
	}
	
	public void doAction()
	{
		if (action != null)
			action.actionPerformed(null);
	}
}
