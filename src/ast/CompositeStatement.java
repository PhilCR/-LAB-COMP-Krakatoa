package ast;

public class CompositeStatement extends Statement {
	public void genK(PW pw){
		sList.genK(pw);
	}
	
	public CompositeStatement(StatementList sList){
		this.sList = sList; 
	}
	
	private StatementList sList;
}
