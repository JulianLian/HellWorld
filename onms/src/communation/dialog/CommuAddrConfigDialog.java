package communation.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import env.Environment;

public class CommuAddrConfigDialog extends JDialog implements ActionListener
{
	public static CommuAddrConfigDialog INS;
	private JTextField peerIPField;
	private JTextField listenPortField;
	private JTextField timeoutField;
	private JButton confirmButton;
	private JButton cancelButton;

	public CommuAddrConfigDialog(Frame fatherComponent)
	{
		super(fatherComponent, "TCP通信参数", true);
		this.setResizable(false);
		layoutPanel();
		loadInitParam();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(250, 150));
		setLocationRelativeTo(null);
	}

	private void loadInitParam ()
	{
		peerIPField.setText(Environment.peerIP);
		listenPortField.setText(Environment.listenPort);
		timeoutField.setText(Environment.timeOut);
	}

	private void layoutPanel ()
	{
		this.setLayout(new BorderLayout());
		JPanel mainPanel = layoutConfigAreaPanel();
		JPanel buttonPanel = layoutButtonPanel();
		this.add(BorderLayout.CENTER, mainPanel);
		this.add(BorderLayout.PAGE_END, buttonPanel);
	}

	private JPanel layoutButtonPanel ()
	{
		confirmButton = new JButton("确定");
		cancelButton = new JButton("取消");
		confirmButton.addActionListener(this);
		cancelButton.addActionListener(this);
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.add(new JLabel());
		panel.add(confirmButton);
		panel.add(cancelButton);
		return panel;
	}

	private JPanel layoutConfigAreaPanel ()
	{
		peerIPField = new JTextField();
		listenPortField = new JTextField();
		timeoutField = new JTextField();
		JPanel panel = new JPanel(new GridLayout(3, 2));
		panel.add(new JLabel("  对端IP"));
		panel.add(peerIPField);
		panel.add(new JLabel("  监听端口"));
		panel.add(listenPortField);
		panel.add(new JLabel("  超时时间"));
		panel.add(timeoutField);
		return panel;
	}

	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource().equals(cancelButton))
		{
			INS.setVisible(false);
		}
		else if (e.getSource().equals(confirmButton))
		{
			Environment.saveCommunicationParam(this.peerIPField.getText(), this.listenPortField.getText(),
					this.timeoutField.getText());
			INS.setVisible(false);
		}
	}

	public static void showDialog (Frame frameComp)
	{
		INS = new CommuAddrConfigDialog(frameComp);
		INS.setVisible(true);
	}

}
