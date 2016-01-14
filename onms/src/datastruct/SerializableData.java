package datastruct;

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

public abstract class SerializableData implements Serializable
{	
	private static final long serialVersionUID = 1L;

	public void writeToFile (FileOutputStream outStream) throws IOException
	{		
		ObjectOutputStream ooStream = new ObjectOutputStream(outStream);
		ooStream.writeObject(this);
		ooStream.flush();		
	}
	
	public abstract List<Double> getWavePoints ();
	
	public static List<Double> readFromFile (FileInputStream inStream) throws IOException , ClassNotFoundException
	{
		ObjectInputStream ooStream = new ObjectInputStream(inStream);
		
		SerializableData s = (SerializableData) (ooStream.readObject());
		
		List<Double> one = s.getWavePoints();		
				
		InventoryData.setCanTransformedDataFromFile(one);
		InventoryData.setDataFromFileNeedPrint(new ArrayList<Double>(one));
		return one;
		// InventoryData.setDataFromFileNeedPrint(two);
		//详情才需要下面这句
//		SaveInfo.restoreSaveInfo(s);
	}
}
