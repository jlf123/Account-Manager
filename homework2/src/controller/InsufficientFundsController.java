package controller;
import model.AccountManagerModel;
import model.AccountManagerModel.Currency;
import model.OverdrawException;
import view.AccountManagerView;
import view.AccountViewEditor;
import view.InsufficientFundsView;
import view.JFrameView;
import model.AccountManagerModel;

public class InsufficientFundsController extends AbstractController{
	
	public void operation(String opt){
		if(opt == "Dismiss"){
			((InsufficientFundsView)getView()).setVisible(false);
		}
	}
	

}
