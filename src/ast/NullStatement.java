/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe C�sar Ramos 380415
 * */

package ast;

public class NullStatement extends Statement{
	public void genC(PW pw){
		pw.printIdent(";");
		pw.println("");
	}
}
