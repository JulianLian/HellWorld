/*
 *@author �ӡ
 *
 *�û����"����"��ťʱ�������ĶԻ����е�����
 *
 *
 */
package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import domain.SaveInfo;

public class AboutMessage extends JDialog implements ActionListener
{
		// �����ͺ�
		private JLabel		jlCableType	= new JLabel("�����ͺ�");
		private JTextField	jtfCableType;

		// ���³���
		private JLabel		jlCableLength	= new JLabel("���³���(��)");
		private JTextField	jtfCableLength;

		// �������
		private JLabel		jlDsDepth	= new JLabel("�������");
		private JTextField	jtfDsDepth;

		// ��������
		private JLabel		jlFsDate	= new JLabel("��������");
		private JTextField	jtfFsDate;

		// ��������
		private JLabel		jlWrongType	= new JLabel("��������");
		private JTextField	jtfWrongType;

		// ���Ͼ���
		private JLabel		jlWrongDistance	= new JLabel("���Ͼ���");
		private JTextField	jtfWrongDistance;

		// ������Ա
		private JLabel		jlTestClerk	= new JLabel("������Ա");
		private JTextField	jtfTestClerk;

		// ��������
		private JLabel		jlTestDate	= new JLabel("��������");
		private JTextField	jtfTestDate;

		// ��ע
		private JLabel		jlNote	= new JLabel("��ע");
		private JTextField	jtfNote;

		JButton confirmButton;

		public AboutMessage(Frame fatherFrame)
		{
			super(fatherFrame);
		}

		// *****************************************************************************
		public void showMessage()
		{
			jtfCableType = new JTextField(SaveInfo.getCableType());
			jtfCableType.setEditable(false);

			jtfCableLength = new JTextField(SaveInfo.getCableLength());
			jtfCableLength.setEditable(false);

			jtfDsDepth = new JTextField(SaveInfo.getDsDepth());
			jtfDsDepth.setEditable(false);

			jtfFsDate = new JTextField(SaveInfo.getFsDate());
			jtfFsDate.setEditable(false);

			jtfWrongType = new JTextField(SaveInfo.getWrongType());
			jtfWrongType.setEditable(false);

			jtfWrongDistance = new JTextField(SaveInfo.getWrongDistance());
			jtfWrongDistance.setEditable(false);

			jtfTestClerk = new JTextField(SaveInfo.getTestClerk());
			jtfTestClerk.setEditable(false);

			jtfTestDate = new JTextField(SaveInfo.getTestDate());
			jtfTestDate.setEditable(false);

			jtfNote = new JTextField(SaveInfo.getNote());
			jtfNote.setEditable(false);

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

			confirmButton = new JButton("�ر�");
			confirmButton.addActionListener(this);
			jp2.add(confirmButton);

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

		// *************************************************�¼�����
		public void actionPerformed(ActionEvent e)
		{

			if (e.getSource().equals(confirmButton))
			{

				this.setVisible(false);
				this.dispose();
			}
		}

		public void clearAll()
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
		}

}
