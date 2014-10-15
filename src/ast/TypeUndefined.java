/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe C�sar Ramos 380415
 * */

package ast;

public class TypeUndefined extends Type {
    // variables that are not declared have this type
    
   public TypeUndefined() { super("undefined"); }
   
   public String getCname() {
      return "int";
   }
   
}
