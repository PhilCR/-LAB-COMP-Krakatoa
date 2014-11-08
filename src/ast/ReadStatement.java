/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class ReadStatement extends Statement{
	public void genC(PW pw){
		pw.printlnIdent("{");
		pw.add();
		if(selfClass){
			Type t = readable.getType();
			if(t.getName().equals(Type.stringType.getName())){
				pw.printlnIdent("char __s[512];");
				pw.printlnIdent("gets(__s);");
				pw.printlnIdent("_static_"+readable.getName()+" = malloc(strlen(__s) + 1);");
				pw.printlnIdent("strcpy(this->_"+readable.getType().getName()+"_"+readable.getName()+", __s);");
			}else{
				pw.printlnIdent("char __s[512];");
				pw.printlnIdent("gets(__s);");
				pw.printlnIdent("sscanf(__s, \"%d\", &this->_"+readable.getType().getName()+"_"+readable.getName()+");");
			}
		}
		else if(staticName != null){
			Type t = readable.getType();
			if(t.getName().equals(Type.stringType.getName())){
				pw.printlnIdent("char __s[512];");
				pw.printlnIdent("gets(__s);");
				pw.printlnIdent("_static_"+readable.getName()+" = malloc(strlen(__s) + 1);");
				pw.printlnIdent("strcpy(_static_"+readable.getName()+", __s);");
			}else{
				pw.printlnIdent("char __s[512];");
				pw.printlnIdent("gets(__s);");
				pw.printlnIdent("sscanf(__s, \"%d\", &_static_"+readable.getName()+");");
			}
		}else{
			Type t = readable.getType();
			if(t.getName().equals(Type.stringType.getName())){
				pw.printlnIdent("char __s[512];");
				pw.printlnIdent("gets(__s);");
				pw.printlnIdent("_"+readable.getName()+" = malloc(strlen(__s) + 1);");
				pw.printlnIdent("strcpy(_"+readable.getName()+", __s);");
			}else{
				pw.printlnIdent("char __s[512];");
				pw.printlnIdent("gets(__s);");
				pw.printlnIdent("sscanf(__s, \"%d\", &_"+readable.getName()+");");
			}
		}
		pw.sub();
		pw.printlnIdent("}");
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
