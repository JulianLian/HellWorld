package rule;

import javax.swing.JFrame;

public class TTT extends JFrame
{
	public TTT()
	{
		this.add(new JCustomScrollPane(20,20));
		this.setSize(800,600);
	}
	
	public static void main(String[] args)
	{
		TTT t = new TTT();
		t.setVisible(true);
		t.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
