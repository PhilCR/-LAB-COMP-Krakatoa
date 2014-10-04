package ast;

public class BreakStatement extends Statement{
	public void genC(PW pw){
		pw.println("break;");
	}
	
	
}
