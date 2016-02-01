package env;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MDLogger
{
	 private Logger log = Logger.getLogger("mdLogger"); 
	 public static MDLogger INS = new MDLogger();
	 private MDLogger()
	 {
		 log.setLevel(Level.INFO); 
        	 FileHandler fileHandler;
		try
		{
			fileHandler = new FileHandler("runing.log");
			fileHandler.setLevel(Level.INFO); 
			fileHandler.setFormatter(new MyLogHander());
	                 log.addHandler(fileHandler); 
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} 
	 }         
         public void  error(String mess)
         {
        	 log.severe(mess);
         }
         
         class MyLogHander extends Formatter { 
        	        @Override 
        	        public String format(LogRecord record) { 
        	                return new Date(record.getMillis())+ ":" + record.getMessage()+"\n"; 
        	        } 
        	}
}
