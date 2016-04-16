package rule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import javax.swing.JComponent;

import domain.ListUtils;

public class OnlyFixedPointRuleVIew extends JComponent 
{
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
	private double measureDistance;
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

	public void setMeasureDistance (double measureDistance)
	{
		this.measureDistance = measureDistance;
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
		int len = Math.min(axisLen, actualLen);
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
		int ruleLabelLen = Math.min(len, maxRuleLabelLen);
		double perPixelDistance = measureDistance/ruleLabelLen;
		double step = width/ruleLabelLen;
		for(int index = 0; index < ruleLabelLen; index ++)
		{
			double newShowVal = index * perPixelDistance;
			double newPosition = step * index;
			Line2D line = new Line2D.Double( newPosition, height*0.7, newPosition, height);			
			g2d.draw(line);
			g2d.drawString(new DecimalFormat("0.0").format(newShowVal) ,
					(int)newPosition -3,  (int)(height/3.0));   
		}
//		for(int index = 0, interval = (int)( ((double)len)/ruleLabelLen) ; index < len; index += interval)
//		{
//			Line2D line = new Line2D.Double( axixPositions[index], height*0.7, 
//					axixPositions[index], height);			
//			g2d.draw(line); 			
//
//			g2d.drawString(new DecimalFormat("0.0").format(actualVals[index]) ,
//					(int)axixPositions[index]-3,  (int)(height/3.0));   
//		}
	}	

	private void drawHaveDataVerticalUnit (Graphics2D g2d , int pointCount)
	{
		int ruleLabelCount = Math.min(pointCount, maxRuleLabelLen);
		g2d.translate(0, height / 2);
		double[] axisActualVals = getMaxValAxisActualVals();
		double perPixelDistance = Math.abs(axisActualVals[1])/axisActualVals[0];
		double step = 2*axisActualVals[0]/ruleLabelCount;
		Line2D linezero = new Line2D.Double(width*0.7, 0, width, 0);
		g2d.draw(linezero);
		g2d.drawString("0" , 0, 0);		
		for(int index = 1; index < ruleLabelCount/2; index ++)
		{
			double newShowVal = index * perPixelDistance;
			double newPosition = step * index;
			Line2D lineUpDirection = new Line2D.Double(width*0.7, -newPosition,  width, -newPosition);
			g2d.draw(lineUpDirection); 
			g2d.drawString(new DecimalFormat("0.0").format(newShowVal) ,0, -1*(int)newPosition);  
			
			Line2D lineDownDirection = new Line2D.Double(width*0.7, newPosition,  width, newPosition);
			g2d.draw(lineDownDirection); 
			g2d.drawString(new DecimalFormat("0.0").format(-1*newShowVal) ,0, (int)newPosition);  
		}
		g2d.translate(0, 0);
	}
	
	private double[] getMaxValAxisActualVals ()
	{
		double[] v2 = new double[2];
		double axis = Double.MIN_VALUE;
		double actual = Double.MIN_VALUE;
		for(int index = 0, len = actualVals.length; index < len ; index ++)
		{
			if(Math.abs(axixPositions[index]) > Math.abs(axis))
			{
				axis = axixPositions[index];
				actual = actualVals[index];
			}			
		}
		v2[0] = axis;
		v2[1] = actual;
		return v2;
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
