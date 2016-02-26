package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import controller.AccountManagerController;
import controller.Controller;
import controller.InsufficientFundsController;
import model.AccountManagerModel;
import model.AccountManagerModel.Currency;
import model.Model;
import model.ModelEvent;


import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.JButton;

public class InsufficientFundsView extends JFrameView {

	private JPanel contentPane;
	private JTextField txtInsufficientFundsAmount;
	AccountManagerModel model;
	private Handler handler = new Handler();
	
	
	
	


	public InsufficientFundsView(Model model, Controller controller, double amount, Currency type) {
		super(model,controller);
		controller.setModel(model);
		controller.setView(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtInsufficientFundsAmount = new JTextField();
		txtInsufficientFundsAmount.setEditable(false);
		txtInsufficientFundsAmount.setText("Insufficient funds: amount to withdraw "
				+ amount + " is greater than available funds: "
				+ ((AccountManagerModel)getModel()).getBalance(type));
		txtInsufficientFundsAmount.setBounds(10, 32, 414, 61);
		contentPane.add(txtInsufficientFundsAmount);
		txtInsufficientFundsAmount.setColumns(10);
		
		JButton btnDismiss = new JButton("Dismiss");
		btnDismiss.addActionListener(handler);
		btnDismiss.setBounds(10, 135, 89, 23);
		contentPane.add(btnDismiss);
	}
	
	private class Handler implements ActionListener{
		public void actionPerformed(ActionEvent evt){
			((InsufficientFundsController)getController()).operation(evt.getActionCommand());
			//((AccountManagerView))
		}
	}



	@Override
	public void modelChanged(ModelEvent event) {
		// TODO Auto-generated method stub
	}
	
	
}
