package rule;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
public class JCustomScrollPane extends JScrollPane 
{ 
	private static final long serialVersionUID = 1L;    
	private RuleView columnView;    
	private RuleView rowView;   
	private JLabel cornerLabel; 
	public JCustomScrollPane(int rowSize, int columnSize)   
	{       
		super();       
		JPanel buttonCorner = new JPanel(); 
		// use FlowLayout       
		cornerLabel = new JLabel("µ¥Î»:Ã×");     
		cornerLabel.setForeground(Color.BLUE); 
		cornerLabel.setFont(new Font("Serif", Font.PLAIN, 8));   
		buttonCorner.setLayout(new FlowLayout(FlowLayout.LEFT));  
		buttonCorner.add(cornerLabel);     
		setCorner(JScrollPane.UPPER_LEFT_CORNER, buttonCorner); 
		columnView = new RuleView(RuleView.HORIZONTAL);   
		columnView.setPreferredHeight(30);     
		rowView = new RuleView(RuleView.VERTICAL);  
		rowView.setPreferredWidth(30);     
		setColumnHeaderView(columnView);    
		setRowHeaderView(rowView);     
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		setViewportBorder(new LineBorder(Color.RED));   
	}      
	public void setImagePanel(JComponent view)  
	{       
		this.getViewport().setView(view);  
	}  
}  
	
