package dir;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.tree.TreePath;

public class FileTreeMouseAdapter extends MouseAdapter
{
	private FileTree tree;
	
	public FileTreeMouseAdapter(FileTree tree)
	{
		super();
		this.tree = tree;
	}
	
	public void mouseMoved (MouseEvent e)
	{
		TreePath path = tree.getPathForLocation(e.getX(), e.getY());
		
		if (path != null)
		{
			if (tree.mouseInPath != null)
			{
				Rectangle oldRect = tree.getPathBounds(tree.mouseInPath);
				tree.mouseInPath = path;
				tree.repaint(tree.getPathBounds(path).union(oldRect));
			}
			else
			{
				tree.mouseInPath = path;
				Rectangle bounds = tree.getPathBounds(tree.mouseInPath);
				tree.repaint(bounds);
			}
		}
		else if (tree.mouseInPath != null)
		{
			Rectangle oldRect = tree.getPathBounds(tree.mouseInPath);
			tree.mouseInPath = null;
			tree.repaint(oldRect);
		}
	}
	
//	public void mouseClicked(MouseEvent e) 
//	{
//		JTree tree = (JTree) e.getSource();
//		int rowLocation = tree.getRowForLocation(e.getX(), e.getY());
//		TreePath treepath = tree.getPathForRow(rowLocation);
//		TreeNode treenode = (TreeNode) treepath.getLastPathComponent();
//		treenode.
//		String name = treenode.toString();
//	}
}
