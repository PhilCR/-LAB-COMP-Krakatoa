package ast;

import java.util.ArrayList;

public class StatementList {
	public StatementList(){
		sList = new ArrayList<Statement>();
	}
	
	public void addElement(Statement s){
		sList.add(s);
	}
	
	public void genC(PW pw){
		
	}
	
	private ArrayList<Statement> sList;
}
