/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */


package ast;

public class CompositeStatement extends Statement {
	public void genC(PW pw){
		sList.genC(pw);
	}
	
	public CompositeStatement(StatementList sList){
		this.sList = sList; 
	}
	
	private StatementList sList;
}
