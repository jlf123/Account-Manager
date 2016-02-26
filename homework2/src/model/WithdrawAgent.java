package model;

import javax.swing.SwingUtilities;

import model.AccountManagerModel.Currency;
import model.AgentCreator.AgentStatus;

public class WithdrawAgent extends AbstractModel implements Runnable, Agent{
	private Object pauseLock;
	private volatile boolean paused;
	public volatile boolean active;
	private AccountManagerModel account;
	private double amount;
	private double transferred;
	private String name = new String("Default");
	private AgentStatus status;
	private volatile boolean wasBlocked;
	private int agentId;
	private int operationsPerSecond;
	private int numberOfTransactions;
	private Currency type = Currency.DOLLARS;


	public WithdrawAgent(AccountManagerModel account, double amount, int agentID, int opPerSec) {
		this.account = account;
		this.amount = amount;
		this.agentId = agentID;
		this.transferred = 0;
		this.status = AgentStatus.RUNNING;
		this.wasBlocked = false;
		this.active = true;
		this.paused = false;
		this.pauseLock = new Object();
		this.operationsPerSecond = opPerSec;
		this.numberOfTransactions = 0;
	}

	@Override
	public AgentStatus getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}

	@Override
	public double getTransferred() {
		// TODO Auto-generated method stub
		return this.transferred;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public AccountManagerModel getAccount() {
		// TODO Auto-generated method stub
		return this.account;
	}
	
	public int getNumberOfTransactions(){
		return this.numberOfTransactions;
	}

	@Override
	/**
	 * sets agent status and sends out a model event to alert controller
	 * of changes
	 */
	public void setStatus(AgentStatus agSt) {
		this.status = agSt;
		final ModelEvent me = new ModelEvent(ModelEvent.EventKind.AgentStatusUpdate, 0.0, agSt);
    	SwingUtilities.invokeLater(
				new Runnable() {
				    public void run() {
				    	notifyChanged(me);
				    }
				});
		
		
	}

	@Override
	public void finish() {
		this.active = false;
		
	}

	@Override
	public void run() {
		while(active){
			synchronized(pauseLock){
				while(paused){
					try{
						pauseLock.wait();
					} catch(InterruptedException e){
						System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
						
					}
				}
			}
			
			account.autoWithdraw(amount, this);
			
			
			this.transferred += amount;
			this.numberOfTransactions += 1;
			final ModelEvent me = new ModelEvent(ModelEvent.EventKind.AmountTransferredUpdate, transferred,
					AgentStatus.NA);
			
			SwingUtilities.invokeLater(
					new Runnable() {
					    public void run() {
					    	notifyChanged(me);
					    }
					});
			try {
				Thread.sleep(1000/this.operationsPerSecond);
			}
			catch(InterruptedException ex){
				System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
			}
			
			
		}
		
	}

	public String getAmount() {
		return String.valueOf(this.amount);
	}

	public String getOpPerSec() {
		// TODO Auto-generated method stub
		return String.valueOf(this.operationsPerSecond);
	}
	
	/**
	 * pauses thread
	 */
	public void onPause() {
        synchronized (pauseLock) {
            paused = true;
            setStatus(AgentStatus.PAUSED);
        }
		
	}
	
	/**
	 * resumes thread
	 */
	public void onResume() {
		synchronized (pauseLock) {
			if(wasBlocked) setStatus(AgentStatus.BLOCKED);
        	else setStatus(AgentStatus.RUNNING);
            paused = false;
            pauseLock.notify();
        }
		
	}

}
