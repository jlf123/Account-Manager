package controller;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import view.AccountManagerView;
import view.AccountViewEditor;
import view.AgentView;
import view.FinalAgentView;
import model.AgentCreator;
import model.InvalidID;

public class AgentController extends AbstractController{

	public void operation(String actionCommand, Boolean depositAgent) {
		if(actionCommand == "Dismiss"){
			((AgentView)getView()).setVisible(false);
			((AgentView)getView()).dispose();
		}
		else if(actionCommand == "Start agent"){
			if(depositAgent){
				int requestedAgentId = ((AgentView)getView()).getAgentId();
				if(AgentCreator.agentIdAvailable(requestedAgentId)){
					SwingUtilities.invokeLater( new Runnable() {
						public void run() {
							FinalAgentView frame = new FinalAgentView(
									AgentCreator.createDepAgent(AccountManagerView.accounts.get(AccountManagerView.jComboBox1.getSelectedIndex()),
											((AgentView)getView()).getAmount(), requestedAgentId,
											((AgentView)getView()).getOpsPerSec()),
									new FinalAgentController(), true);
							frame.setVisible(true);
						}
					});
				}
			}
			else{
				int requestedAgentId = ((AgentView)getView()).getAgentId();
				SwingUtilities.invokeLater( new Runnable() {
					public void run() {
						FinalAgentView frame;
						try {
							frame = new FinalAgentView(
										AgentCreator.createWithdrawAgent(AccountManagerView.accounts.get(AccountManagerView.jComboBox1.getSelectedIndex()),
										((AgentView)getView()).getAmount(), requestedAgentId,
										((AgentView)getView()).getOpsPerSec()),
										new FinalAgentController(), false);
							frame.setVisible(true);
						} catch (InvalidID e) {
							JOptionPane.showMessageDialog(null, e);
						}
						
					}
				});
				
			}
			
		}
		
	}

}
