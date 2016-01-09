package domain;
/*
 *
 *@author  杨安印
 *
 *我们这里的数据存储和数据
 *读入都采用可串行化的数据格式
 *
 *@DATA 2005-8-20
 *
 *
 *
 *
 **/

import java.util.List;

public class SavedSerialData
{

	private List<Double> data;// 这里面存放的是采集的数据

	private String sno;// 序号

	private String cableLength;// 电缆长度

	private String FSDepth;// 敷设深度

	private String FSDate;// 敷设日期

	private String wrongAttribute;// 故障性质

	private String wrongDistance;// 故障距离

	private String testPeople;// 测试人员

	private String testDate;// 测试日期

	private String note;// 备注

	// *************************************************************************************
	public SavedSerialData(List<Double> v, String len, String dep, String data, String att, String dis, String who,
						   String estDate, String note)
	{

		this.data = v;
		this.cableLength = len;
		this.FSDepth = dep;
		this.FSDate = data;
		this.wrongAttribute = att;
		this.wrongDistance = dis;
		this.testPeople = who;
		this.testDate = estDate;
		this.note = note;

	}

	// *********************************************************************************
	public List<Double> getData()
	{
		return data;
	}

	// **********************
	public String getSno()
	{
		return sno;
	}

	// *********************
	public String getCableLength()
	{
		return cableLength;
	}

	// ********************
	public String getFSDepth()
	{
		return FSDepth;
	}

	// ***********************
	public String getFSDate()
	{
		return FSDate;
	}

	// ************************
	public String getWrongAttribute()
	{
		return wrongAttribute;
	}

	// ************************
	public String getwrongDistance()
	{
		return wrongDistance;
	}

	// ************************
	public String getTestPeople()
	{
		return testPeople;
	}

	// ************************
	public String getTestDate()
	{
		return testDate;
	}

	// ************************
	public String getNote()
	{
		return note;
	}

}