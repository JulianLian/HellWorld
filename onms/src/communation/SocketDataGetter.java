package communation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import env.MDLogger;
import main.Md711MainFrame;

public class SocketDataGetter implements IDataGetter
{
	private Md711MainFrame mainFrame;
	private int port = 123;
	private String svrAddr = "localhost";

	public SocketDataGetter(Md711MainFrame mainFrame)
	{
		super();
		this.mainFrame = mainFrame;
	}

	public void setSvrEnv (String svrAddr , int svrPort)
	{
		this.svrAddr = svrAddr;
		this.port = svrPort;
	}

//	@Override
//	public boolean startFetchData ()
//	{
//		return communication("发给svr的信息");
//	}

	private boolean communication (String sendMess)
	{
		Socket socket = null;
		try
		{
			socket = new Socket(svrAddr, port);

			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			out.writeUTF(sendMess);

			String responseMess = input.readUTF();
			System.err.println("服务器端返回过来的是: " + responseMess);

			out.close();
			input.close();
		}
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
			return false;
		}
		finally
		{
			if (socket != null)
			{
				try
				{
					socket.close();
				}
				catch (IOException e)
				{
					MDLogger.INS.error(e.getMessage());
					socket = null;
				}
			}
		}
		return true;
	}

	@Override
	public List<Double> getWaveData (Map<String, String> permittedVal)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getEventData (Map<String, String> permittedVal)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
