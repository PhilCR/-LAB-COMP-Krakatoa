package ast;

public class NullStatement extends Statement{
	public void genK(PW pw){
		pw.printIdent(";");
		pw.println("");
	}
}
