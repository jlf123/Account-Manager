package model;

import java.util.ArrayList;
import java.util.List;

public class AgentCreator {
	static public enum AgentStatus{
		RUNNING, BLOCKED, PAUSE, NA, PAUSED
	}
	private static List<Thread> agentThreads = new ArrayList<Thread>(10);
	public static int []usedAgentID = new int[10];
	public static int depAgentCount = 0;
	public static int wthAgentCount = 0;
	
	/**
	 * creates a deposit agent
	 * @param account : account object the agent will deposit into
	 * @param amount : amount the agent will deposit
	 * @param AgentID
	 * @param opPerSec
	 * @return the deposit agent that is created
	 */
	public static Agent createDepAgent(AccountManagerModel account, double amount, int AgentID, int opPerSec){
		DepositAgent depAg = new DepositAgent(account,amount, AgentID, opPerSec);
		Thread depAgt = new Thread(depAg);
		String name = "Deposit Agent " + AgentID;
		depAg.setName(name);
		depAgt.setName(name);
		usedAgentID[depAgentCount + wthAgentCount] = AgentID;
		depAgentCount++;
		agentThreads.add(depAgt);
		depAgt.start();
		return depAg;	
	}

	/**
	 * creates a deposit agent
	 * @param account : account object the agent will withdraw from
	 * @param amount : amount the agent will withdraw
	 * @param AgentID
	 * @param opPerSec
	 * @return the withdraw agent that was created
	 */
	public static Agent createWithdrawAgent(AccountManagerModel account, double amount,
			int AgentID, int opPerSec) throws InvalidID{
		if(!agentIdAvailable(AgentID)){
			throw new InvalidID(AgentID);
		}
		
		WithdrawAgent depAg = new WithdrawAgent(account,amount, AgentID, opPerSec);
		Thread depAgt = new Thread(depAg);
		String name = "Deposit Agent " + AgentID;
		depAg.setName(name);
		depAgt.setName(name);
		usedAgentID[depAgentCount + wthAgentCount] = AgentID;
		wthAgentCount++;
		agentThreads.add(depAgt);
		depAgt.start();
		return depAg;	
	}
	
	/**
	 * function for checking availability of agent id
	 * @param agentID
	 * @return true if agent id is available or false if not
	 */
	public static Boolean agentIdAvailable(int agentID){
		for(int i = 0; i < (depAgentCount + wthAgentCount); i++){
			if(agentID == usedAgentID[i]){
				return false;
			}
		}
		return true;
	}

}
