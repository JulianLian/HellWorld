package action;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import env.MDLogger;
import i18n.I18n;
import main.Md711MainFrame;
import main.PrintGraph;

public class Action
{
	public static void closingAction (JFrame frame)
	{
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}	
	
	public static void setJSplitPaneAction (JSplitPane splitPane)
	{
		splitPane.addPropertyChangeListener(new PropertyChangeListener()
				{
					 public void propertyChange(PropertyChangeEvent event)
					 {
						 JSplitPane sourceSplitPane = (JSplitPane)event.getSource();  
						 String propertyName = event.getPropertyName();  
						 if(propertyName.equals(JSplitPane.DIVIDER_LOCATION_PROPERTY))
						 {	
							 
							 Integer currentVal = (Integer)event.getOldValue();
							 Integer newVal = (Integer)event.getNewValue(); 
							 if(newVal == 1)
							 {
								 sourceSplitPane.setDividerLocation(currentVal);
							 }
						 }
					 }
				});
		
	}
	
	public static void setPrintAction (Md711MainFrame mainFrame)
	{
		try
		{

			PrinterJob job = PrinterJob.getPrinterJob();

			PageFormat format = job.pageDialog(job.defaultPage());

			job.setPrintable(new PrintGraph(mainFrame));

			if (job.printDialog())
					job.print();
		}
		catch (PrinterException d)
		{
			MDLogger.INS.error(d.getMessage());
			JOptionPane.showMessageDialog(mainFrame, I18n.INS.getI18Str("printError"), 
					I18n.INS.getI18Str("errorOccurse"), JOptionPane.ERROR_MESSAGE);
		}
	}
}
