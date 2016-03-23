package dir;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

public class FileTreeTest
{
	public static void main (String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run ()
			{
				JFrame frame = new JFrame();
				FileTree fileTree = new FileTree();
				fileTree.addTreeMouseClickAction(new FileTreeMouseAdapter(fileTree));
				FileTreeModel model = new FileTreeModel(
						new DefaultMutableTreeNode(new FileNode("root", null, null, true)));
				fileTree.setModel(model);
				fileTree.setCellRenderer(new FileTreeRenderer());
				
				frame.getContentPane().add(new JScrollPane(fileTree), BorderLayout.CENTER);
				frame.setSize(300, 700);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}