package dir;

import java.io.File;

import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class FileTreeModel extends DefaultTreeModel
{
	public FileTreeModel(TreeNode root)
	{
		super(root);
		FileSystemView fileSystemView = FileSystemView.getFileSystemView();
		for(File oneFile : fileSystemView.getRoots())
		{
			FileNode childFileNode = new FileNode(fileSystemView.getSystemDisplayName(oneFile),
					fileSystemView.getSystemIcon(oneFile), oneFile, false);
			DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(childFileNode);
			((DefaultMutableTreeNode) root).add(childTreeNode);
		}
	}
	
	@Override
	public boolean isLeaf (Object node)
	{
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
		FileNode fileNode = (FileNode) treeNode.getUserObject();
		if (fileNode.isDummyRoot)
			return false;
		return fileNode.file.isFile();
	}
}
