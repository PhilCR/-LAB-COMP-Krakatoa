package ast;

import java.util.Iterator;

public class Method {
	public Method(Type t, String name, boolean privateFlag, boolean staticFlag, boolean finalFlag){
		this.t = t;
		this.name = name;
		formalParamList = null;
		this.privateFlag = privateFlag;
		this.staticFlag = staticFlag;
		this.finalFlag = finalFlag;
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
	
	private Type t;
	private StatementList sList;
	private ParamList formalParamList;
	private String name;
	private boolean privateFlag;
	private boolean staticFlag;
	private boolean finalFlag;
}
