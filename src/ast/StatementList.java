/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

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
	
	
	public void genC(PW pw){
		Iterator<Statement> statIt = sList.iterator();
		while(statIt.hasNext()){
			statIt.next().genC(pw);
			pw.println("");
			//pw.printIdent("");
		}
	}
	
	private ArrayList<Statement> sList;
}
