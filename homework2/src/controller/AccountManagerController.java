package controller;
import java.awt.event.WindowEvent;
import java.util.InputMismatchException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.AccountManagerModel;
import model.AccountManagerModel.Currency;
import model.OverdrawException;
import view.AccountManagerView;
import view.AccountViewEditor;
import view.InsufficientFundsView;
import view.JFrameView;
import model.AccountManagerModel;


public class AccountManagerController extends AbstractController{
	
	/**
	 * handles option selected in the view and performs option on model
	 * @param opt
	 * @param type
	 */
	public void operation(String opt,Currency type){
		if(opt == "Deposit"){
			double amount = 0;
			amount = ((AccountViewEditor)getView()).getAmount();
			((AccountManagerModel)getModel()).add(amount, type);
			
		}
		else if(opt == "Withdraw"){
			double amount = ((AccountViewEditor)getView()).getAmount();
			try {
				((AccountManagerModel)getModel()).subtract(amount, type);
				//((AccountManagerView)getView()).jComboBox1.
			} catch (OverdrawException e) {
				SwingUtilities.invokeLater( new Runnable() {
					public void run() {
						InsufficientFundsView frame = new InsufficientFundsView(
								AccountManagerView.accounts.get(AccountManagerView.jComboBox1.getSelectedIndex()),
								new InsufficientFundsController(), amount, type);
						frame.setVisible(true);
					}
				});
			}
		}
		else if(opt == "Dismiss"){
			((AccountViewEditor)getView()).setVisible(false);
			((AccountViewEditor)getView()).dispose();
		}
	}

}
