package ast;

public class IfStatement extends Statement{
	public void genK(PW pw){
		pw.printIdent("if(");
		conditionExpr.genK(pw, true);
		pw.print("){");
		pw.add();
		pw.println("");
		statement.genK(pw);
		pw.sub();
		pw.printlnIdent("}");
		if(elseStatement != null){
			pw.printIdent("else{");
			pw.add();
			pw.println("");
			elseStatement.genK(pw);
			pw.sub();
			pw.println("");
			pw.println("");
			pw.printlnIdent("}");
		}
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
