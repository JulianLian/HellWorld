package dir;

import java.io.File;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

public class FileTree extends JTree
{
	protected TreePath mouseInPath;
	protected FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	
	public FileTree()
	{
		setRootVisible(false);
		addTreeWillExpandListener(new TreeWillExpandListener() {
			@Override
			public void treeWillExpand (TreeExpansionEvent event) throws ExpandVetoException
			{
				DefaultMutableTreeNode lastTreeNode = (DefaultMutableTreeNode) event.getPath()
						.getLastPathComponent();
				FileNode fileNode = (FileNode) lastTreeNode.getUserObject();
				if (!fileNode.isInit)
				{
					File[] files;
					if (fileNode.isDummyRoot)
					{
						files = fileSystemView.getRoots();
					}
					else
					{
						files = fileSystemView.getFiles(
								((FileNode) lastTreeNode.getUserObject()).file, false);
					}
					for (File oneSubFile : files)
					{
						boolean needAddToDirTree = false;
						if(oneSubFile.isDirectory() || oneSubFile.getName().endsWith(".wave"))
						{
							needAddToDirTree = true;
						}
						if(needAddToDirTree)
						{
							FileNode childFileNode = new FileNode(
									fileSystemView.getSystemDisplayName(oneSubFile),
									fileSystemView.getSystemIcon(oneSubFile), oneSubFile,
									false);
							DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(
									childFileNode);
							lastTreeNode.add(childTreeNode);
						}						
					}					
					DefaultTreeModel treeModel1 = (DefaultTreeModel) getModel();
					treeModel1.nodeStructureChanged(lastTreeNode);
				}
				fileNode.isInit = true;
			}
			
			@Override
			public void treeWillCollapse (TreeExpansionEvent event) throws ExpandVetoException
			{
			}
		});
	}
	
	public void addTreeMouseClickAction (FileTreeMouseAdapter fileTreeMouseAdapter)
	{
		if (fileTreeMouseAdapter != null)
		{
			addMouseMotionListener(fileTreeMouseAdapter);
//			this.addMouseListener(fileTreeMouseAdapter);
		}
	}
	
	public TreePath getMouseInPath ()
	{
		return mouseInPath;
	}
}
