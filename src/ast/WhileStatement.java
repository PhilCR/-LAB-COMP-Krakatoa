package ast;

public class WhileStatement extends Statement{
	public void genC(PW pw){
		int a;
	}

	public WhileStatement(Expr expr, Statement statement){
		this.expr = expr;
		this.statement = statement;
	}

	private Expr expr;
	private Statement statement;
}
