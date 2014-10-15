/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe C�sar Ramos 380415
 * */

package ast;

public class WhileStatement extends Statement{
	public void genK(PW pw){
		pw.printIdent("while(");
		expr.genK(pw, true);
		pw.print("){");
		pw.add();
		pw.println("");
		statement.genK(pw);
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
