/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */


package ast;

import lexer.*;

public class UnaryExpr extends Expr {

	public UnaryExpr(Expr expr, Symbol op) {
		this.expr = expr;
		this.op = op;
	}

	@Override
	public void genC(PW pw, boolean putParenthesis) {
		boolean negFlag = false;
		switch (op) {
		case PLUS:
			pw.print("+");
			break;
		case MINUS:
			pw.print("-");
			break;
		case NOT:
			pw.print("!");
			negFlag = true;
			break;
		default:
			pw.print(" internal error at UnaryExpr::genK");

		}
		if(expr.getType().getName().compareTo(Type.booleanType.getName()) == 0){
			expr.genC(pw, true);
			if(negFlag)
				pw.print(" != false");
			else
				pw.print(" == false");
		}else{
			expr.genC(pw, false);
		}
		
	}

	@Override
	public Type getType() {
		return expr.getType();
	}

	private Expr	expr;
	private Symbol	op;
}
