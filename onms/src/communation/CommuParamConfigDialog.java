package communation;

import dataview.CurveSelectionPanel;
import interaction.OTDRTraceGetter;
import interaction.MeasureParamsSetter;
import main.Md711MainFrame;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class CommuParamConfigDialog extends JDialog implements ActionListener
{
	JComboBox<String> moduleCB = new JComboBox<String>();
	JComboBox<String> functionCB = new JComboBox<String>();

	JComboBox<String> otuInPortCB = new JComboBox<String>();
	JComboBox<String> otuOutPortCB = new JComboBox<String>();

	JComboBox<String> acquisitionSettingCB = new JComboBox<String>();

	JComboBox<String> waveLengthCB = new JComboBox<String>();
	JComboBox<String> pulseWidthCB = new JComboBox<String>();
	JComboBox<String> rangeCB = new JComboBox<String>();
	private JTextField acquisitionMinField = new JTextField(6);
	private JTextField acquisitionSecField = new JTextField(6);
	JComboBox<String> resolutionCB = new JComboBox<String>();
	private CommuParamPanelChoiceAction choiceAction;
	public static CommuParamConfigDialog INS;

	private JButton confirmButton;
	private JButton cancelButton;
	private Md711MainFrame	mainFrame;
	private IDataGetter dataGetter;// 用来读取端口数据的类,  有利于用测试接口替换
	private CommuParamConfigDialog(Md711MainFrame	mainFrame)
	{
		super(mainFrame, "TCP通信参数", true);
		this.mainFrame = mainFrame;
		this.setResizable(false);
		layoutPanel();
		loadInitParam();
		setAction();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(700, 300));
		setLocationRelativeTo(mainFrame);
	}

	private void setAction ()
	{
		choiceAction = new CommuParamPanelChoiceAction(this);
		choiceAction.setPermitCommuParamDealer(new MeasureParamsSetter());

		moduleCB.addActionListener(choiceAction);
		functionCB.addActionListener(choiceAction);
		otuInPortCB.addActionListener(choiceAction);
		otuOutPortCB.addActionListener(choiceAction);
		acquisitionSettingCB.addActionListener(choiceAction);
		waveLengthCB.addActionListener(choiceAction);
		pulseWidthCB.addActionListener(choiceAction);
		rangeCB.addActionListener(choiceAction);
		resolutionCB.addActionListener(choiceAction);
	}

	private void loadInitParam ()
	{
		this.moduleCB.addItem("MOD1:8118RLR65");
		this.functionCB.addItem("SM-OTDR");
		this.otuInPortCB.addItem("01");
		this.otuOutPortCB.addItem("01");

		this.acquisitionSettingCB.addItem(Protocol.MANU_CONFIG);
		this.acquisitionSettingCB.addItem(Protocol.AUTO_CONFIG);
		this.acquisitionSettingCB.setSelectedIndex(1);

		this.waveLengthCB.addItem("1650 nm");
		this.pulseWidthCB.addItem("3 ns");

		this.rangeCB.addItem("2 km");

		this.resolutionCB.addItem("自动");
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
		confirmButton = new JButton("查询");
		cancelButton = new JButton("取消");
		confirmButton.addActionListener(this);
		cancelButton.addActionListener(this);

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 8, 6, 8));
		panel.add(confirmButton);
		panel.add(cancelButton);
		return panel;
	}

	private JPanel layoutConfigAreaPanel ()
	{
		JPanel confParamPanel = new JPanel(new BorderLayout());
		layoutModulePanel(confParamPanel);
		layoutConcreteParam(confParamPanel);
		return confParamPanel;
	}

	private void layoutConcreteParam (JPanel confParamPanel)
	{
		JPanel panel = new JPanel(new BorderLayout());
		layoutOTUInOutAndAcquisitionTypePanel(panel);
		layoutCustomParamAcquisitionPanel(panel);
		confParamPanel.add(panel, BorderLayout.CENTER);
	}

	private void layoutCustomParamAcquisitionPanel (JPanel fatherPanel)
	{
		//坐标，几行，几列

		// Weights are used to determine how to distribute space among
		//columns (weightx) and among rows (weighty); this is important for
		//specifying resizing behavior

		//anchor- when the component is smaller than its display area
		//fill-when the component's display area is larger than the component's requested size
		//ipad-The width of the component will be at least its minimum width plus ipadx*2 pixels

		JPanel panel = new JPanel(new GridBagLayout());
		CompoundBorder border = BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(0, 10, 0, 10),
				BorderFactory.createTitledBorder("设置参数"));
		panel.setBorder(border);
		//----------------------------------------------
		panel.add(new JLabel("波长:"), new GridBagConstraints(0,0,1,1
				,0,0,//weights
				GridBagConstraints.FIRST_LINE_START,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,2,4,2), 0,0));
		panel.add(waveLengthCB, new GridBagConstraints(1,0,1,1
				,0,0,//weights
				GridBagConstraints.CENTER,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,0,4,4),0,0));
		//----------------------------------------------
		panel.add(new JLabel("脉冲宽度:"), new GridBagConstraints(2,0,1,1
				,0,0,//weights
				GridBagConstraints.WEST,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,20,4,0), 0,0));
		panel.add(pulseWidthCB, new GridBagConstraints(3,0,1,1
				,0,0,//weights
				GridBagConstraints.WEST,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,0,4,2),0,0));
		//-------------------------------------------------
		panel.add(new JLabel("分辨率:"), new GridBagConstraints(4,0,1,1
				,0,0,//weights
				GridBagConstraints.WEST,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,30,4,0), 0,0));
		panel.add(resolutionCB, new GridBagConstraints(5,0,1,1
				,0,0,//weights
				GridBagConstraints.WEST,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,0,4,2),0,0));
		panel.add(new JLabel(), new GridBagConstraints(6,0,1,1
				,1,0,//weights
				GridBagConstraints.FIRST_LINE_END,//anchor
				GridBagConstraints.HORIZONTAL,//fill
				new Insets(2,0,4,2),1,0));
		//----------------------------------------------
		panel.add(new JLabel("范围:"), new GridBagConstraints(0,1,1,1
				,0,0,//weights
				GridBagConstraints.LINE_START,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,2,4,2), 0,0));
		panel.add(rangeCB, new GridBagConstraints(1,1,1,1
				,0,0,//weights
				GridBagConstraints.WEST,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,0,4,4),0,0));
		//----------------------------------------------
		JPanel acquiteTimePanel = new JPanel();
		acquiteTimePanel.add(new JLabel("获取时间:"));
		acquiteTimePanel.add(this.acquisitionMinField);
		acquiteTimePanel.add(new JLabel("分钟"));
		acquiteTimePanel.add(this.acquisitionSecField);
		acquiteTimePanel.add(new JLabel("秒"));

		panel.add(acquiteTimePanel, new GridBagConstraints(2,1,4,1
				,0,0,//weights
				GridBagConstraints.WEST,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,16,4,0), 0,0));

		panel.add(new JLabel(), new GridBagConstraints(6,1,1,1
				,1,0,//weights
				GridBagConstraints.LAST_LINE_END,//anchor
				GridBagConstraints.HORIZONTAL,//fill
				new Insets(2,0,4,2),0,0));
		//--------------------------------------------------
		int heighth = 30;//waveLengthCB.getHeight();//这样写preferredSize执行后显示不出来
		waveLengthCB.setPreferredSize(new Dimension(140, heighth));
		pulseWidthCB.setPreferredSize(new Dimension(140, heighth));
		rangeCB.setPreferredSize(new Dimension(140, heighth));
		resolutionCB.setPreferredSize(new Dimension(140, heighth));

		fatherPanel.add(panel, BorderLayout.CENTER);
	}

	private void layoutOTUInOutAndAcquisitionTypePanel (JPanel fatherPanel)
	{
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		panel.add(new JLabel("光纤路径 OTU:0 "), new GridBagConstraints(0,0,1,1
				,0,0,//weights
				GridBagConstraints.LINE_START,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,2,4,2), 0,0));
		JPanel switchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		switchPanel.add(new JLabel("Switch:"));
		switchPanel.add(this.otuInPortCB);
		switchPanel.add(new JLabel("==>"));
		switchPanel.add(this.otuOutPortCB);
		panel.add(switchPanel, new GridBagConstraints(1,0,3,1
				,0,0,//weights
				GridBagConstraints.WEST,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,0,4,4),0,0));
		panel.add(new JLabel(), new GridBagConstraints(4,0,3,1
				,1,0,//weights
				GridBagConstraints.FIRST_LINE_END,//anchor
				GridBagConstraints.HORIZONTAL,//fill
				new Insets(2,0,4,4),0,0));

		panel.add(new JLabel("查询设置:"), new GridBagConstraints(0,1,1,1
				,0,0,//weights
				GridBagConstraints.LINE_START,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,2,4,2), 0,0));
		panel.add(this.acquisitionSettingCB, new GridBagConstraints(1,1,3,1
				,0,0,//weights
				GridBagConstraints.CENTER,//anchor
				GridBagConstraints.NONE,//fill
				new Insets(2,2,4,2), 0,0));
		fatherPanel.add(panel, BorderLayout.NORTH);
	}

	private void layoutModulePanel (JPanel confParamPanel)
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		panel.add(new JLabel("OTDR"));
		panel.add(moduleCB);
		panel.add(new JLabel(":"));
		panel.add(functionCB);
		panel.add(new JLabel());
		confParamPanel.add(panel, BorderLayout.NORTH);
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
			// 由这个类去完成数据采集任务
			createDataGetter();
			if(dataGetter.startFetchData())
			{
				mainFrame.getGraphControllerpanel().getCurSelectionPanel()
						.setStateEnable(CurveSelectionPanel.PORT_CUR_SELECTION, true);
			}
		}
	}

	public static void showDialog (Md711MainFrame mainFrame)
	{
		INS = new CommuParamConfigDialog(mainFrame);
		INS.setVisible(true);
	}

	private void createDataGetter()
	{
//		dataGetter = new PortSinfferMocker( mainFrame);
		dataGetter = new OTDRTraceGetter(mainFrame);

	}

	public void setDevicePermittedItems (Map<String, List<String>> permittedVal)
	{
		if(permittedVal != null)
		{
			refillPermittedModulr(permittedVal);
			refillPermittedFunction(permittedVal);
			refillPermittedOTUIn(permittedVal);
			refillPermittedOTUOut(permittedVal);
			refillPermittedWaveLength(permittedVal);
			refillPermittedPulseWidth(permittedVal);
			refillPermittedRange(permittedVal);
			refillPermittedResolution(permittedVal);
		}
	}

	private void refillPermittedResolution (Map<String, List<String>> permittedVal)
	{
		List<String> vals;
		vals = permittedVal.get(Protocol.RESOLUTION);
		if(vals!= null && vals.size() > 0)
		{
			resolutionCB.removeAllItems();
			for(String item : vals)
			{
				resolutionCB.addItem(item);
			}
		}
	}

	private void refillPermittedRange (Map<String, List<String>> permittedVal)
	{
		List<String> vals;
		vals = permittedVal.get(Protocol.RANGE);
		if(vals!= null && vals.size() > 0)
		{
			rangeCB.removeAllItems();
			for(String item : vals)
			{
				rangeCB.addItem(item);
			}
		}
	}

	private void refillPermittedPulseWidth (Map<String, List<String>> permittedVal)
	{
		List<String> vals;
		vals = permittedVal.get(Protocol.PULSE_WIDTH);
		if(vals!= null && vals.size() > 0)
		{
			pulseWidthCB.removeAllItems();
			for(String item : vals)
			{
				pulseWidthCB.addItem(item);
			}
		}
	}

	private void refillPermittedWaveLength (Map<String, List<String>> permittedVal)
	{
		List<String> vals;
		vals = permittedVal.get(Protocol.WAVE_LENGTH);
		if(vals!= null && vals.size() > 0)
		{
			waveLengthCB.removeAllItems();
			for(String item : vals)
			{
				waveLengthCB.addItem(item);
			}
		}
	}

	private void refillPermittedOTUOut (Map<String, List<String>> permittedVal)
	{
		List<String> vals;
		vals = permittedVal.get(Protocol.OTU_OUT);
		if(vals!= null && vals.size() > 0)
		{
			otuOutPortCB.removeAllItems();
			for(String item : vals)
			{
				otuOutPortCB.addItem(item);
			}
		}
	}

	private void refillPermittedOTUIn (Map<String, List<String>> permittedVal)
	{
		List<String> vals;
		vals = permittedVal.get(Protocol.OTU_IN);
		if(vals!= null && vals.size() > 0)
		{
			otuInPortCB.removeAllItems();
			for(String item : vals)
			{
				otuInPortCB.addItem(item);
			}
		}
	}

	private void refillPermittedFunction (Map<String, List<String>> permittedVal)
	{
		List<String> vals;
		vals = permittedVal.get(Protocol.FUNCTION);
		if(vals!= null && vals.size() > 0)
		{
			functionCB.removeAllItems();
			for(String item : vals)
			{
				functionCB.addItem(item);
			}
		}
	}

	private void refillPermittedModulr (Map<String, List<String>> permittedVal)
	{
		List<String> vals = permittedVal.get(Protocol.MODULE);
		if(vals!= null && vals.size() > 0)
		{
			moduleCB.removeAllItems();
			for(String item : vals)
			{
				moduleCB.addItem(item);
			}
		}
	}
}
