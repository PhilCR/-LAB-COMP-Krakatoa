package ast;

public class MessageSendToSuper extends MessageSend { 

	public MessageSendToSuper(Method m, ExprList p, KraClass s){
		this.message = m;
		this.parameters = p;
		this.superClass = s;
	}
	
    public Type getType() { 
        return message.getType();
    }

    public void genC( PW pw, boolean putParenthesis ) {
        
    }
    
    private Method message;
    private ExprList parameters;
    private KraClass superClass;
}