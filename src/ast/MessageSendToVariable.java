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
    
    public void genC( PW pw, boolean putParenthesis ) {
        
    }

    private KraClass classIdent;
    private Variable variable;
    private Method methodCall;
    private ExprList exprList;
}    