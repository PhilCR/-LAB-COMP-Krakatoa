/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class LiteralString extends Expr {
    
    public LiteralString( String literalString ) { 
        this.literalString = literalString;
    }
    
    public void genK( PW pw, boolean putParenthesis ) {
        pw.print("\""+literalString+"\"");
    }
    
    public Type getType() {
        return Type.stringType;
    }
    
    private String literalString;
}
