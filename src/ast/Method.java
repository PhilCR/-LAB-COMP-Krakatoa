/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

import java.util.Iterator;

public class Method {
	public Method(Type t, String name, boolean privateFlag, boolean staticFlag, boolean finalFlag, KraClass thisClass){
		this.t = t;
		this.name = name;
		formalParamList = null;
		this.privateFlag = privateFlag;
		this.staticFlag = staticFlag;
		this.finalFlag = finalFlag;
		this.thisClass = thisClass;
	}
	
	
	public Type getType() {
		return t;
	}

	public String getName(){
		return name;
	}
	
	public boolean isParamEmpty(){
		return formalParamList.isEmpty();
	}
	
	public boolean isPrivate(){
		return privateFlag;
	}
	
	public Iterator<Variable> getParamElements(){
		return formalParamList.elements();
	}
	
	public int getParamListSize(){
		return formalParamList.getSize();
	}
	
	public ParamList getParamList(){
		return formalParamList;
	}
	
	public void setStatementList(StatementList sList){
		this.sList = sList;
	}
	
	public void setParamList(ParamList formalParamList){
		this.formalParamList = formalParamList;
	}
	
	public boolean isStatic(){
		return this.staticFlag;
	}
	
	public boolean isFinal(){
		return this.finalFlag;
	}
	
	public String getCname(){
		if(staticFlag)
			return "_static_"+thisClass.getName()+"_"+name;
		else
			return "_"+thisClass.getName()+"_"+name;
	}
	
	public KraClass getThisClass(){
		return this.thisClass;
	}
	
	public void genC(PW pw){
		if(staticFlag){
			if(t.getCname().compareTo("int") == 0 || t.getCname().compareTo("char *") == 0 || t.getCname().compareTo("void") == 0)
				pw.print(t.getCname()+" _static_"+thisClass.getName()+"_"+name+"(");
			else
				pw.print(t.getCname()+"* _static_"+thisClass.getName()+"_"+name+"(");
			if(formalParamList != null){
				Iterator<Variable> paramIt = formalParamList.elements();
				while(paramIt.hasNext()){
					paramIt.next().genC(pw);
					if(paramIt.hasNext())
						pw.print(", ");
				}
			}
			pw.print("){");
			pw.println("");
			pw.add();
			//pw.printIdent("");
			sList.genC(pw);
			
			pw.sub();
			pw.println("");
			pw.printlnIdent("}");
		}else{
			if(t.getCname().compareTo("int") == 0 || t.getCname().compareTo("char *") == 0 || t.getCname().compareTo("void") == 0)
				pw.print(t.getCname()+" _"+thisClass.getName()+"_"+name+"( "+thisClass.getCname()+" *this");
			else
				pw.print(t.getCname()+"* _"+thisClass.getName()+"_"+name+"( "+thisClass.getCname()+" *this");
			if(formalParamList != null){
				pw.print(", ");
				Iterator<Variable> paramIt = formalParamList.elements();
				while(paramIt.hasNext()){
					paramIt.next().genC(pw);
					if(paramIt.hasNext())
						pw.print(", ");
				}
			}
			pw.print("){");
			pw.println("");
			pw.add();
			//pw.printIdent("");
			sList.genC(pw);
			
			pw.sub();
			pw.println("");
			pw.printlnIdent("}");
		}
	}
	
	private Type t;
	private StatementList sList;
	private ParamList formalParamList;
	private String name;
	private boolean privateFlag;
	private boolean staticFlag;
	private boolean finalFlag;
	private KraClass thisClass;
}
