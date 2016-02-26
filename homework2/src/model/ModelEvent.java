package model;

import model.AgentCreator.AgentStatus;

public class ModelEvent {
	public enum EventKind{
		BalanceUpdate, AmountTransferredUpdate, AgentStatusUpdate
	}
	private EventKind kind;
	private double balance;
	private AgentStatus agt;
	public ModelEvent(EventKind kind, double balance, AgentStatus status){
		this.kind = kind;
		this.balance = balance;
		this.agt = status;
	}
	public EventKind getKind(){
		return kind;
	}
	
	public double getBalance(){
		return balance;
	}
	
	public AgentStatus getAgentStatus(){
		return this.agt;
	}

}
