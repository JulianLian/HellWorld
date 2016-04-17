package communation.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;

import communation.ICommuParamAssiciation;
import communation.Protocol;

public class CommuParamPanelChoiceAction implements ActionListener
{
	private CommuParamConfigDialog commuParamPanel;
	private ICommuParamAssiciation permitCommuParamDealer;
	public CommuParamPanelChoiceAction(CommuParamConfigDialog commuParamPanel)
	{
		this.commuParamPanel = commuParamPanel;
		permitCommuParamDealer = null;
	}
	public void setPermitCommuParamDealer( ICommuParamAssiciation permitCommuParamDealer)
	{
		this.permitCommuParamDealer = permitCommuParamDealer;
	}
	@Override
	public void actionPerformed (ActionEvent e)
	{
		JComboBox cb = (JComboBox)e.getSource();
		String selectedVal = (String)cb.getSelectedItem();
		Map<String, String> selectedItems = new HashMap<String, String>();
		if(cb.equals(commuParamPanel.moduleCB))
		{
			selectedItems.put(Protocol.MODULE, selectedVal);			
		}
		else if(cb.equals(commuParamPanel.functionCB))
		{
			selectedItems.put(Protocol.MODULE, 
					(String)commuParamPanel.moduleCB.getSelectedItem());	
			selectedItems.put(Protocol.FUNCTION, selectedVal);	
		}
		else if(cb.equals(commuParamPanel.otuInPortCB))
		{
			selectedItems.put(Protocol.MODULE, 
					(String)commuParamPanel.moduleCB.getSelectedItem());	
			selectedItems.put(Protocol.FUNCTION, 
					(String)commuParamPanel.functionCB.getSelectedItem());	
			selectedItems.put(Protocol.OTU_IN, selectedVal);	
		}
		else if(cb.equals(commuParamPanel.otuOutPortCB))
		{
			selectedItems.put(Protocol.MODULE, 
					(String)commuParamPanel.moduleCB.getSelectedItem());	
			selectedItems.put(Protocol.FUNCTION, 
					(String)commuParamPanel.functionCB.getSelectedItem());	
			selectedItems.put(Protocol.OTU_OUT, selectedVal);	
		}
                else if(cb.equals(commuParamPanel.waveLengthCB))
		{
			selectedItems.put(Protocol.MODULE, 
					(String)commuParamPanel.moduleCB.getSelectedItem());	
			selectedItems.put(Protocol.FUNCTION, 
					(String)commuParamPanel.functionCB.getSelectedItem());	
			selectedItems.put(Protocol.WAVE_LENGTH, selectedVal);				
		}
		else if(cb.equals(commuParamPanel.pulseWidthCB))
		{
			selectedItems.put(Protocol.MODULE, 
					(String)commuParamPanel.moduleCB.getSelectedItem());	
			selectedItems.put(Protocol.FUNCTION, 
					(String)commuParamPanel.functionCB.getSelectedItem());	
			selectedItems.put(Protocol.PULSE_WIDTH, selectedVal);				
		}
		else if(cb.equals(commuParamPanel.rangeCB))
		{
			selectedItems.put(Protocol.MODULE, 
					(String)commuParamPanel.moduleCB.getSelectedItem());	
			selectedItems.put(Protocol.FUNCTION, 
					(String)commuParamPanel.functionCB.getSelectedItem());	
			selectedItems.put(Protocol.PULSE_WIDTH, 
					(String)commuParamPanel.pulseWidthCB.getSelectedItem());	
			selectedItems.put(Protocol.RANGE, selectedVal);				
		}
		else if(cb.equals(commuParamPanel.resolutionCB))
		{
			selectedItems.put(Protocol.MODULE, 
					(String)commuParamPanel.moduleCB.getSelectedItem());	
			selectedItems.put(Protocol.FUNCTION, 
					(String)commuParamPanel.functionCB.getSelectedItem());	
			selectedItems.put(Protocol.PULSE_WIDTH, 
					(String)commuParamPanel.pulseWidthCB.getSelectedItem());	
			selectedItems.put(Protocol.RANGE, 
					(String)commuParamPanel.rangeCB.getSelectedItem());	
			selectedItems.put(Protocol.RESOLUTION, selectedVal);				
		}
		else if(cb.equals(commuParamPanel.acquisitionSettingCB))
		{
			selectedItems.put(Protocol.MODULE,
					(String)commuParamPanel.moduleCB.getSelectedItem());
			selectedItems.put(Protocol.FUNCTION,
					(String)commuParamPanel.functionCB.getSelectedItem());
			if(selectedVal.equals(Protocol.MANU_CONFIG))
			{
				selectManuAction();
				selectedItems.put(Protocol.MANU_CONFIG, selectedVal);
			}
			else if(selectedVal.equals(Protocol.AUTO_CONFIG))
			{
				selectAutoAction();
				selectedItems.put(Protocol.AUTO_CONFIG, selectedVal);
			}			
		}

		if(permitCommuParamDealer != null)
		{
			Map<String, List<String>> permittedVal = 
					permitCommuParamDealer.getPermitItemWhenSelect(selectedItems);
			commuParamPanel.setDevicePermittedItems(permittedVal);
		}
	}
	
	private void selectManuAction()
	{
		setUsingManuAcquisitionSetting(true);
	}
	
	private void setUsingManuAcquisitionSetting(boolean isUsingManu)
	{
		commuParamPanel.waveLengthCB.setEnabled(isUsingManu);
		commuParamPanel.pulseWidthCB.setEnabled(isUsingManu);
		commuParamPanel.rangeCB.setEnabled(isUsingManu);
		commuParamPanel.resolutionCB.setEnabled(isUsingManu);
	}
	private void selectAutoAction()
	{
		setUsingManuAcquisitionSetting(false);
	}
}
