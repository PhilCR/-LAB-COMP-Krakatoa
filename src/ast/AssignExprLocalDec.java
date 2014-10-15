/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */


package ast;

import java.util.Iterator;

public class AssignExprLocalDec extends Expr{
	//AQUI VAI TER QUE TRATAR CASOS LOUCAMENTE 
	public void genK( PW pw, boolean putParenthesis ) {
		
		if(firstExpr == null){
			if(localList != null){
				Iterator<Variable> varIt = localList.elements();
				Variable aux;
				while(varIt.hasNext()){
					aux = varIt.next();
					pw.print(aux.getType().getName()+" "+aux.getName());
				}
			}
		}else{
			firstExpr.genK(pw, putParenthesis);
			if(secondExpr != null){
				pw.print(" = ");
				secondExpr.genK(pw, putParenthesis);
			}
		}
	}
	   
	public Type getType() {
		if(firstExpr != null)
			return firstExpr.getType();
		if(localList != null){
			Iterator<Variable> i = localList.elements();
			return i.next().getType();
		}
		return null;
	}
	
	public AssignExprLocalDec(Expr first, Expr second){
		firstExpr = first;
		secondExpr = second;
		localList = null;
	}
	
	/*public AssignExprLocalDec(LocalVarList localList, Expr first){
		firstExpr = first;
		this.localList = localList;
		secondExpr = null;
	}*/
	
	public AssignExprLocalDec(Expr first){
		firstExpr = first;
		localList = null;
		secondExpr = null;
	}
	
	public AssignExprLocalDec(LocalVarList localList){
		this.localList = localList;
		firstExpr = null;
		secondExpr = null;
	}
	
	private Expr firstExpr;
	private Expr secondExpr;
	private LocalVarList localList;
}
