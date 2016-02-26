package controller;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.swing.SwingUtilities;

import model.AccountManagerModel;
import model.AccountManagerModel.Currency;
import view.AccountManagerView;
import view.AccountViewEditor;
import view.AgentView;

public class MainPageController extends AbstractController{
	
	/**
	 * writes changes to accounts to a file
	 * @param file
	 */
	public static void writeToFile(String file){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file,"UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(AccountManagerModel hi : AccountManagerView.accounts){
			writer.println(hi.toString(Currency.DOLLARS));
		}
		writer.close();
	}
	
	/**
	 * handles option selected from view and updates model
	 * @param option
	 */
	public static void operation(String option){
		if(option == AccountManagerView.inDollars){
			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					AccountViewEditor frame = new AccountViewEditor(
							AccountManagerView.accounts.get(AccountManagerView.jComboBox1.getSelectedIndex()),
							new AccountManagerController(), Currency.DOLLARS);
					frame.setVisible(true);
				}
			});

		}
		else if(option == AccountManagerView.inEuros){
			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					AccountViewEditor frame = new AccountViewEditor(
							AccountManagerView.accounts.get(AccountManagerView.jComboBox1.getSelectedIndex()),
							new AccountManagerController(), Currency.EUROS);
					frame.setVisible(true);
				}
			});

		}
		else if(option == "Create deposit agent"){
			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					AgentView frame = new AgentView(
							AccountManagerView.accounts.get(AccountManagerView.jComboBox1.getSelectedIndex()),
							new AgentController(), true);
					frame.setVisible(true);
				}
			});
			
		}
		else if(option == "Create withdraw agent"){
			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					AgentView frame = new AgentView(
							AccountManagerView.accounts.get(AccountManagerView.jComboBox1.getSelectedIndex()),
							new AgentController(), false);
					frame.setVisible(true);
				}
			});
		}
		else if(option == AccountManagerView.inYen){
			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					AccountViewEditor frame = new AccountViewEditor(
							AccountManagerView.accounts.get(AccountManagerView.jComboBox1.getSelectedIndex()),
							new AccountManagerController(), Currency.YEN);
					frame.setVisible(true);
				}
			});

		}
		else if(option == "Exit"){
			writeToFile(AccountManagerView.filename);
			AccountManagerView.view.setVisible(false);
			AccountManagerView.view.dispose();
			System.exit(0);
		}
		else if(option == "Save"){
			writeToFile(AccountManagerView.filename);
			System.out.println("Saved to file\n");

		}
	
	}

}
