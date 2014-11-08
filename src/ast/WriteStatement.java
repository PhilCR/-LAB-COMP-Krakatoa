/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

import java.util.Iterator;

public class WriteStatement extends Statement{
	public void genC(PW pw){
		if(insideFlag){
			Iterator<Expr> exprIt = exprList.elements();
			while(exprIt.hasNext()){
				Expr aux = exprIt.next();
				if(aux.getType().getName().equals(Type.stringType.getName())){
					pw.printIdent("puts( ");
					aux.genC(pw, false);
					pw.print(" );");
				}else{
					pw.printIdent("printf(\"%d \", ");
					aux.genC(pw, false);
					pw.print(" );");
				}
			}
			pw.print("printf(\"\n\");");
		}else{
			Iterator<Expr> exprIt = exprList.elements();
			while(exprIt.hasNext()){
				Expr aux = exprIt.next();
				if(aux.getType().getName().equals(Type.stringType.getName())){
					pw.printIdent("puts( ");
					aux.genC(pw, false);
					pw.print(" );");
				}else{
					pw.printIdent("printf(\"%d \", ");
					aux.genC(pw, false);
					pw.print(" );");
				}
			}
		}
		
	}
	
	public WriteStatement(ExprList exprList, boolean flagIn){
		this.exprList = exprList;
		this.insideFlag = flagIn;
	}
	
	private ExprList exprList;
	private boolean insideFlag;
}
