package communation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;

public class CommuParamPanelChoiceAction implements ActionListener
{
	private CommuParamConfigDialog commuParamPanel;
	private ICommuParamAssiciation permitCommuParamDealer;
	public CommuParamPanelChoiceAction(CommuParamConfigDialog commuParamPanel)
	{
		this.commuParamPanel = commuParamPanel;
		this.permitCommuParamDealer = permitCommuParamDealer;
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
		else if(cb.equals(commuParamPanel.acquisitionSettingCB))
		{
			if(selectedVal.equals(Protocol.MANU_CONFIG))
			{
				commuParamPanel.pulseWidthCB.setEnabled(true);
				commuParamPanel.rangeCB.setEnabled(true);
				commuParamPanel.resolutionCB.setEnabled(true);
			}
			else if(selectedVal.equals(Protocol.AUTO_CONFIG))
			{
				commuParamPanel.pulseWidthCB.setEnabled(false);
				commuParamPanel.rangeCB.setEnabled(false);
				commuParamPanel.resolutionCB.setEnabled(false);
			}			
		}

		if(permitCommuParamDealer != null)
		{
			Map<String, List<String>> permittedVal = 
					permitCommuParamDealer.getPermitItemWhenSelect(selectedItems);
			commuParamPanel.setDevicePermittedItems(permittedVal);
		}
	}
}
