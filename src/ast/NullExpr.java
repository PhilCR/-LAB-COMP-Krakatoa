package ast;

public class NullExpr extends Expr {
    
   public void genK( PW pw, boolean putParenthesis ) {
      pw.print("null");
   }
   
   public Type getType() {
       return Type.nullType;
   }
}