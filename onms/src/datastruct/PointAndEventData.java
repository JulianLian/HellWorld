package datastruct;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import persistant.InventoryData;

public class PointAndEventData extends SerializableData
{
	private List<Double> wavePoints = new ArrayList<Double>();
	private List<Double> eventData = new ArrayList<Double>();
	
	@Override
	public List<Double> getWavePoints ()
	{
		return wavePoints;
	}
	
	public void setWavePoints (List<Double> wavePoints)
	{
		this.wavePoints = wavePoints;
	}
	
	public List<Double> getEventData ()
	{
		return eventData;
	}
	
	public void setEventData (List<Double> eventData)
	{
		this.eventData = eventData;
	}
}
