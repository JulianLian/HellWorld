package datastruct;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class EventDataStruct implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String eventNo;
	private String eventType;
	private String position;
	private String loss;
	private String reflect;
	private String slop;
	private String priviewEventDistance;
	private String otdr;
	
	public EventDataStruct(String eventNo, String eventType, String position, String loss, String reflect,
			String slop, String priviewEventDistance, String otdr)
	{
		super();
		this.eventNo = eventNo;
		this.eventType = eventType;
		this.position = position;
		this.loss = loss;
		this.reflect = reflect;
		this.slop = slop;
		this.priviewEventDistance = priviewEventDistance;
		this.otdr = otdr;
	}
	
	
	public String getEventNo ()
	{
		return eventNo;
	}

	public void setEventNo (String eventNo)
	{
		this.eventNo = eventNo;
	}

	public String getEventType ()
	{
		return eventType;
	}

	public void setEventType (String eventType)
	{
		this.eventType = eventType;
	}

	public String getPosition ()
	{
		return position;
	}


	public void setPosition (String position)
	{
		this.position = position;
	}



	public String getLoss ()
	{
		return loss;
	}



	public void setLoss (String loss)
	{
		this.loss = loss;
	}



	public String getReflect ()
	{
		return reflect;
	}



	public void setReflect (String reflect)
	{
		this.reflect = reflect;
	}



	public String getSlop ()
	{
		return slop;
	}



	public void setSlop (String slop)
	{
		this.slop = slop;
	}



	public String getPriviewEventDistance ()
	{
		return priviewEventDistance;
	}



	public void setPriviewEventDistance (String priviewEventDistance)
	{
		this.priviewEventDistance = priviewEventDistance;
	}



	public String getOtdr ()
	{
		return otdr;
	}



	public void setOtdr (String otdr)
	{
		this.otdr = otdr;
	}



	public static long getSerialversionuid ()
	{
		return serialVersionUID;
	}



	public void writeToFile (FileOutputStream outStream) throws IOException
	{
		ObjectOutputStream ooStream = new ObjectOutputStream(outStream);
		ooStream.writeObject(this);
		ooStream.flush();
	}
	
	public static EventDataStruct readFromFile (FileInputStream inStream)
			throws IOException , ClassNotFoundException
	{
		ObjectInputStream ooStream = new ObjectInputStream(inStream);
		return (EventDataStruct) (ooStream.readObject());
	}
}
