package model;


public class InvalidID extends Exception {
	InvalidID(int id){
		super("This ID is already in use: " + id);
	}

}
