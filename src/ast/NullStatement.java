/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class NullStatement extends Statement{
	public void genK(PW pw){
		pw.printIdent(";");
		pw.println("");
	}
}
