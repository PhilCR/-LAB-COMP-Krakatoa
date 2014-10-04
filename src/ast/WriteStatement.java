package ast;

public class WriteStatement extends Statement{
	public void genC(PW pw){
		
	}
	
	public WriteStatement(ExprList exprList, boolean flagIn){
		this.exprList = exprList;
		this.insideFlag = flagIn;
	}
	
	private ExprList exprList;
	private boolean insideFlag;
}
