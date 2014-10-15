/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class WriteStatement extends Statement{
	public void genK(PW pw){
		if(insideFlag){
			pw.printIdent("write(");
			exprList.genK(pw);
			pw.print(");");
		}else{
			pw.printIdent("write(");
			exprList.genK(pw);
			pw.print(");");
		}
	}
	
	public WriteStatement(ExprList exprList, boolean flagIn){
		this.exprList = exprList;
		this.insideFlag = flagIn;
	}
	
	private ExprList exprList;
	private boolean insideFlag;
}
