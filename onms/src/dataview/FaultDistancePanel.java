package dataview;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FaultDistancePanel extends JPanel
{
	private JTextField jtfDistance;// 故障距离

	public FaultDistancePanel()
	{
		super();
		layoutPanel();
	}

	public void layoutPanel ()
	{
		setLayout(new FlowLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "故障距离",
				TitledBorder.CENTER, TitledBorder.TOP));
		jtfDistance = new JTextField(15);
		jtfDistance.setEditable(false);
		add(jtfDistance);
		add(new JLabel("     米"));
	}

	public void fillDistanceInfo (String distanceStr)
	{
		jtfDistance.setText("" + distanceStr);
	}

	public void clearDistance ()
	{
		jtfDistance.setEditable(false);
		jtfDistance.setText("");
	}
}
