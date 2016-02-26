package controller;

import view.AgentView;
import view.FinalAgentView;
import model.AgentCreator.AgentStatus;
import model.DepositAgent;
import model.WithdrawAgent;



public class FinalAgentController extends AbstractController{
	
	private Boolean alreadyStopped = false;
	
	/**
	 * handles operation received from view and updates model
	 * @param opt option that is selected from view
	 * @param depositAgent 
	 */
	public void operation(String opt, Boolean depositAgent){
		if(opt == "Stop agent"){
			if(depositAgent){
				if(!alreadyStopped){
					((DepositAgent)getModel()).onPause();
					alreadyStopped = true;
					((FinalAgentView)getView()).enableDismiss();
				}else{
					((DepositAgent)getModel()).onResume();
					alreadyStopped = false;
					((FinalAgentView)getView()).disableDismiss();
				}
			}else{
				if(!alreadyStopped){
					((WithdrawAgent)getModel()).onPause();
					alreadyStopped = true;
					((FinalAgentView)getView()).enableDismiss();
				}else{
					((WithdrawAgent)getModel()).onResume();
					alreadyStopped = false;
					((FinalAgentView)getView()).disableDismiss();
				}
				
			}
		}else{
			((FinalAgentView)getView()).setVisible(false);
			((FinalAgentView)getView()).dispose();
			
		}
	}

}
