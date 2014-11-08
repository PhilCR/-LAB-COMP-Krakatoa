/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class WhileStatement extends Statement{
	public void genC(PW pw){
		pw.printIdent("while(");
		expr.genC(pw, true);
		pw.print("){");
		pw.add();
		pw.println("");
		statement.genC(pw);
		pw.sub();
		pw.printlnIdent("}");
	}

	public WhileStatement(Expr expr, Statement statement){
		this.expr = expr;
		this.statement = statement;
	}

	private Expr expr;
	private Statement statement;
}
