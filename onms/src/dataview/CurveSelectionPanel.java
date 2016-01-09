package dataview;

import domain.BusinessConst;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurveSelectionPanel extends JPanel implements ActionListener
{
		public static final int PORT_CUR_SELECTION = 2;
		public static final int FILE_CUR_SELECTION = 3;
		private GraphControllerPanel	controlPanel;
		// 选择红线，选择蓝线
		private JRadioButton			jbRedSelect;
		private JRadioButton			jbGreenSelect;
		// 选中了哪条线,PORTSELECT表示端口数据，FILESELECT表示文件数据.0表示还没有数据
		private static int				portOrFileData	= 0;

		public CurveSelectionPanel(GraphControllerPanel controlPanel)
		{
			super(new FlowLayout());
			this.controlPanel = controlPanel;
			layoutPanel();
		}

		public void layoutPanel()
		{
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "选择波形",
					TitledBorder.CENTER, TitledBorder.TOP));

			ButtonGroup bu4 = new ButtonGroup();
			jbRedSelect = new JRadioButton("选择红线(端口数据)");
			jbRedSelect.addActionListener(this);
			jbRedSelect.setEnabled(false);

			jbGreenSelect = new JRadioButton("选择绿线(导入数据)");
			jbGreenSelect.addActionListener(this);
			jbGreenSelect.setEnabled(false);

			bu4.add(jbRedSelect);
			bu4.add(jbGreenSelect);

			this.add(jbRedSelect);
			this.add(jbGreenSelect);
			this.setAlignmentY(Component.CENTER_ALIGNMENT);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			// ********************************************
			// 如果用户选择"选择红色"，表示现在对端口波形处理
			if (e.getSource().equals(jbRedSelect))
			{
				controlPanel.getMainFrame().geCheckMenuBar().jbRedSelectAction();
				controlPanel.jbRedSelectAction();
			}

			if (e.getSource().equals(jbGreenSelect))
			{
				controlPanel.getMainFrame().geCheckMenuBar().jbGreenSelectAction();
				controlPanel.jbGreenSelectAction();
			}

		}

		public void setStateEnable(int whichCur, boolean isEnable)
		{
			if(FILE_CUR_SELECTION  == whichCur)
			{
				jbGreenSelect.setEnabled(isEnable);
				jbGreenSelect.setSelected(isEnable);
			}
			else
			{
				jbRedSelect.setEnabled(isEnable);
				jbRedSelect.setSelected(isEnable);
			}			
		}

		// ***********************************用户点选了哪根线
		public static void selectPortDataLine()
		{
			portOrFileData = BusinessConst.PORTSELECT;
		}

		public static void selectFileDataLine()
		{
			portOrFileData = BusinessConst.FILESELECT;
		}

		public void setSelectNothing()
		{
			portOrFileData = 0;
		}

		public static int selectPortOrFileData()
		{
			return portOrFileData;
		}
}
