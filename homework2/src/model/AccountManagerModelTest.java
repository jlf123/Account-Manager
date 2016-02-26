package model;

import static org.junit.Assert.*;

import org.junit.Test;

import model.AccountManagerModel.Currency;

public class AccountManagerModelTest {

	
	@Test
	public void testAdd() {
		AccountManagerModel test = new AccountManagerModel("test", 1234, 0.00);
		test.add(100, Currency.DOLLARS);
		test.add(100, Currency.DOLLARS);
		test.add(100, Currency.DOLLARS);
		assertTrue( test.equals(new AccountManagerModel("test",1235,300.00)));
	}


	@Test
	public void testSubtract() {
		AccountManagerModel test = new AccountManagerModel("test", 1234, 300.00);
		try {
			test.subtract(100, Currency.DOLLARS);
			test.subtract(100, Currency.DOLLARS);
			test.subtract(100, Currency.DOLLARS);
		} catch (OverdrawException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(test.equals(new AccountManagerModel("test",1235,0.00)));
	}


}
