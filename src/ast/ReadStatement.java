/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class ReadStatement extends Statement{
	public void genK(PW pw){
		String printable = "";
		if(selfClass)
			printable += "this.";
		if(staticName != null)
			printable += staticName+".";
		printable += readable.getName();
		pw.printlnIdent("read("+printable+");");
	}
	
	public ReadStatement(Variable r, boolean self, String staticClassName){
		readable = r;
		selfClass = self;
		this.staticName = staticClassName;
	}
	
	
	
	private Variable readable; 
	private boolean selfClass;
	private String staticName;
	
}
