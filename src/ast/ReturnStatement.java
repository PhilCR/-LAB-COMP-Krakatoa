package ast;

public class ReturnStatement extends Statement{
	public void genC(PW pw){
		
	}
	
	public ReturnStatement(Expr returnExpr){
		this.returnExpr = returnExpr;
	}
	
	private Expr returnExpr;
}
