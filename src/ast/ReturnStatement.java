/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class ReturnStatement extends Statement{
	public void genK(PW pw){
		pw.println("");
		pw.printIdent("return ");
		returnExpr.genK(pw, false);
		pw.print(";");
	}
	
	public ReturnStatement(Expr returnExpr){
		this.returnExpr = returnExpr;
	}
	
	private Expr returnExpr;
}
