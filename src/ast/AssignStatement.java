package ast;

public class AssignStatement extends Statement{

	public AssignStatement(Expr e){
		assignExpr = e;
	}
	
	@Override
	public void genC(PW pw) {
		// TODO Auto-generated method stub
		
	}

	private Expr assignExpr;
}
