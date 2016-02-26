package view;
import model.Model;
import model.AccountManagerModel;
import model.AccountManagerModel.Currency;
import controller.Controller;
import controller.AccountManagerController;

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Handler;

import controller.MainPageController;
import model.AccountManagerModel;
import model.ModelEvent;
import model.ModelListener;

public class AccountManagerView extends JFrame{
	

	public final static String inDollars = "Edit Account in $";
	public final static String inEuros = "Edit account in Euro";
	public final static String inYen = "Edit Account in Yen";
	public static ArrayList<AccountManagerModel> accounts;
	public static int NumberOfAccounts = 0;
	public static String filename = null;


	
	private javax.swing.JButton editDollars;
    private javax.swing.JButton editEuros;
    private javax.swing.JButton editYen;
    private javax.swing.JButton save;
    private javax.swing.JButton exit;
    private javax.swing.JButton depAgent;
    private javax.swing.JButton wthAgent;
    public static javax.swing.JComboBox jComboBox1;
    public static DefaultComboBoxModel boxModel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private Handler handler = new Handler();
    public static AccountManagerView view;
 
    
    
	
	public AccountManagerView() {
		initComponents();
		
	}
	
    private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		editDollars = new javax.swing.JButton();
		editEuros = new javax.swing.JButton();
		editYen = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
		save = new javax.swing.JButton();
		exit = new javax.swing.JButton();
		depAgent = new javax.swing.JButton();
		wthAgent = new javax.swing.JButton();
		//jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        editDollars.setText(inDollars);
        editDollars.addActionListener(handler);

        editEuros.setText(inEuros);
        editEuros.addActionListener(handler);

        editYen.setText(inYen);
        editYen.addActionListener(handler);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editYen, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(editDollars, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editEuros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editDollars)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editEuros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editYen)
                .addContainerGap())
        );

        save.setText("Save");
        save.addActionListener(handler);

        exit.setText("Exit");
        exit.addActionListener(handler);
        
        depAgent.setText("Create deposit agent");
        depAgent.addActionListener(handler);

        wthAgent.setText("Create withdraw agent");
        wthAgent.addActionListener(handler);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(save)
                    .addComponent(depAgent)
                    .addComponent(wthAgent)
                    )
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(depAgent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wthAgent)
            
                .addContainerGap())
        );
       

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        
        this.setSize(500,300);
    }
	
	public void modelChanged(ModelEvent me){
		
	}
	
	/**
	 * gets user selection from view and hands it to the controller
	 * @author John
	 *
	 */
	private class Handler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			MainPageController.operation(event.getActionCommand());
		}
	}
	
	/**
	 * gets file name from command line
	 * @return string of for the file specified
	 */
	public static String getFile(){
		/*System.out.println("Enter a file to start\n");
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
		*/
		//uncomment for testing
		
		return "dataforHW2.txt";
		
	}
	
	/**
	 * gets the number of lines from file to determine number of accounts
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static int getNumberOfAccounts(String file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int lines = 0;
		while(reader.readLine() != null) lines++;
		reader.close();
		return lines;

	}
	
	/**
	 * fills arrays with account information from file
	 * @param acntNumbers fills an array of account numbers
	 * @param name fills an array of names
	 * @param balances fills an array of balances
	 * @param file file we are extracting the data from
	 */
	public static void fillArrays(int[] acntNumbers, String[] name, double[] balances,String file){
		String [] lineSplitter = new String[4];
		String line = null;
		try{
			FileReader filereader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(filereader);
			int i = 0;
			while((line = bufferedReader.readLine()) != null){
				lineSplitter = line.split(" ", 4);
				name[i] = lineSplitter[0] + " " + lineSplitter[1];
				acntNumbers[i] = Integer.parseInt(lineSplitter[2]);
				balances[i] = Double.parseDouble(lineSplitter[3].substring(1));
				i++;	
			}
			bufferedReader.close();
		}
		catch(IOException ex){
			System.out.println("Could not work with file");
			String filename = getFile();
			try {
				NumberOfAccounts = getNumberOfAccounts(filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fillArrays(acntNumbers, name, balances, filename);
		}
	}
	
	
	@SuppressWarnings("null")
	public static void main(String[] args){
		

		filename = getFile();
		try {
			NumberOfAccounts = getNumberOfAccounts(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int [] acntNumbers = new int[NumberOfAccounts];
		double [] acntBalances = new double[NumberOfAccounts];
		String [] names = new String[NumberOfAccounts];
		final AccountManagerController contr = new AccountManagerController();
		accounts = new ArrayList<AccountManagerModel>();
		
		fillArrays(acntNumbers, names, acntBalances, filename);
		
		
		for(int i = 0; i < NumberOfAccounts; i ++){
			accounts.add(new AccountManagerModel(names[i], acntNumbers[i], acntBalances[i]));
			System.out.println(accounts.get(i).toString(Currency.DOLLARS));
		}
		
		Collections.sort(accounts,AccountManagerModel.AcntNumberComparator);
		
		
		//do some sort of sorting here
		
		int j = 0;
		for(AccountManagerModel element : accounts){
        	names[j++] = element.toString(Currency.DOLLARS);
        }
		
		boxModel = new DefaultComboBoxModel( names );	
		jComboBox1 = new JComboBox();
		jComboBox1.setModel(boxModel);
		view = new AccountManagerView();
		view.setVisible(true);

    }
		

}


