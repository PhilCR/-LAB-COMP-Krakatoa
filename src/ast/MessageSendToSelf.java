package ast;


public class MessageSendToSelf extends MessageSend {
    
	public MessageSendToSelf(KraClass thisClass){
		this.thisClass = thisClass;
		instance = null;
		eList = null;
		methodCall = null;
	}
	
	public MessageSendToSelf(KraClass thisClass, Variable instance){
		this.thisClass = thisClass;
		this.instance = instance;
		eList = null;
		methodCall = null;
	}
	
	public MessageSendToSelf(KraClass thisClass, Method methodCall, ExprList eList){
		this.thisClass = thisClass;
		this.methodCall = methodCall;
		this.eList = eList;
		instance = null;
	}
	
	public MessageSendToSelf(KraClass thisClass, Variable instance, Method methodCall, ExprList eList){
		this.thisClass = thisClass;
		this.methodCall = methodCall;
		this.eList = eList;
		this.instance = instance;
	}
	
    public Type getType() { 
        if(instance == null && eList == null && methodCall == null)
        	return thisClass;
        if(methodCall == null && eList == null)
        	return instance.getType();
        if(instance == null)
        	return methodCall.getType();
        return methodCall.getType();
    }
    
    public void genC( PW pw, boolean putParenthesis ) {
    }
    
    private KraClass thisClass;
    private Variable instance; 
    private ExprList eList;
    private Method methodCall;
}