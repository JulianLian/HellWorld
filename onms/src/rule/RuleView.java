package rule;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
public class RuleView extends JComponent 
{    /**  *   */ 
	private static final long serialVersionUID = 1L;   
	public static final int HORIZONTAL = 0;    
	public static final int VERTICAL = 1;    
	private static int DEFAULT_SIZE = 400;  
	
	private int orientation;    
	private boolean isMetric = true;   
	
	protected double  interval = 10.0;
	protected int groupNo = 10;//间隔多少需要显示数字
	protected double perInterval = 1;
	
	protected int width;    
	protected int height;       
	public RuleView(int orientation)
	{
		this.orientation = orientation;		
		setIncrementAndUnits();       
		if(orientation == HORIZONTAL)       
		{           
			width = DEFAULT_SIZE;			
		}
		else        
		{          
			height = DEFAULT_SIZE;			
		}
	}
	
	public void setShowRule(boolean isShowRule)
	{
		isMetric = isShowRule;
	}
	
	public void setInterval(double interval)
	{
		this.interval = interval;
	}		
		
	public void setPerInterval (double perInterval)
	{
		this.perInterval = perInterval;
	}	

	public void setGroupNo (int groupNo)
	{
		this.groupNo = groupNo;
	}

	private void setIncrementAndUnits() {           }   
	
	public void setPreferredHeight(int ph) 
	{        
		this.height = ph;       
		setPreferredSize(new Dimension(DEFAULT_SIZE, ph));    
	}    
	public void setPreferredWidth(int pw) 
	{     
		this.width = pw;        
		setPreferredSize(new Dimension(pw, DEFAULT_SIZE));   
	}    
	
	protected void paintComponent(Graphics g) 
	{      
		height =  (int)this.getSize().getHeight();
		width = (int)this.getSize().getWidth();
		if(isMetric)
		{
			Graphics2D g2d = (Graphics2D) g;		       
			// background color    
			g2d.setColor(new Color(102, 255, 255));     
			Rectangle2D rect2d = new Rectangle2D.Double(0, 0, width, height);      
			g2d.fill(rect2d); 					 
			// unit and text symbol    
			g2d.setPaint(new Color(0,0,204));      
			if(orientation == HORIZONTAL)       
			{           
				drawHorizontalUnit(g2d);       
			}       
			else        
			{           
				drawVerticalUnit(g2d);       
			}       
			g2d.setPaint(Color.RED);   
		}
		else
		{
			super.paintComponent(g);
		}
	}

	protected void drawVerticalUnit (Graphics2D g2d)
	{
		int count = 0;
		double units = height / interval;
		double increment = 0; 	
		for(int i=0; i<=units; i++)  
		{              
			double colOff = i * interval;  
			Line2D line = new Line2D.Double(width*0.7, colOff, width, colOff);
			g2d.draw(line);           
			if(count == groupNo)   
			{                  
				count = 0; 
				increment += perInterval;  
				Line2D bigLine = new Line2D.Double(width*0.5, colOff, width, colOff);
				g2d.draw(bigLine);  
				g2d.drawString(increment + "", 0, (int)colOff);            
			}              
			count++;
		}
	}

	protected void drawHorizontalUnit (Graphics2D g2d)
	{
		double units = width / interval;	
		int count = 0;
		double increment = 0; 	
		for(int i=0; i<=units; i++)          
		{              
			double rowOff = i * interval; 
			Line2D line = new Line2D.Double(rowOff, height*0.7, rowOff, height);
			g2d.draw(line);            
			if(count == groupNo) 
			{                   
				count = 0;
				increment += perInterval;
				Line2D bigLine = new Line2D.Double(rowOff, height*0.4, rowOff, height);
				g2d.draw(bigLine);                  
				g2d.drawString(increment + "", (int)rowOff-3, (int)(height/3.0));               
			}               
			count++;           
		}
	}	
}  
	
	
	
	
