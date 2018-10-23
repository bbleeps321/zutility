package zcalc.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Component for displaying and modifying configuration options.
 */
public class OptionsComponent extends OutputComponent
{
	private JCheckBox crosshairOnCheck;

	private JRadioButton inRadiansRadio;
	private JRadioButton inDegreesRadio;

	public OptionsComponent()
	{
		super(new BorderLayout());

		add(createSettingsPanel(), BorderLayout.CENTER);
//		add(createButtonBar(), BorderLayout.SOUTH);
	}

	private JPanel createSettingsPanel()
	{
		JPanel panel = new JPanel(new GridLayout(1,2));

		crosshairOnCheck = new JCheckBox("Crosshair On");
		crosshairOnCheck.setSelected(Config.CROSSHAIR_ON);
		crosshairOnCheck.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Config.CROSSHAIR_ON = crosshairOnCheck.isSelected();
			}
		});
		panel.add(crosshairOnCheck);

		inRadiansRadio = new JRadioButton("Radians");
		inRadiansRadio.setSelected(Config.IN_RADIANS);
		inRadiansRadio.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Config.IN_RADIANS = inRadiansRadio.isSelected();
			}
		});
		inDegreesRadio = new JRadioButton("Degrees");
		inDegreesRadio.setSelected(!Config.IN_RADIANS);
		inDegreesRadio.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Config.IN_RADIANS = inRadiansRadio.isSelected();
			}
		});
		ButtonGroup group = new ButtonGroup();
		group.add(inRadiansRadio);
		group.add(inDegreesRadio);

		// layout radio buttons
		JPanel anglePanel = new JPanel(new GridLayout(2,1));
		anglePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Angle mode"));
		anglePanel.add(inRadiansRadio);
		anglePanel.add(inDegreesRadio);
		panel.add(anglePanel);

		return panel;
	}

	private JPanel createButtonBar()
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton button = null;

		// reset button
		button = new JButton("Reset");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				reset();
			}
		});
		panel.add(button);

		// apply button
		button = new JButton("Apply");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				applyChanges();
			}
		});
		panel.add(button);

		return panel;
	}

	public void applyChanges()
	{
		Config.CROSSHAIR_ON = crosshairOnCheck.isSelected();

		Config.IN_RADIANS = inRadiansRadio.isSelected();
	}

	public void reset()
	{
		crosshairOnCheck.setSelected(Config.CROSSHAIR_ON);

		inRadiansRadio.setSelected(Config.IN_RADIANS);
		// inDegreesRadio automatically changes.
	}

	public void doAction()
	{
		applyChanges();
	}

	public void doDown() {}
	public void doLeft() {}
	public void doRight() {}
	public void doUp() {}

	public ExprLineEdit getEditor()
	{
		return null;
	}

	public String getTitle()
	{
		return "Options";
	}

}
