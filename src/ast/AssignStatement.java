/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */


package ast;

public class AssignStatement extends Statement{

	public AssignStatement(Expr e){
		assignExpr = e;
	}
	
	@Override
	public void genK(PW pw) {
		// TODO Auto-generated method stub
		pw.printIdent("");
		assignExpr.genK(pw, false);
		pw.print(";");
	}

	private Expr assignExpr;
}
