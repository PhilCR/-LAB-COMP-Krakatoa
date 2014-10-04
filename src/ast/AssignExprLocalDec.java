package ast;

import java.util.Iterator;

public class AssignExprLocalDec extends Expr{
	//AQUI VAI TER QUE TRATAR CASOS LOUCAMENTE 
	public void genC( PW pw, boolean putParenthesis ) {
		//pw.printIdent("NULL");
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
	}
	
	public AssignExprLocalDec(LocalVarList localList, Expr first){
		firstExpr = first;
		this.localList = localList;
	}
	
	public AssignExprLocalDec(Expr first){
		firstExpr = first;
	}
	
	public AssignExprLocalDec(LocalVarList localList){
		this.localList = localList;
	}
	
	private Expr firstExpr;
	private Expr secondExpr;
	private LocalVarList localList;
}
