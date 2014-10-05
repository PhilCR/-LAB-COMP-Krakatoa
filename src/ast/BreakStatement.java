package ast;

public class BreakStatement extends Statement{
	public void genK(PW pw){
		pw.printlnIdent("break;");
	}
	
	
}
