package model;
import java.text.DecimalFormat;
import java.util.Comparator;

import javax.swing.SwingUtilities;

import model.AgentCreator.AgentStatus;

public class AccountManagerModel  extends AbstractModel {
	

	private double balance;
	private String name;
	private int accountNumber;
	static public enum Currency { DOLLARS, EUROS, YEN };
	public Currency curChosen;
	static final double EUROCONVERSIONRATE = 1.26;
	static final double YENCONVERSIONRATE = .01;
	static final double DOLLARTOEUROCONVERSION = .79;
	static final double DOLLARTOYENCONVERSION = 94.1;
	static final DecimalFormat df = new DecimalFormat("#.##");
	
	public AccountManagerModel(String newName, int newAccountNumber, double newBalance){
		name = newName;
		accountNumber = newAccountNumber;
		balance = newBalance;
		curChosen = Currency.DOLLARS;
	}
	

	/**
	 * Comparator method used in sorting accounts based on their account number
	 */
	public static Comparator<AccountManagerModel> AcntNumberComparator =
			new Comparator<AccountManagerModel>(){
		public int compare(AccountManagerModel a1, AccountManagerModel a2){
			int acntNum1 = a1.getAcntNumber();
			int acntNum2 = a2.getAcntNumber();
			return acntNum1 - acntNum2;
		}
		
	};
	
	/**
	 * Deposits money into account
	 * @param enteredAmount : amount we want to deposit
	 * @param type : currency type we want to deposits
	 */
	public void add(double enteredAmount, Currency type){
		balance += convertToDollars(enteredAmount, type);
		final ModelEvent me = new ModelEvent(ModelEvent.EventKind.BalanceUpdate, balance, AgentStatus.NA);
		
		notifyChanged(me);
	}
	
	/**
	 * Deposit that is used by threads which has a notify method
	 * that notifies all other deposit agents
	 * @param enteredAmount
	 * @param type
	 */
	public synchronized void autoDeposit(double enteredAmount,Currency type){
		this.add(enteredAmount, type);
		notifyAll();
		
	}
	
	/**
	 * Comparative methods for comparing the balances of two different
	 * account, used for testing
	 * @param accountObject : other account that we are comparing this with
	 * @return : returns a Boolean on whether the account's balances are equal
	 */
	public Boolean equals(AccountManagerModel accountObject){
		if(accountObject instanceof Model){
			return (accountObject.getBalance(Currency.DOLLARS) == this.getBalance(Currency.DOLLARS));
		}
		return false;
	}
	
	/**
	 * Withdraws from the account
	 * @param enteredAmount : amount we are attempting to withdraw
	 * @param type : currency type we are manipulating account with
	 * @throws OverdrawException : if enteredAmount is more than balance, overdrawn exception will be thrown
	 */
	public void subtract(double enteredAmount, Currency type) throws OverdrawException{
		
		if(type != Currency.DOLLARS){
			double debitAmt = convertToDollars(enteredAmount,type);
			enteredAmount = debitAmt;
		}
		
		double testBalance = balance - enteredAmount;
		if(testBalance < 0.0) throw new OverdrawException(testBalance);
		balance = testBalance;
		final ModelEvent me = new ModelEvent(ModelEvent.EventKind.BalanceUpdate, balance, AgentStatus.NA);
		
		notifyChanged(me);
		
	}
	
	/**
	 * 
	 * @param type : currency we want to display
	 * @return : balance of account
	 */
	public double getBalance(Currency type){
		return convertToType(balance,type);
		
	}
	
	/**
	 * get balance used for jframe
	 * @param type
	 * @return
	 */
	public  String getBalanceString(Currency type){
		return df.format(convertToType(balance,type));
	}
	
	/**
	 * converts amount into a decimal format
	 * @param amount
	 * @return 
	 */
	public static String toDecimalFormat(double amount){
		return df.format(amount);
	}
	
	
	public String getName(){
		return this.name;
	}
	
	/**
	 * method used to display account information into the jcombobox
	 * @param type
	 * @return
	 */
	public String toString(Currency type){
		String returnString = getName() + " " + getAcntNumber()
		+ " $" + getBalanceString(type);
		return returnString;
	}
	
	public int getAcntNumber(){
		return this.accountNumber;
	}
	
	public double convertToDollars(double enteredAmount, Currency type){
		switch(type){
		case DOLLARS:
			return enteredAmount;
		case EUROS:
			return enteredAmount * EUROCONVERSIONRATE;
		case YEN:
			return enteredAmount * YENCONVERSIONRATE;
			default:
				return 0;
			
		}
		
	}
	
	public static double convertToType(double enteredAmount,Currency type){
		switch(type){
		case DOLLARS:
			return enteredAmount;
		case EUROS:
			return enteredAmount * DOLLARTOEUROCONVERSION;
		case YEN:
			return enteredAmount * DOLLARTOYENCONVERSION;
			default:
				return 0;
			
		}
		
	}
	
	/**
	 * Withdraw method for threads, implements notify all method
	 * @param amount
	 * @param ag
	 */
	public synchronized void autoWithdraw(double amount, Agent ag) {
		try {
			System.out.println("Trying to withdraw " + amount + " from balance " + balance);
			
			//if(balance - amount < 0.0) {
				//System.out.println("Sending blocked");
				//ag.setStatus(AgentStatus.Blocked);		
			//}
			
			while(this.balance - amount < 0.0) {
				ag.setStatus(AgentStatus.BLOCKED);	
				wait();
			}
			if(ag.getStatus() == AgentStatus.PAUSED) return;
			ag.setStatus(AgentStatus.RUNNING);
					
			this.balance -= amount;
			final ModelEvent me = new ModelEvent(ModelEvent.EventKind.BalanceUpdate, this.balance, AgentStatus.RUNNING);
			SwingUtilities.invokeLater(
				new Runnable() {
				    public void run() {
				    	notifyChanged(me);
				    }
				});
		}
		catch(InterruptedException ex){
			System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
		}
		/*
		catch(InvocationTargetException ex){
			System.out.println("Thread " + Thread.currentThread().getName() + " " + ex.getMessage());
			ex.printStackTrace();
		}
		*/
	}
	
	

	
	
}
