package datastruct;
/*
 *
 *@author 杨安印
 *
 *
 *这个数据结构是保存用户获取的端口数据，并准备送往文件中存储
 *
 *
 *
 *
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import domain.SaveInfo;
import persistant.InventoryData;

public class SerialDataFromToFile extends SerializableData
{
	
	// 放端口信号数据
	private List<Double> data = new ArrayList<Double>();
	
	// 电缆型号
	private String cableType;
	
	// 电缆长度
	private String cableLength;
	
	// 敷设深度
	private String dsDepth;
	
	// 敷设日期
	private String fsDate;
	
	// 故障性质
	private String wrongType;
	
	// 故障距离
	private String wrongDistance;
	
	// 测试人员
	private String testClerk;
	
	// 测试日期
	private String testDate;
	
	// 备注
	private String note;
	
	// *********************************************
	public SerialDataFromToFile()
	{
	
	}
	
	public SerialDataFromToFile(List<Double> v, String cableType, String cableLength, String dsDepth, String fsDate,
			String wrongType, String wrongDistance, String testClerk, String testDate, String note)
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
		
	}
	
	// ***************************************************************
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
	
	// ********************************************************	
//	public void readFromFile (FileInputStream inStream) throws IOException , ClassNotFoundException
//	{
//		ObjectInputStream ooStream = new ObjectInputStream(inStream);
//		
//		SerialDataFromToFile s = (SerialDataFromToFile) (ooStream.readObject());
//		
//		List<Double> one = s.getWavePoints();		
//				
//		InventoryData.setCanTransformedDataFromFile(one);
//		InventoryData.setDataFromFileNeedPrint(new ArrayList<Double>(one));
//		// InventoryData.setDataFromFileNeedPrint(two);
//		SaveInfo.restoreSaveInfo(s);
//	}
}
