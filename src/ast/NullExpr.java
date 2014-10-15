/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class NullExpr extends Expr {
    
   public void genK( PW pw, boolean putParenthesis ) {
      pw.print("null");
   }
   
   public Type getType() {
       return Type.nullType;
   }
}