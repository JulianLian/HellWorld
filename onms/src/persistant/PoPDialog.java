/*
 *
 *@author �ӡ
 *
 *����Ի���Ҫ������������û���Ҫ����ͼ��
 *����Ϣ���϶�
 *
 *
 *
 *
 */
package persistant;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import domain.BusinessConst;
import domain.SerialDataFromToFile;
import main.Md711MainFrame;

/*
 *  ��������߿�
    JPanel panel = new JPanel();
	Border e = BorderFactory.createEtchedBorder();
	Border t = BorderFactory.createTitledBorder(e,"the title goes here");
	panel.setBorder(t);


 *int returnVal = fc.showOpenDialog(FileChooserDemo.this);
 *if (returnVal == JFileChooser.APPROVE_OPTION)
 *File file = fc.getSelectedFile();
 *
 *
 *
 *int returnVal = fc.showSaveDialog(FileChooserDemo.this);
 *if (returnVal == JFileChooser.APPROVE_OPTION)
 *File file = fc.getSelectedFile();
 *
 *��������ȥ��ԭ������ı���������⣬���Ǹ������û�ѡ���ļ����ͱ���λ��
 *
 *
 *
 */
public class PoPDialog extends JDialog implements ActionListener
{
	
	// �����ͺ�
	private JLabel jlCableType = new JLabel("�����ͺ�");
	private JTextField jtfCableType;
	
	// ���³���
	private JLabel jlCableLength = new JLabel("���³���(��)");
	private JTextField jtfCableLength;
	
	// �������
	private JLabel jlDsDepth = new JLabel("�������(��)");
	private JTextField jtfDsDepth;
	
	// ��������
	private JLabel jlFsDate = new JLabel("��������");
	private JTextField jtfFsDate;
	
	// ��������
	private JLabel jlWrongType = new JLabel("��������");
	private JTextField jtfWrongType;
	
	// ���Ͼ���
	private JLabel jlWrongDistance = new JLabel("���Ͼ���");
	private JTextField jtfWrongDistance;
	
	// ������Ա
	private JLabel jlTestClerk = new JLabel("������Ա");
	private JTextField jtfTestClerk;
	
	// ��������
	private JLabel jlTestDate = new JLabel("��������");
	private JTextField jtfTestDate;
	
	// ��ע
	private JLabel jlNote = new JLabel("��ע");
	private JTextField jtfNote;
	
	// �洢��ť��ȡ����ť
	private JButton confirmButton;
	private JButton cencelButton;
	
	private static String cableType = "";
	
	private static String cableLength = "";
	
	private static String dsDepth = "";
	
	private static String fsDate = "";
	
	private static String wrongType = "";
	
	private static String wrongDistance = "";
	
	private static String testClerk = "";
	
	private static String testDate = "";
	
	private static String note = "";
	
	private Md711MainFrame mf;
	
	// *****************************************************************************
	public PoPDialog(Md711MainFrame mf)
	{
		
		super(mf, "�洢��Ϣ", true);
		this.mf = mf;
		
		jtfCableType = new JTextField();
		
		jtfCableLength = new JTextField();
		
		jtfDsDepth = new JTextField();
		
		jtfFsDate = new JTextField();
		
		jtfWrongType = new JTextField();
		
		jtfWrongDistance = new JTextField();
		
		jtfTestClerk = new JTextField();
		
		jtfTestDate = new JTextField();
		
		jtfNote = new JTextField();
		
		JPanel topPane = new JPanel();
		topPane.setLayout(new BorderLayout());
		topPane.setBounds(40, 10, 10, 10);
		topPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "������Ϣ",
				TitledBorder.CENTER, TitledBorder.TOP));
		this.setContentPane(topPane);
		
		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridLayout(0, 2));
		
		jp1.add(jlCableType);
		jp1.add(jtfCableType);
		
		jp1.add(jlCableLength);
		jp1.add(jtfCableLength);
		
		jp1.add(jlDsDepth);
		jp1.add(jtfDsDepth);
		
		jp1.add(jlFsDate);
		jp1.add(jtfFsDate);
		
		jp1.add(jlWrongType);
		jp1.add(jtfWrongType);
		
		jp1.add(jlWrongDistance);
		jp1.add(jtfWrongDistance);
		
		jp1.add(jlTestClerk);
		jp1.add(jtfTestClerk);
		
		jp1.add(jlTestDate);
		jp1.add(jtfTestDate);
		
		jp1.add(jlNote);
		jp1.add(jtfNote);
		
		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		
		confirmButton = new JButton("ȷ��");
		cencelButton = new JButton("ȡ��");
		confirmButton.addActionListener(this);
		cencelButton.addActionListener(this);
		jp2.add(confirmButton);
		jp2.add(cencelButton);
		
		jp1.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
		topPane.add(jp1, BorderLayout.CENTER);
		topPane.add(jp2, BorderLayout.SOUTH);
		
		this.setSize(600, 400);
		// ʹ������Ļ�м�
		Dimension SS = this.getToolkit().getScreenSize();
		Dimension CS = this.getSize();
		this.setLocation((SS.width - CS.width) / 2, (SS.height - CS.height) / 2);
		this.setVisible(true);
	}
	
	// *****************************************************************
	
	public static String getCableType ()
	{
		return cableType;
		
	}
	
	public static void setCableType (String a)
	{
		cableType = a;
	}
	
	// *****************************
	public static String getCableLength ()
	{
		return cableLength;
		
	}
	
	public static void setCableLength (String a)
	{
		cableLength = a;
	}
	
	// ***************************
	public static String getDsDepth ()
	{
		return dsDepth;
		
	}
	
	public static void setDsDepth (String a)
	{
		dsDepth = a;
	}
	
	// ***************************
	public static String getFsDate ()
	{
		return fsDate;
		
	}
	
	public static void setFsDate (String a)
	{
		fsDate = a;
	}
	
	// ***************************
	public static String getWrongType ()
	{
		return wrongType;
		
	}
	
	public static void setWrongType (String a)
	{
		wrongType = a;
	}
	
	// ****************************
	public static String getWrongDistance ()
	{
		return wrongDistance;
		
	}
	
	public static void setWrongDistance (String a)
	{
		wrongDistance = a;
	}
	
	// ***************************
	public static String getTestClerk ()
	{
		return testClerk;
		
	}
	
	public static void setTestClerk (String a)
	{
		testClerk = a;
	}
	
	// ******************
	public static String getTestDate ()
	{
		return testDate;
		
	}
	
	public static void setTestDate (String a)
	{
		testDate = a;
	}
	
	// ******************
	public static String getNote ()
	{
		return note;
		
	}
	
	public static void setNote (String a)
	{
		note = a;
	}
	
	public void clearAll ()
	{
		jtfCableType.setText(" ");
		
		jtfCableLength.setText(" ");
		
		jtfDsDepth.setText(" ");
		
		jtfFsDate.setText(" ");
		
		jtfWrongType.setText(" ");
		
		jtfWrongDistance.setText(" ");
		
		jtfTestClerk.setText(" ");
		
		jtfTestDate.setText(" ");
		
		jtfNote.setText(" ");
		
		cableType = " ";
		
		cableLength = " ";
		
		dsDepth = " ";
		
		fsDate = " ";
		
		wrongType = " ";
		
		wrongDistance = " ";
		
		testClerk = " ";
		
		testDate = " ";
		
		note = " ";
		
	}
	
	// *************************************************�¼�����
	public void actionPerformed (ActionEvent e)
	{
		
		if (e.getSource().equals(confirmButton))
		{
			this.setVisible(false);
			String tempCapleType = new String();
			if (jtfCableType.getText().equals(null) || jtfCableType.getText().equals(""))
			{
				tempCapleType = " ";
				PoPDialog.setCableType(" ");
			}
			else
			{
				tempCapleType = jtfCableType.getText();
				PoPDialog.setCableType(jtfCableType.getText());
				
			}
			
			String tempCableLength = new String();
			if (jtfCableLength.getText().equals(null) || jtfCableLength.getText().equals(""))
			{
				tempCableLength = " ";
				PoPDialog.setCableLength(" ");
			}
			else
			{
				tempCableLength = jtfCableLength.getText();
				PoPDialog.setCableLength(jtfCableLength.getText());
			}
			
			String tempDsDepth = new String();
			if (jtfDsDepth.getText().equals(null) || jtfDsDepth.getText().equals(""))
			{
				tempDsDepth = " ";
				PoPDialog.setDsDepth(" ");
			}
			else
			{
				tempDsDepth = jtfDsDepth.getText();
				PoPDialog.setDsDepth(jtfDsDepth.getText());
			}
			
			String tempFsDate = new String();
			if (jtfFsDate.getText().equals(null) || jtfFsDate.getText().equals(""))
			{
				tempFsDate = " ";
				PoPDialog.setFsDate(" ");
			}
			else
			{
				tempFsDate = jtfFsDate.getText();
				PoPDialog.setFsDate(jtfFsDate.getText());
			}
			
			String tempWrongType = new String();
			if (jtfWrongType.getText().equals(null) || jtfWrongType.getText().equals(""))
			{
				tempWrongType = " ";
				PoPDialog.setWrongType(" ");
			}
			else
			{
				tempWrongType = jtfWrongType.getText();
				PoPDialog.setWrongType(jtfWrongType.getText());
			}
			
			String tempWrongDistance = new String();
			if (jtfWrongDistance.getText().equals(null) || jtfWrongDistance.getText().equals(""))
			{
				tempWrongDistance = " ";
				PoPDialog.setWrongDistance(" ");
			}
			else
			{
				tempWrongDistance = jtfWrongDistance.getText();
				PoPDialog.setWrongDistance(jtfWrongDistance.getText());
			}
			
			String tempTestClerk = new String();
			if (jtfTestClerk.getText().equals(null) || jtfTestClerk.getText().equals(""))
			{
				tempTestClerk = " ";
				PoPDialog.setTestClerk(" ");
			}
			else
			{
				tempTestClerk = jtfTestClerk.getText();
				PoPDialog.setTestClerk(jtfTestClerk.getText());
			}
			
			String tempTestDate = new String();
			if (jtfTestDate.getText().equals(null) || jtfTestDate.getText().equals(""))
			{
				tempTestDate = " ";
				PoPDialog.setTestDate(" ");
			}
			else
			{
				tempTestDate = jtfTestDate.getText();
				PoPDialog.setTestDate(jtfTestDate.getText());
			}
			
			String tempNote = new String();
			if (jtfNote.getText().equals(null) || jtfNote.getText().equals(""))
			{
				tempNote = " ";
				PoPDialog.setNote(" ");
			}
			else
			{
				tempNote = jtfNote.getText();
				PoPDialog.setNote(jtfNote.getText());
			}
			
			// ������Ǵ򿪰�ť
			JFileChooser jf = new JFileChooser();
			jf.setCurrentDirectory(new File("data"));
			int returnVal = jf.showSaveDialog(PoPDialog.this);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				
				SerialDataFromToFile one = null;
				if (mf.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.PORTSELECT)
				{
					one = new SerialDataFromToFile(InventoryData.getDataFromPortImmutable(),
							tempCapleType, tempCableLength, tempDsDepth, tempFsDate,
							tempWrongType, tempWrongDistance, tempTestClerk, tempTestDate,
							tempNote);
				}
				else if (mf.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.FILESELECT)
				{
					one = new SerialDataFromToFile(InventoryData.getCanTransformedDataFromFile(),
							tempCapleType, tempCableLength, tempDsDepth, tempFsDate,
							tempWrongType, tempWrongDistance, tempTestClerk, tempTestDate,
							tempNote);
				}
				try
				{
					
					File file = jf.getSelectedFile();
					FileOutputStream f = new FileOutputStream(file);
					one.writeToFile(f);
					JOptionPane.showMessageDialog(this, "����ɹ�", "������",
							JOptionPane.INFORMATION_MESSAGE);
				}
				catch (Exception ddd)
				{
					javax.swing.JOptionPane.showMessageDialog(null, "�ļ����淢������");
					System.err.println("�ļ����淢������");
					
				}
			}
			
		}
		if (e.getSource().equals(cencelButton))
		{
			this.setVisible(false);
			this.dispose();
		}
	}
}
