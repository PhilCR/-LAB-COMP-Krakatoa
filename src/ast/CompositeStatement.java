package ast;

public class CompositeStatement extends Statement {
	public void genC(PW pw){
		
	}
	
	public CompositeStatement(StatementList sList){
		this.sList = sList; 
	}
	
	private StatementList sList;
}
