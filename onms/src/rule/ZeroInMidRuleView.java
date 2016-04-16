package rule;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

public class ZeroInMidRuleView extends RuleView
{	
	private static final long serialVersionUID = 1L;

	public ZeroInMidRuleView(int orientation)
	{
		super(orientation);		
	}	
	
	@Override
	protected void drawVerticalUnit (Graphics2D g2d)
	{
		int count = 0;
		int units = (int)(height / interval/2);
		double incrementUp = 0; 
		g2d.translate(0, height / 2);
		
		Line2D zeroLine = new Line2D.Double(width*0.5, 0, width, 0);
		g2d.draw(zeroLine);  
		g2d.drawString("0", 0,0);   
		
		for(int i=1; i<=units; i++)  
		{              
			double colOff = (-1)*i * interval; 
			g2d.draw(new Line2D.Double(width*0.7, colOff, width, colOff));			         
			if(count == groupNo)   
			{                  
				count = 0; 
				incrementUp += perInterval;  
				Line2D bigLine = new Line2D.Double(width*0.5, colOff, width, colOff);
				g2d.draw(bigLine);  
				g2d.drawString(new DecimalFormat("0.0").format(incrementUp) , 0, (int)colOff);            
//				g2d.drawString(incrementUp + "", 0, (int)colOff);            
			}              
			count++;
		}
		
		count = 0;
		double incrementDown = 0; 
		for(int i=1; i<=units; i++)  
		{              
			double colOff = i * interval;  
			Line2D line = new Line2D.Double(width*0.7, colOff, width, colOff);
			g2d.draw(line);           
			if(count == groupNo)   
			{                  
				count = 0; 
				incrementDown += perInterval;  
				Line2D bigLine = new Line2D.Double(width*0.5, colOff, width, colOff);
				g2d.draw(bigLine);  
				g2d.drawString(new DecimalFormat("0.0").format((-1)*incrementDown ), 0, (int)colOff);            
//				g2d.drawString((-1)*incrementDown + "", 0, (int)colOff);            
			}              
			count++;
		}
		g2d.translate(0, 0);
	}
}
