package ast;

import java.util.ArrayList;
import java.util.Iterator;

public class StatementList {
	public StatementList(){
		sList = new ArrayList<Statement>();
	}
	
	public void addElement(Statement s){
		sList.add(s);
	}
	
	
	public void genK(PW pw){
		Iterator<Statement> statIt = sList.iterator();
		while(statIt.hasNext()){
			statIt.next().genK(pw);
			pw.println("");
			//pw.printIdent("");
		}
	}
	
	private ArrayList<Statement> sList;
}
