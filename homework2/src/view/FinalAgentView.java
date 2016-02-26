package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import controller.FinalAgentController;
import model.Model;
import model.ModelEvent;

import javax.swing.JTextField;
import javax.swing.JButton;

import model.Agent;
import model.DepositAgent;
import model.WithdrawAgent;

import view.AgentView;

public class FinalAgentView extends JFrameView {

	private JPanel contentPane;
	private JTextField txtAmountIn;
	private JTextField txtOperationsPerSecond;
	private JTextField txtState;
	private JTextField txtTransfered;
	private JTextField txtOperationsCompleted;
	private Boolean depositAgent;
	private JButton btnDismissAgent;
	Handler handler = new Handler();


	public FinalAgentView(Model model, Controller controller, Boolean depAgent) {
		super(model,controller);
		controller.setModel(model);
		controller.setView(this);
		this.depositAgent = depAgent;
		
		if(depAgent){
			setTitle("Deposit agent " + ((DepositAgent)getModel()).getName() + " for account " 
					+ ((DepositAgent)getModel()).getAccount().getAcntNumber());
		}else{
			setTitle("Withdraw agent " + ((WithdrawAgent)getModel()).getName() + " for account " 
					+ ((WithdrawAgent)getModel()).getAccount().getAcntNumber());
			
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 548, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtAmountIn = new JTextField();
		txtAmountIn.setEditable(false);
		
		if(depAgent){
			txtAmountIn.setText("Amount in $: " + ((DepositAgent)getModel()).getAmount());
		}else{
			txtAmountIn.setText("Amount in $: " + ((WithdrawAgent)getModel()).getAmount());
		}
		
		txtAmountIn.setBounds(25, 28, 217, 20);
		contentPane.add(txtAmountIn);
		txtAmountIn.setColumns(10);
		
		txtOperationsPerSecond = new JTextField();
		txtOperationsPerSecond.setEditable(false);
		
		if(depAgent){
			txtOperationsPerSecond.setText("Operations per second: " + ((DepositAgent)getModel()).getOpPerSec());
		}else{
			txtOperationsPerSecond.setText("Operations per second: " + ((WithdrawAgent)getModel()).getOpPerSec());
		}
		
		txtOperationsPerSecond.setBounds(25, 59, 217, 20);
		contentPane.add(txtOperationsPerSecond);
		txtOperationsPerSecond.setColumns(10);
		
		txtState = new JTextField();
		txtState.setEditable(false);
		txtState.setText("State: RUNNING");
		txtState.setBounds(25, 90, 217, 20);
		contentPane.add(txtState);
		txtState.setColumns(10);
		
		txtTransfered = new JTextField();
		txtTransfered.setEditable(false);
		txtTransfered.setText("Amount in $ transferred: 0");
		txtTransfered.setBounds(25, 121, 217, 20);
		contentPane.add(txtTransfered);
		txtTransfered.setColumns(10);
		
		txtOperationsCompleted = new JTextField();
		txtOperationsCompleted.setEditable(false);
		txtOperationsCompleted.setText("Operations completed: 0");
		txtOperationsCompleted.setBounds(25, 152, 217, 20);
		contentPane.add(txtOperationsCompleted);
		txtOperationsCompleted.setColumns(10);
		
		JButton btnStopAgent = new JButton("Stop agent");
		btnStopAgent.addActionListener(handler);
		btnStopAgent.setBounds(25, 198, 115, 23);
		contentPane.add(btnStopAgent);
		
		btnDismissAgent = new JButton("Dismiss agent");
		btnDismissAgent.addActionListener(handler);
		btnDismissAgent.setBounds(137, 198, 120, 23);
		contentPane.add(btnDismissAgent);
		btnDismissAgent.setEnabled(false);
	}
	
	private class Handler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			((FinalAgentController)getController()).operation(event.getActionCommand(), depositAgent);
		}
	}
	
	/**
	 * enables dismiss button
	 */
	public void enableDismiss(){
		btnDismissAgent.setEnabled(true);
	}
	
	/**
	 * disables dismiss button
	 */
	public void disableDismiss(){
		btnDismissAgent.setEnabled(false);
	}


	@Override
	/**
	 * handles model event created by controller to reflect changes on the view
	 */
	public void modelChanged(ModelEvent event) {
		if(event.getKind() == ModelEvent.EventKind.AmountTransferredUpdate){
			txtTransfered.setText("Amount in $ transferred: " + event.getBalance());
			if(depositAgent){
				txtOperationsCompleted.setText("Operations completed: " + ((DepositAgent)getModel()).getNumberOfTransactions());
			}else{
				txtOperationsCompleted.setText("Operations completed: " + ((WithdrawAgent)getModel()).getNumberOfTransactions());
			}
		}else if(event.getKind() == ModelEvent.EventKind.AgentStatusUpdate){
			txtState.setText("State: " + event.getAgentStatus());
		
		}
		
	}

}
