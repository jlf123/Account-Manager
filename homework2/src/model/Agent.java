package model;

import model.AgentCreator.AgentStatus;

public interface Agent extends Model{
	public AgentStatus getStatus();
	public double getTransferred();
	public void setName(String name);
	public String getName();
	public AccountManagerModel getAccount();
	public void setStatus(AgentStatus agSt);
	public void finish();
	

}
