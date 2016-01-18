package datastruct;
/*
 *
 *@author �ӡ
 *
 *
 *������ݽṹ�Ǳ����û���ȡ�Ķ˿����ݣ���׼�������ļ��д洢
 *
 *
 *
 *
 */

import java.util.ArrayList;
import java.util.List;

public class SerialDataFromToFile extends SerializableData
{
	
	// �Ŷ˿��ź�����
	private List<Double> data = new ArrayList<Double>();
	
	// �����ͺ�
	private String cableType;
	
	// ���³���
	private String cableLength;
	
	// �������
	private String dsDepth;
	
	// ��������
	private String fsDate;
	
	// ��������
	private String wrongType;
	
	// ���Ͼ���
	private String wrongDistance;
	
	// ������Ա
	private String testClerk;
	
	// ��������
	private String testDate;
	
	// ��ע
	private String note;
	
	private List<EventDataStruct> eventData;
	
	// *********************************************
	public SerialDataFromToFile()
	{
	
	}
	
	public SerialDataFromToFile(List<Double> v, String cableType, String cableLength, String dsDepth, String fsDate,
			String wrongType, String wrongDistance, String testClerk, String testDate, String note,
			List<EventDataStruct> eventData)
	{
		
		this.data = v;
		
		this.cableType = cableType;
		
		this.cableLength = cableLength;
		
		this.dsDepth = dsDepth;
		
		this.fsDate = fsDate;
		
		this.wrongType = wrongType;
		
		this.wrongDistance = wrongDistance;
		
		this.testClerk = testClerk;
		
		this.testDate = testDate;
		
		this.note = note;
		
		this.eventData = eventData;
	}
	
	// ***************************************************************
	@Override
	public List<Double> getWavePoints ()
	{	
		return data;
	}
	
	public String getCableType ()
	{
		
		return cableType;
		
	}
	
	public String getCableLength ()
	{
		
		return cableLength;
		
	}
	
	public String getDsDepth ()
	{
		
		return dsDepth;
		
	}
	
	public String getFsDate ()
	{
		
		return fsDate;
		
	}
	
	public String getWrongType ()
	{
		
		return wrongType;
		
	}
	
	public String getWrongDistance ()
	{
		
		return wrongDistance;
		
	}
	
	public String getTestClerk ()
	{
		
		return testClerk;
		
	}
	
	public String getTestDate ()
	{
		
		return testDate;
		
	}
	
	public String getNote ()
	{
		
		return note;
		
	}
	
	public List<EventDataStruct> getEventData ()
	{
		return eventData;
	}
	
	public void setEventData (List<EventDataStruct> eventData)
	{
		this.eventData = eventData;
	}
	
	// ********************************************************	
	// public void readFromFile (FileInputStream inStream) throws
	// IOException , ClassNotFoundException
//	{
//		ObjectInputStream ooStream = new ObjectInputStream(inStream);
//		
	// SerialDataFromToFile s = (SerialDataFromToFile)
	// (ooStream.readObject());
//		
//		List<Double> one = s.getWavePoints();		
//				
//		InventoryData.setCanTransformedDataFromFile(one);
//		InventoryData.setDataFromFileNeedPrint(new ArrayList<Double>(one));
//		// InventoryData.setDataFromFileNeedPrint(two);
//		SaveInfo.restoreSaveInfo(s);
//	}
}
