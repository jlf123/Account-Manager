package model;

import static org.junit.Assert.*;

import org.junit.Test;

import model.AgentCreator.AgentStatus;

public class DepositAgentTest {

	@Test
	public void testGetStatus() {
		DepositAgent test = new DepositAgent(new AccountManagerModel("test", 123, 100),
				0, 100, 1);
		assertTrue(test.getStatus() == AgentStatus.RUNNING );
		test.finish();
	}

	@Test
	public void testGetTransferred() {
		DepositAgent test = new DepositAgent(new AccountManagerModel("test", 123, 100),
				0, 100, 1);

		test.finish();
		assertTrue(test.getTransferred() == 0);
	}

	@Test
	public void testGetNumberOfTransactions() {
		DepositAgent test = new DepositAgent(new AccountManagerModel("test", 123, 100),
				0, 100, 1);
		
		
		assertTrue(test.getNumberOfTransactions() == 0);
		test.finish();
	}

	@Test
	public void testGetOpPerSec() {
		DepositAgent test = new DepositAgent(new AccountManagerModel("test", 123, 100),
				0, 100, 1);
		test.finish();
		assertTrue(Integer.parseInt(test.getOpPerSec()) == 1);
	}

}
