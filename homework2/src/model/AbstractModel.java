package model;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractModel implements Model{
	
	private List<ModelListener> listeners = new ArrayList<ModelListener>(5);
	
	public void notifyChanged(ModelEvent event){
		for(ModelListener iterator : listeners){
			iterator.modelChanged(event);
		}
	}
	public void addModelListener(ModelListener l){
		listeners.add(l);
	}
	public void removeModelListener(ModelListener l){
		listeners.remove(l);
	}


}
