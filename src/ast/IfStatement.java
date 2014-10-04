package ast;

public class IfStatement extends Statement{
	public void genC(PW pw){
		
	}
	
	public IfStatement(Expr conditionExpr, Statement statement){
		this.conditionExpr = conditionExpr;
		this.statement = statement;
	}
	
	public IfStatement(Expr conditionExpr, Statement statement, Statement elseStatement){
		this.conditionExpr = conditionExpr;
		this.statement = statement;
		this.elseStatement = elseStatement;
	}
	
	private Expr conditionExpr;
	private Statement elseStatement;
	private Statement statement;
}
