package devconfig;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;

public class DeviceConfigDialog extends JDialog implements ActionListener
{
	public static DeviceConfigDialog INS;

	// 基本参数
	private JComboBox mode = new JComboBox(new String[] { "平均(手动)", "默认" });
	private JComboBox optimiseChoice = new JComboBox(new String[] { "标准" });
	private JTextField beginPosTF = new JTextField(8);
	private JTextField endPosTF = new JTextField(8);
	private JComboBox waveLengthChoice = new JComboBox(new String[] { "1650nm" });
	private JComboBox waveWidthChoice = new JComboBox(new String[] { "300" });
	private JTextField refractivityTF = new JTextField(8);
	private JTextField scatterTF = new JTextField(8);
	private JTextField measureCountTF = new JTextField(8);
	private JTextField aveTimeTF = new JTextField(8);
	// 损耗参数
	private JTextField forthPanelConnectorTF = new JTextField(8);
	private JTextField reflextTF = new JTextField(8);
	private JTextField notReflextTF = new JTextField(8);
	private JTextField finishTF = new JTextField(8);

	private JTextArea descTestArea = new JTextArea(4, 20);
	private JButton confirmButton;
	private JButton cancelButton;

	public DeviceConfigDialog(Frame fatherComponent)
	{
		super(fatherComponent, "参数设置", true);
		this.setResizable(false);
		layoutPanel();
		loadInitParam();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(600, 400));
		setLocationRelativeTo(null);
	}

	private void loadInitParam ()
	{

	}

	private void layoutPanel ()
	{
		this.setLayout(new BorderLayout());
		this.add(layoutButtonPanel(), BorderLayout.SOUTH);
		this.add(layoutConfigAreaPanel(layoutBasicParamPanel(), layoutLossThresholdConfigPanel()),
				BorderLayout.CENTER);
		setDefaultLookAndFeelDecorated(true);
	}

	private JPanel layoutLossThresholdConfigPanel ()
	{
		JPanel lossThresholdConfigPanel = new JPanel(new GridLayout(5, 2));
		lossThresholdConfigPanel.setBorder(BorderFactory.createTitledBorder("损耗门限参数"));

		lossThresholdConfigPanel.add(new JLabel("前面板连接器"));
		lossThresholdConfigPanel.add(forthPanelConnectorTF);

		lossThresholdConfigPanel.add(new JLabel("反射"));
		lossThresholdConfigPanel.add(reflextTF);

		lossThresholdConfigPanel.add(new JLabel("非反射"));
		lossThresholdConfigPanel.add(notReflextTF);

		lossThresholdConfigPanel.add(new JLabel("结束"));
		lossThresholdConfigPanel.add(finishTF);

		return lossThresholdConfigPanel;
	}

	private JPanel layoutBasicParamPanel ()
	{
		JPanel basicConfigPanel = new JPanel(new GridLayout(5, 2));
		// basicConfigPanel.setBorder(BorderFactory.createTitledBorder("基本参数配置"));

		CompoundBorder border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10),
				BorderFactory.createTitledBorder("基本参数配置"));
		basicConfigPanel.setBorder(border);

		JPanel modePanel = new JPanel(new GridLayout(1, 1));
		modePanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		JLabel modeLabel = new JLabel("模式");
		modeLabel.setHorizontalTextPosition(JLabel.LEFT);
		modePanel.add(modeLabel);
		modePanel.add(mode);
		basicConfigPanel.add(modePanel);

		JPanel optimiseChoicePanel = new JPanel(new GridLayout(1, 1));
		optimiseChoicePanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		JLabel optimiseLabel = new JLabel("优化模式");
		optimiseLabel.setHorizontalTextPosition(JLabel.LEFT);
		optimiseChoicePanel.add(optimiseLabel);
		optimiseChoicePanel.add(optimiseChoice);
		basicConfigPanel.add(optimiseChoicePanel);

		JPanel beginPosPanel = new JPanel(new GridLayout(1, 1));
		beginPosPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		JLabel beginPosLabel = new JLabel("开始位置(千米)");
		beginPosLabel.setHorizontalTextPosition(JLabel.LEFT);
		beginPosPanel.add(beginPosLabel);
		beginPosPanel.add(beginPosTF);
		basicConfigPanel.add(beginPosPanel);

		JPanel endPosPanel = new JPanel(new GridLayout(1, 1));
		endPosPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		JLabel endPosLabel = new JLabel("结束位置(千米)");
		endPosLabel.setHorizontalTextPosition(JLabel.LEFT);
		endPosPanel.add(endPosLabel);
		endPosPanel.add(endPosTF);
		basicConfigPanel.add(endPosPanel);

		JPanel waveLengthPanel = new JPanel(new GridLayout(1, 1));
		waveLengthPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		JLabel waveLengthLabel = new JLabel("波长");
		waveLengthLabel.setHorizontalTextPosition(JLabel.LEFT);
		waveLengthPanel.add(waveLengthLabel);
		waveLengthPanel.add(waveLengthChoice);
		basicConfigPanel.add(waveLengthPanel);

		JPanel waveWidthPanel = new JPanel(new GridLayout(1, 1));
		waveWidthPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		JLabel waveWidthLabel = new JLabel("脉宽");
		waveWidthLabel.setHorizontalTextPosition(JLabel.LEFT);
		waveWidthPanel.add(waveWidthLabel);
		waveWidthPanel.add(waveWidthChoice);
		basicConfigPanel.add(waveWidthPanel);

		JPanel refractivityPanel = new JPanel(new GridLayout(1, 1));
		refractivityPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		JLabel refractivityLabel = new JLabel("折射率");
		refractivityLabel.setHorizontalTextPosition(JLabel.LEFT);
		refractivityPanel.add(refractivityLabel);
		refractivityPanel.add(refractivityTF);
		basicConfigPanel.add(refractivityPanel);

		JPanel scatterPanel = new JPanel(new GridLayout(1, 1));
		scatterPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		JLabel scatterLabel = new JLabel("散射系数");
		scatterLabel.setHorizontalTextPosition(JLabel.LEFT);
		scatterPanel.add(scatterLabel);
		scatterPanel.add(scatterTF);
		basicConfigPanel.add(scatterPanel);

		JPanel measureCountPanel = new JPanel(new GridLayout(1, 1));
		measureCountPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		JLabel measureCountLabel = new JLabel("测量数据点");
		measureCountLabel.setHorizontalTextPosition(JLabel.LEFT);
		measureCountPanel.add(measureCountLabel);
		measureCountPanel.add(measureCountTF);
		basicConfigPanel.add(measureCountPanel);

		JPanel aveTimePanel = new JPanel(new GridLayout(1, 1));
		aveTimePanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		JLabel aveTimeLabel = new JLabel("平均化时间");
		aveTimeLabel.setHorizontalTextPosition(JLabel.LEFT);
		aveTimePanel.add(aveTimeLabel);
		aveTimePanel.add(aveTimeTF);
		basicConfigPanel.add(aveTimePanel);

		return basicConfigPanel;
	}

	private JPanel layoutConfigAreaPanel (JPanel basicConfigPanel , JPanel lossThresholdConfigPanel)
	{
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel upPanel = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		upPanel.add(basicConfigPanel, c);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 0;
		upPanel.add(lossThresholdConfigPanel, c);
		mainPanel.add(upPanel, BorderLayout.CENTER);

		descTestArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add(descTestArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		CompoundBorder border = BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(0, 18, 4, 18), BorderFactory.createTitledBorder("描述"));

		scrollPane.setBorder(border);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);
		return mainPanel;
	}

	private JPanel layoutButtonPanel ()
	{
		confirmButton = new JButton("确定");
		cancelButton = new JButton("取消");
		confirmButton.addActionListener(this);
		cancelButton.addActionListener(this);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 8, 12, 8));
		panel.add(confirmButton);
		panel.add(cancelButton);
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
			INS.setVisible(false);
		}
	}

	public static void showDialog (Frame frameComp)
	{
		INS = new DeviceConfigDialog(frameComp);
		INS.setVisible(true);
	}

	public static void main (String[] args)
	{
		DeviceConfigDialog.showDialog(null);
	}
}
