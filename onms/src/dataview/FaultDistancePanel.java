package dataview;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

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
