package rule;

import domain.ListUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.List;

public class OnlyFixedPointRuleVIew extends JComponent 
{
	public static Color RULE_BG_COLOR = new Color(102, 255, 255);     
	private static final long serialVersionUID = 1L;   
	public static final int HORIZONTAL = 0;    
	public static final int VERTICAL = 1;    
	private static int DEFAULT_SIZE = 400;  
	
	private int orientation;    
	private boolean isMetric = true; 
	
	protected int width;    
	protected int height;       
	private int maxRuleLabelLen = 40;
	private double[] axixPositions;
	private double [] actualVals;

	public OnlyFixedPointRuleVIew(int orientation)
	{
		this.orientation = orientation;	
		if(orientation == HORIZONTAL)       
		{           
			width = DEFAULT_SIZE;			
		}
		else        
		{          
			height = DEFAULT_SIZE;			
		}
	}	
	
	public void setMaxRuleLabelLen (int maxRuleLabelLen)
	{
		this.maxRuleLabelLen = (maxRuleLabelLen < 1 ? 1 :  maxRuleLabelLen);
	}

	public void setShowRule(boolean isShowRule)
	{
		isMetric = isShowRule;
	}
	
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
	
	public void setAxixPositions (double[] axixPositions)
	{
		this.axixPositions = axixPositions;
	}

	public void setActualVals (double[] actualVals)
	{
		this.actualVals = actualVals;
	}

	protected void paintComponent(Graphics g) 
	{
		height =  (int)this.getSize().getHeight();
		width = (int)this.getSize().getWidth();
		if(isMetric)
		{
			Graphics2D g2d = (Graphics2D) g;		       
			// background color    
			g2d.setColor(RULE_BG_COLOR);     
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
		int axisLen = axixPositions == null ? 0 : axixPositions.length;
		int actualLen = actualVals == null ? 0 : actualVals.length;
		int pointCount = Math.min(axisLen, actualLen);
		if(pointCount == 0)
		{
			drawDefaultVerticalUnit(g2d);
		}
		else
		{
			drawHaveDataVerticalUnit(g2d, pointCount);
		}
	}

	protected void drawHorizontalUnit (Graphics2D g2d)
	{		
		int axisLen = axixPositions == null ? 0 : axixPositions.length;
		int actualLen = actualVals == null ? 0 : actualVals.length;
		int len = axisLen;//Math.min(axisLen, actualLen);
		if(len == 0)
		{
			drawDefaultHorizontalUnit(g2d);
		}
		else
		{
			drawHaveDataHorizontalUnit(g2d, len);
		}
	}

	private void drawHaveDataHorizontalUnit (Graphics2D g2d , int len)
	{
		g2d.translate(width /2 , 0);
		DecimalFormat decimalFormat = actualVals[0]  > 10 ? 
				new DecimalFormat("0.0") : new DecimalFormat("0.00");		
		int ruleLabelLen = Math.min(len, maxRuleLabelLen);
		double perPixelDistance = actualVals[0] / ruleLabelLen; //measureDistance/ruleLabelLen;
		
		double firstAxixLabelPosition = axixPositions[0];//Math.max(-width/2, axixPositions[0]);
		double lastAxixLabelPosition = axixPositions[len - 1];//Math.min(width/2, axixPositions[len - 1]);		
		
		double step = (lastAxixLabelPosition - firstAxixLabelPosition)/ruleLabelLen;
		for(int index = 0; index < ruleLabelLen; index ++)
		{
			double newShowVal = index * perPixelDistance;
			double newPosition = firstAxixLabelPosition + step * index;
			if(newPosition >= lastAxixLabelPosition)
			{
				break;
			}
			Line2D line = new Line2D.Double( newPosition, height*0.7, newPosition, height);			
			g2d.draw(line);
			g2d.drawString(decimalFormat.format(newShowVal) ,
					(int)newPosition -3,  (int)(height/3.0));   
		}
		g2d.translate(0 , 0);
	}	

	private void drawHaveDataVerticalUnit (Graphics2D g2d , int pointCount)
	{
		g2d.translate(0, height / 2);
			
		List maxMinVals = ListUtils.getMaxMinNumberAndIndex(actualVals);
		double max = (double)maxMinVals.get(0);
		int maxIndex = (int)maxMinVals.get(1);
		double min = (double)maxMinVals.get(2);
		int minIndex = (int)maxMinVals.get(3);
		
		int ruleLabelLen = Math.min(pointCount, maxRuleLabelLen);
		double perPixelDistance = (max -min) / ruleLabelLen; 
		
		double firstAxixLabelPosition = axixPositions[maxIndex];
		double lastAxixLabelPosition = axixPositions[minIndex];
	
		double step = Math.abs(lastAxixLabelPosition - firstAxixLabelPosition)/ruleLabelLen;
		for(int index = 0; index < ruleLabelLen; index ++)
		{
			double newShowVal = max - index * perPixelDistance;
			double newPosition = firstAxixLabelPosition + step * index;
			if(newPosition >= lastAxixLabelPosition)
			{
				break;
			}			
			Line2D lineDownDirection = new Line2D.Double(width*0.7, newPosition,  width, newPosition);
			g2d.draw(lineDownDirection); 
			g2d.drawString(new DecimalFormat("0.00").format(newShowVal) ,0, (int)newPosition); 			
		}
		g2d.translate(0, 0);
	}
	
	private void drawDefaultHorizontalUnit (Graphics2D g2d)
	{
		double units = width / 10;	
		int count = 0;
		double increment = 0; 	
		for(int i=0; i<=units; i++)          
		{              
			double rowOff = i * 10; 
			Line2D line = new Line2D.Double(rowOff, height*0.7, rowOff, height);
			g2d.draw(line);            
			if(count == 10) 
			{                   
				count = 0;
				increment += 1;
				Line2D bigLine = new Line2D.Double(rowOff, height*0.4, rowOff, height);
				g2d.draw(bigLine);                  
				g2d.drawString(increment + "", (int)rowOff-3, (int)(height/3.0));               
			}               
			count++;           
		}
	}
	
	protected void drawDefaultVerticalUnit (Graphics2D g2d)
	{
		int count = 0;
		double units = height / 10;
		double increment = 0; 	
		for(int i=0; i<=units; i++)  
		{              
			double colOff = i * 10;  
			Line2D line = new Line2D.Double(width*0.7, colOff, width, colOff);
			g2d.draw(line);           
			if(count == 10)   
			{                  
				count = 0; 
				increment += 1;  
				Line2D bigLine = new Line2D.Double(width*0.5, colOff, width, colOff);
				g2d.draw(bigLine);  
				g2d.drawString(increment + "", 0, (int)colOff);            
			}              
			count++;
		}
	}
}
