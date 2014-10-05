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

    public void genK( PW pw, boolean putParenthesis ) {
    	if(putParenthesis)
    		pw.print("(");
        pw.print("super."+message.getName()+"(");
        if(parameters != null)
        	parameters.genK(pw);
        pw.print(")");
        if(putParenthesis)
    		pw.print(")");
    }
    
    private Method message;
    private ExprList parameters;
    private KraClass superClass;
}