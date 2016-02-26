package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import model.AccountManagerModel;
import model.AccountManagerModel.Currency;
import model.Model;
import model.ModelEvent;
import controller.Controller;
import controller.AccountManagerController;
import view.AccountManagerView;


public class AccountViewEditor extends JFrameView {

	private JPanel contentPane;
	private JTextField txtAvailableFunds;
	private JTextField textNewAmount;
	private Currency type;
	private Handler handler = new Handler();


	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public AccountViewEditor(Model model, Controller controller, Currency type) {
		super(model,controller);
		controller.setModel(model);
		controller.setView(this);
		this.type = type;
		setTitle(((AccountManagerModel)getModel()).getName() + " " + ((AccountManagerModel)getModel()).getAcntNumber() + 
				" Operations in " + type);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		txtAvailableFunds = new JTextField();
		txtAvailableFunds.setText("Available funds: " + AccountManagerModel
				.toDecimalFormat(((AccountManagerModel)getModel()).getBalance(type)));
		txtAvailableFunds.setEditable(false);
		txtAvailableFunds.setColumns(10);
		
		textNewAmount = new JTextField();
		textNewAmount.setText("0.0");
		textNewAmount.setColumns(10);
		
		JTextArea txtrEnterAmountIn = new JTextArea();
		txtrEnterAmountIn.setText("Enter amount in ");
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(handler);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(handler);
		
		JButton btnDismiss = new JButton("Dismiss");
		btnDismiss.addActionListener(handler);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textNewAmount, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtAvailableFunds, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtrEnterAmountIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnDeposit)
							.addGap(18)
							.addComponent(btnWithdraw)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDismiss)))
					.addContainerGap(149, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(22)
					.addComponent(txtAvailableFunds, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(34)
					.addComponent(txtrEnterAmountIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textNewAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDeposit)
						.addComponent(btnWithdraw)
						.addComponent(btnDismiss))
					.addContainerGap(76, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private class Handler implements ActionListener{
		public void actionPerformed(ActionEvent evt){
			((AccountManagerController)getController()).operation(evt.getActionCommand(),type);
			//((AccountManagerView))
		}
	}
	

	/**
	 * gets amount from text field
	 * @return
	 */
	public double getAmount(){
		
		try {
			double returnAmount = Double.parseDouble(textNewAmount.getText());
			textNewAmount.setText("0.0");
			return returnAmount;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Enter a number");
		}
		return 0;
	}
	
	/**
	 * updates view when controller creates a model event to reflect changes
	 * made to the model
	 */
	public void modelChanged(ModelEvent me){
		if(me.getKind() == ModelEvent.EventKind.BalanceUpdate){
			txtAvailableFunds.setText("Available funds: " + 
					AccountManagerModel.toDecimalFormat(AccountManagerModel.convertToType(me.getBalance(), type)));
			int currentIndex = AccountManagerView.jComboBox1.getSelectedIndex();
			AccountManagerView.boxModel.removeAllElements();
			for(AccountManagerModel element : AccountManagerView.accounts){
	        	AccountManagerView.boxModel.addElement(element.toString(Currency.DOLLARS)); ;
			}
			
			Collections.sort(AccountManagerView.accounts,AccountManagerModel.AcntNumberComparator);
			
			AccountManagerView.jComboBox1.setSelectedIndex(currentIndex);
			
		}
	}

}
