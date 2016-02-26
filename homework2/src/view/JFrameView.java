package view;
import java.util.ArrayList;

import javax.swing.JFrame;
import model.Model;
import model.AbstractModel;
import model.AccountManagerModel;
import model.ModelListener;
import controller.Controller;

abstract public class JFrameView extends JFrame implements View, ModelListener {
	
	private Model model;
	private Controller controller;
	public JFrameView (Model accounts, Controller contr){
		setModel(accounts);
		setController(contr);
	}
	public void registerWithModel(){
		((AbstractModel)model).addModelListener(this);
	}
	public Controller getController(){return controller;}
	
	public void setController(Controller controller){this.controller = controller;}
	
	public Model getModel(){return model;}
	
	public void setModel(Model model) {
		this.model = model;
		registerWithModel();
	}

}
