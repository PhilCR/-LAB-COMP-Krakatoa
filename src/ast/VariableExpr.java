/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe C�sar Ramos 380415
 * */

package ast;

public class VariableExpr extends Expr {
    
    public VariableExpr( Variable v ) {
        this.v = v;
    }
    
    public void genK( PW pw, boolean putParenthesis ) {
        pw.print( v.getName() );
    }
    
    public Type getType() {
        return v.getType();
    }
    
    private Variable v;
}