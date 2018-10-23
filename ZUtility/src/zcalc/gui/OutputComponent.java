package zcalc.gui;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * Abstract class all components intended for 
 * their own tabs should extend.
 */
public abstract class OutputComponent extends JPanel
{
	protected OutputComponent(LayoutManager mngr)
	{
		super(mngr);
		this.setFocusable(true);
	}
	
	/**
	 * Returns the editor that currently has focus in the component.
	 */
	public abstract ExprLineEdit getEditor();
	
	/**
	 * Tells the component to do some action with the editor.
	 */
	public abstract void doAction();
	
	/**
	 * Tells the component to do some action with the editor.
	 */
	public abstract void doUp();
	
	/**
	 * Tells the component to do some action with the editor.
	 */
	public abstract void doDown();
	
	/**
	 * Tells the component to do some action with the editor.
	 */
	public abstract void doLeft();
	
	/**
	 * Tells the component to do some action with the editor.
	 */
	public abstract void doRight();
	
	/**
	 * Returns the title of the component, as shown in the tab.
	 */
	public abstract String getTitle();
}
