/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */


package ast;

public class BreakStatement extends Statement{
	public void genK(PW pw){
		pw.printlnIdent("break;");
	}
	
	
}
