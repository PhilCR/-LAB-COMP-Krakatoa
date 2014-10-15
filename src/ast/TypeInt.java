/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class TypeInt extends Type {
    
    public TypeInt() {
        super("int");
    }
    
   public String getCname() {
      return "int";
   }

}