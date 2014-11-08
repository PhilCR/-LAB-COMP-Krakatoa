/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class ReturnStatement extends Statement{
	public void genC(PW pw){
		pw.println("");
		pw.printIdent("return ");
		returnExpr.genC(pw, false);
		pw.print(";");
	}
	
	public ReturnStatement(Expr returnExpr){
		this.returnExpr = returnExpr;
	}
	
	private Expr returnExpr;
}
