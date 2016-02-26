package model;

import javax.swing.SwingUtilities;

import model.AccountManagerModel.Currency;
import model.AgentCreator.AgentStatus;

public class DepositAgent extends AbstractModel implements Runnable, Agent {
	
	private boolean paused;
	private int agentId;
	private int operationsPerSecond;
	public volatile boolean active;
	private AccountManagerModel account;
	private double amount;
	private double transferred;
	private String name = new String("Default");
	private AgentStatus status;
	private Object pauselock;
	private Currency type = Currency.DOLLARS;
	private int NumberOfTransactions;
	
	

	public DepositAgent(AccountManagerModel account2, double amount2, int agentID2, int opPerSec) {
		this.account = account2;
		this.amount = amount2;
		this.agentId = agentID2;
		this.operationsPerSecond = opPerSec;
		this.status = AgentStatus.RUNNING;
		this.transferred = 0;
		this.active = true;
		this.paused = false;
		this.pauselock = new Object();
		this.NumberOfTransactions = 0;
	}

	@Override
	public AgentStatus getStatus() {
		return this.status;
	}

	@Override
	public double getTransferred() {
		return this.transferred;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public AccountManagerModel getAccount() {
		return this.account;
	}


	@Override
	public void finish() {
		active = false;
		
	}
	
	public int getNumberOfTransactions(){
		return this.NumberOfTransactions;
	}
	
	@Override
	public void run() {
		while(active){
			synchronized(pauselock){
				while(paused){
					try{
						pauselock.wait();
					} catch(InterruptedException e){
						System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
						
					}
				}
			}
			
			account.autoDeposit(amount, type);
			this.transferred += amount;
			this.NumberOfTransactions += 1;
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
	
	/**
	 * pauses thread
	 */
	public void onPause() {
        synchronized (pauselock) {
            paused = true;
            setStatus(AgentStatus.PAUSED);
        }
    }
	
	/**
	 * resumes thread
	 */
    public void onResume() {
        synchronized (pauselock) {
            paused = false;
            setStatus(AgentStatus.RUNNING);
            pauselock.notify();
        }
    }
    /**
     * sets agent status and creates a model event to alert controller of changes
     * and updates changes on the view
     */
    public void setStatus(AgentStatus agSt) {
    	status = agSt;
    	final ModelEvent me = new ModelEvent(ModelEvent.EventKind.AgentStatusUpdate, 0.0, agSt);
    	SwingUtilities.invokeLater(
				new Runnable() {
				    public void run() {
				    	notifyChanged(me);
				    }
				});
    }

	public String getAmount() {
		return String.valueOf(this.amount);
	}

	public String getOpPerSec() {
		return String.valueOf(this.operationsPerSecond);
	}

}
