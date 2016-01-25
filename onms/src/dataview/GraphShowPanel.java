package dataview;

/*
 * 作者：杨安印
 *
 * 本类意在完成一个图形显示功能，数据
 * 从Data中获取
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import domain.BusinessConst;
import domain.DistanceCalculator;
import draw.DrawUtils;
import main.Md711MainFrame;
import persistant.FileDataPersister;
import persistant.IDataPersister;
import persistant.InventoryData;
import persistant.PortDataPersister;
import persistant.WindowControlEnv;

public class GraphShowPanel extends JPanel implements MouseMotionListener, MouseListener
{
	private Md711MainFrame md;
	// 跟踪光标位置的直线横坐标
	private static double firstPositionHorizontalVal; // ******获取测量用的第一条竖线的横坐标
	private static double secondPositionHorizontalVal; // ********获取测量用的第二条竖线的横坐标
	private static double firstEventHorizontalVal = Double.MIN_VALUE;
	private double selectedStartX;
	private double selectedStartY;
	private double selectedEndX;
	private double selectedEndY;
	private static boolean isDragging = false; // dataview.MoveAndAmplifyControllerPanel.amplyOrShrink(IDataPersister,
	// double)
	private static int clickTimes = 0;

	// ********************************************
	public GraphShowPanel(Md711MainFrame md)
	{
		super();
		resetAmplyParam();
		setNotBeginMeasureState();
		this.md = md;
		addMouseMotionListener(this);
		addMouseListener(this);
		this.setBackground(Color.LIGHT_GRAY);
	}

	// ***********************************************鼠标事件，我们只处理移动和点击事件
	@Override
	public void mouseMoved (MouseEvent e)
	{
		if (waitSelectFirstPosition())
		{
			firstPositionHorizontalVal = e.getPoint().getX();// e.getX();
		}
		else if (waitSelectSecondPosition())
		{
			secondPositionHorizontalVal = e.getPoint().getX();// e.getX();
		}
		showCursorPosition(e.getPoint());		
		repaint();
	}

	private void showCursorPosition (Point point)
	{
		Double x = calCussorCorrectPosition(point.getX());
		if(x != null)
		{
			showCursorPosition(x, point);
		}
	}
	
	private Double calCussorCorrectPosition (double xPosition)
	{
		Double measureLen = getMeasureDistance();
		if(measureLen == null)
		{
			return null;
		}
		if (md.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.PORTSELECT)
		{
			return DistanceCalculator.countCursonPositionForPort(xPosition, measureLen,
					this.getSize().getWidth() / 2);
		}
		else if (md.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.FILESELECT)
		{
			return DistanceCalculator.countCursonPositionForFile(xPosition, measureLen,
					this.getSize().getWidth() / 2);
		}
		return null;
	}

	private void showCursorPosition(double correctPosition, Point point)
	{
		JPopupMenu popup = new JPopupMenu();
		popup.setBorder(null);
		popup.setOpaque(false);		
		JPanel infoPanel = new JPanel();
		infoPanel.setOpaque(false);
		JLabel label = new JLabel(correctPosition + " km");
		label.setBorder(null);
		label.setForeground(Color.red);
		label.setBackground(null);
		label.setOpaque(false);
		infoPanel.add(label);		
		popup.add(infoPanel);		
		popup.show(md, (int) point.getX()+10, (int) point.getY()+50);
	}
	
	@Override
	public void mouseClicked (MouseEvent e)
	{

	}

	@Override
	public void mouseDragged (MouseEvent e)
	{
		setNotBeginMeasureState();
		if (!isDragging)
		{
			selectedStartX = e.getPoint().getX();// e.getX();
			selectedStartY = e.getPoint().getY();// e.getY();
		}
		isDragging = true;
		selectedEndX = e.getPoint().getX();// e.getX();
		selectedEndY = e.getPoint().getY();// e.getY();
		repaint();
	}

	@Override
	public void mouseReleased (MouseEvent e)
	{
		isDragging = false;
		selectedEndX = e.getPoint().getX();// e.getX();
		selectedEndY = e.getPoint().getY();// e.getY();
		if (Math.abs(firstPositionHorizontalVal - selectedEndX) >= 0.001 && clickTimes != 2
				&& needAmplyfySelectedArea())
		{
			amplifySelectArea(FileDataPersister.getInstance(md.getEventPanel().getkeyPointPanel()));
			amplifySelectArea(PortDataPersister.getInstance(md.getEventPanel().getkeyPointPanel()));
			resetAmplyParam();
		}
		repaint();
	}

	@Override
	public void mouseExited (MouseEvent e)
	{
	}

	@Override
	public void mousePressed (MouseEvent e)
	{
		selectedStartX = e.getPoint().getX();// e.getX();
		selectedStartY = e.getPoint().getY();// e.getY();
		if (waitSelectFirstPosition())
		{
			firstPositionHorizontalVal = e.getPoint().getX();// e.getX();
			clickTimes++;
		}
		else if (waitSelectSecondPosition())
		{
			secondPositionHorizontalVal = e.getPoint().getX();// e.getX();
			clickTimes++;
			calFaultDistanceAndShow();
		}
		else if (alreadyFormedTwoMeasurePoint())
		{
			setNotBeginMeasureState();
			md.getGraphControllerpanel().fillDistanceInfo("");
		}
		repaint();
	}

	@Override
	public void mouseEntered (MouseEvent e)
	{
	}

	// ***********************************************绘图
	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// 注意，坐标移动到了中心位置
		formDrawBackgroundArea(g2);

		Dimension dimension = this.getSize();
		double xBase = -dimension.getWidth() / 2;

		// amplifySelectArea(FileDataPersister.getInstance(), g2);

		// ***********************************
		if (InventoryData.hasDataToShow())
		{
			// ****************************画端口数据
			// 如果端口数据还没有做坐标调整，则这次需要调整
			if (WindowControlEnv.getRepaintForPortInfoCome())
			{
				DrawUtils.drawDataAfterAdjustAxis(g2, this.getSize(),
						PortDataPersister.getInstance(md.getEventPanel().getkeyPointPanel()));
			}
			else
			{
				DrawUtils.drawPersistData(g2,
						PortDataPersister.getInstance(md.getEventPanel().getkeyPointPanel()));
			}
			// ********************************************画文件数据
			// 文件数据坐标尚未调整
			if (WindowControlEnv.getRepaintForFileInfoCome())
			{
				DrawUtils.drawDataAfterAdjustAxis(g2, this.getSize(),
						FileDataPersister.getInstance(md.getEventPanel().getkeyPointPanel()));
			}
			else
			{
				DrawUtils.drawPersistData(g2,
						FileDataPersister.getInstance(md.getEventPanel().getkeyPointPanel()));
			}
			// resetAmplyParam();
			// ******************************************************************如果要跟踪鼠标位置
			if (firstPositionHorizontalVal != Double.MIN_VALUE)
			{
				// 绘制要测量的起始点竖线
				g2.setColor(Color.yellow);
				g2.draw(new Line2D.Double(firstPositionHorizontalVal + xBase,
						-dimension.getHeight() / 2, firstPositionHorizontalVal + xBase,
						dimension.getHeight()  / 2));
//				g2.draw(new Line2D.Double(firstPositionHorizontalVal + xBase,
//						-dimension.getWidth() / 2 - 10, firstPositionHorizontalVal + xBase,
//						dimension.getWidth() / 2));
			}
			if (secondPositionHorizontalVal != Double.MIN_VALUE)
			{
				// 绘制要测量的起始点竖线
				g2.setColor(Color.yellow);
				g2.draw(new Line2D.Double(secondPositionHorizontalVal + xBase,
						-dimension.getHeight() / 2, secondPositionHorizontalVal + xBase,
						dimension.getHeight() / 2));
//				g2.draw(new Line2D.Double(secondPositionHorizontalVal + xBase,
//						-dimension.getWidth() / 2 - 10, secondPositionHorizontalVal + xBase,
//						dimension.getWidth() / 2));
			}
			if (firstEventHorizontalVal != Double.MIN_VALUE)
			{
				// 绘制要测量的起始点竖线
				g2.setColor(Color.yellow);
				g2.draw(new Line2D.Double(firstEventHorizontalVal, -dimension.getHeight() / 2,
						firstEventHorizontalVal, dimension.getHeight() / 2));
//				g2.draw(new Line2D.Double(firstEventHorizontalVal, -dimension.getWidth() / 2,
//						firstEventHorizontalVal, dimension.getWidth() / 2));
			}

			// ***************************处理测量距离
			if (firstPositionHorizontalVal != Double.MIN_VALUE
					&& secondPositionHorizontalVal != Double.MIN_VALUE)
			{
				calFaultDistanceAndShow();
			}
		}
		if (isDragging)
		{
			g2.translate(-dimension.getWidth() / 2, -dimension.getHeight() / 2);
			g2.setColor(Color.WHITE);
			g2.drawRect((int) selectedStartX, (int) selectedStartY,
					(int) Math.abs(selectedEndX - selectedStartX),
					(int) Math.abs(selectedEndY - selectedStartY));
		}
	}

	private void amplifySelectArea (IDataPersister dataPersister)
	{
		Dimension dimension = this.getSize();
		double centerX = 0;// dimension.getWidth() / 2.0;
		double centerY = 0;// dimension.getHeight() / 2.0;
		double centerAreaX = selectedStartX - dimension.getWidth() / 2 + (selectedEndX - selectedStartX) / 2;
		double centerAreaY = selectedStartY - dimension.getHeight() / 2 + (selectedEndY - selectedStartY) / 2;
		double rightDelta = centerX - centerAreaX;
		double downDelta = centerY - centerAreaY;

		MoveAndAmplifyControllerPanel.moveVertical(dataPersister, downDelta);
		MoveAndAmplifyControllerPanel.moveHorizontal(dataPersister, rightDelta);

		MoveAndAmplifyControllerPanel.amplyOrShrink(dataPersister, ampTimes(rightDelta, downDelta));
	}

	public double ampTimes (double rightDelta , double downDelta)
	{
		Dimension dimension = this.getSize();
		double xTimes = dimension.getWidth() / Math.abs(selectedEndX - selectedStartX);
		double yTimes = dimension.getHeight() / Math.abs(selectedEndY - selectedStartY);
		return Math.min(xTimes, yTimes);
	}

	private void amplifySelectArea (IDataPersister dataPersister , Graphics2D g2)
	{
		if (needAmplyfySelectedArea())
		{
			Dimension dimension = this.getSize();
			// MoveAndAmplifyControllerPanel panel =
			// md.getGraphControllerpanel().getMoveAndAmplyPanel();
			double centerX = 0;// dimension.getWidth() / 2;
			double centerY = 0;// dimension.getHeight() / 2;
			double centerAreaX = selectedStartX + (selectedEndX - selectedStartX) / 2;
			double centerAreaY = selectedStartY + (selectedEndY - selectedStartY) / 2;
			double rightDelta = centerX - centerAreaX;
			double downDelta = centerY - centerAreaY;

			MoveAndAmplifyControllerPanel.moveVertical(dataPersister, rightDelta);
			MoveAndAmplifyControllerPanel.moveHorizontal(dataPersister, downDelta);

			double xAmplyTimes = dimension.getWidth() / Math.abs((selectedEndX - selectedStartX));
			double yAmplyTimes = dimension.getHeight() / Math.abs(selectedEndY - selectedStartY);
			double amplifyTimes = Math.min(xAmplyTimes, yAmplyTimes);
			MoveAndAmplifyControllerPanel.amplyOrShrink(dataPersister, amplifyTimes);

			AffineTransform transform = new AffineTransform();
			transform.translate(rightDelta, downDelta);
			transform.scale(amplifyTimes, amplifyTimes);
			g2.setTransform(transform);

			resetAmplyParam();
		}
		else
		{
			g2.getTransform().setToIdentity();
		}
	}

	private boolean needAmplyfySelectedArea ()
	{
		return !isDragging && (selectedStartX != selectedEndX || selectedStartY != selectedEndY);
	}

	private void formDrawBackgroundArea (Graphics2D d)
	{
		Dimension dimension = this.getSize();
		// 首先覆盖掉原先的图形
		d.setColor(this.getBackground());
		d.setClip(0, 0, (int) dimension.getWidth(), (int) dimension.getHeight());

		d.fill(new Rectangle2D.Double(0, 0, dimension.getWidth(), dimension.getHeight()));

		// 原点平移到中心,画出两条坐标轴
		d.setColor(Color.BLUE);

		d.translate(dimension.getWidth() / 2, dimension.getHeight() / 2);
		d.draw(new Line2D.Double(-dimension.getWidth() / 2, 0, dimension.getWidth() / 2, 0));
	}

	private void calFaultDistanceAndShow ()
	{
		Double measureLen = getMeasureDistance();
		if (measureLen != null)
		{
		if (md.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.PORTSELECT)
		{
				md.getGraphControllerpanel()
						.fillDistanceInfo("" + DistanceCalculator.countPortWrongDistance(
								secondPositionHorizontalVal
										- firstPositionHorizontalVal,
								measureLen));

		}
		if (md.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.FILESELECT)
		{
				// 30个信号数据用时1微秒
				md.getGraphControllerpanel()
						.fillDistanceInfo("" + DistanceCalculator.countFileWrongDistance(
								secondPositionHorizontalVal
										- firstPositionHorizontalVal,
								measureLen));
			}
		}
	}
	
	private Double getMeasureDistance ()
	{
		String measureLen = md.getEventPanel().getkeyPointPanel().getSelectedDevParam()
				.get(communation.Protocol.RANGE);
		if (measureLen != null)
		{
			if (measureLen.endsWith("km"))
			{
				return Double.valueOf(measureLen.substring(0, measureLen.length() - 2).trim());
			}
		}
		return null;
	}

	private boolean waitSelectSecondPosition ()
	{
		return clickTimes == 1;
	}

	private boolean waitSelectFirstPosition ()
	{
		return clickTimes == 0;
	}

	private boolean alreadyFormedTwoMeasurePoint ()
	{
		return clickTimes == 2;
	}

	public static void setNotBeginMeasureState ()
	{
		clickTimes = 0;
		firstPositionHorizontalVal = Double.MIN_VALUE;
		secondPositionHorizontalVal = Double.MIN_VALUE;
	}

	private void resetAmplyParam ()
	{
		if (!isDragging)
		{
			selectedStartX = Double.MIN_VALUE;
			selectedStartY = Double.MIN_VALUE;
			selectedEndX = Double.MIN_VALUE;
			selectedEndY = Double.MIN_VALUE;
		}
	}

	public void showPortData (List<Double> data)
	{
		for (Double tempData : data)
		{
			InventoryData.getCanTransformedDataFromPort().add(tempData);
			InventoryData.getDataFromPortImmutable().add(tempData);
		}
		WindowControlEnv.setRepaintForPortInfoCome(true);
		md.showGraph();
	}

	public void showEventVerticalPosition (Double xPosition)
	{
		setNotBeginMeasureState();
		Double measureLen = getMeasureDistance();
		if (measureLen != null)
		{
			if (md.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.PORTSELECT)
			{
				firstEventHorizontalVal = DistanceCalculator
						.countKeyEventWindowPositionForPort(xPosition / 1000., measureLen);
			}
			else if (md.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.FILESELECT)
			{
				firstEventHorizontalVal = DistanceCalculator
						.countKeyEventWindowPositionForFile(xPosition / 1000., measureLen);
			}
		repaint();
		}
	}
}

