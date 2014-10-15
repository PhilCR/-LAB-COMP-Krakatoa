/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;


public class MessageSendToVariable extends MessageSend { 

	public MessageSendToVariable(KraClass k, Variable v){
		this.classIdent = k;
		this.variable = v;
		methodCall = null;
		exprList = null;
	}
	
	public MessageSendToVariable(KraClass k, Method methodCall, ExprList e){
		this.classIdent = k;
		this.methodCall = methodCall;
		this.variable = null;
		this.exprList = e;
	}
	
	public MessageSendToVariable(KraClass k, Variable v, Method methodCall, ExprList e){
		this.classIdent = k;
		this.variable = v;
		this.methodCall = methodCall;
		this.exprList = e;
	}
	
	public MessageSendToVariable(Variable v, Method methodCall, ExprList e){
		this.variable = v;
		this.methodCall = methodCall;
		this.exprList = e;
		classIdent = null;
	}
	
    public Type getType() { 
    	if(methodCall == null){
    		return variable.getType();
    	}else{
    		return methodCall.getType();
    	}
    }
    
    public void genK( PW pw, boolean putParenthesis ) {
        if(putParenthesis)
        	pw.print("(");
        
        if(methodCall == null && exprList == null){
        	pw.print(classIdent.getName()+"."+variable.getName());
        }else if(classIdent == null){
        	pw.print(variable.getName()+"."+methodCall.getName()+"(");
        	if(exprList != null)
        		exprList.genK(pw);
        	pw.print(")");
        }else if(variable == null){
        	pw.print(classIdent.getName()+"."+methodCall.getName()+"(");
        	if(exprList != null)
        		exprList.genK(pw);
        	pw.print(")");
        }else{
        	pw.print(classIdent.getName()+"."+variable.getName()+"."+methodCall.getName()+"(");
        	if(exprList != null)
        		exprList.genK(pw);
        	pw.print(")");
        }
        
        if(putParenthesis)
        	pw.print(")");
    }

    private KraClass classIdent;
    private Variable variable;
    private Method methodCall;
    private ExprList exprList;
}    