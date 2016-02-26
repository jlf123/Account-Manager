package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.AccountManagerController;
import controller.AgentController;
import controller.Controller;
import model.AccountManagerModel;
import model.Model;
import model.ModelEvent;

public class AgentView extends JFrameView {
	
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	public Boolean depositAgent;
	Handler handler = new Handler();
	
	public AgentView(Model model, Controller controller, Boolean kindOfAgent){
		super(model,controller);
		controller.setModel(model);
		controller.setView(this);
		this.depositAgent = kindOfAgent;
		
		if(depositAgent){
			setTitle("Start deposit agent for account: " + ((AccountManagerModel)getModel()).getAcntNumber());
		}
		else{
			setTitle("Start withdraw agent for account: " + ((AccountManagerModel)getModel()).getAcntNumber());
		}
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAgentId = new JLabel("Agent ID");
		lblAgentId.setBounds(30, 36, 70, 14);
		contentPane.add(lblAgentId);
		
		JLabel lblAmountIn = new JLabel("Amount in $");
		lblAmountIn.setBounds(30, 80, 67, 14);
		contentPane.add(lblAmountIn);
		
		JLabel lblOperationsPerSecond = new JLabel("Operations per second");
		lblOperationsPerSecond.setBounds(30, 125, 130, 14);
		contentPane.add(lblOperationsPerSecond);
		
		textField = new JTextField();
		textField.setBounds(197, 33, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(197, 77, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(197, 122, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnStartAgent = new JButton("Start agent");
		btnStartAgent.addActionListener(handler);
		
		btnStartAgent.setBounds(30, 192, 120, 23);
		contentPane.add(btnStartAgent);
		
		JButton btnDismiss = new JButton("Dismiss");
		btnDismiss.addActionListener(handler);
		btnDismiss.setBounds(194, 192, 120, 23);
		contentPane.add(btnDismiss);
		
	}
	
	private class Handler implements ActionListener{
		public void actionPerformed(ActionEvent evt){
			((AgentController)getController()).operation(evt.getActionCommand(), depositAgent);
			//((AccountManagerView))
		}
	}

	@Override
	public void modelChanged(ModelEvent event) {
		// TODO Auto-generated method stub
		
	}

	public int getAgentId() {
		return Integer.parseInt(textField.getText());
	}

	public double getAmount() {
		return Double.parseDouble(textField_1.getText());
	}

	public int getOpsPerSec() {
		// TODO Auto-generated method stub
		return Integer.parseInt(textField_2.getText());
	}

}
