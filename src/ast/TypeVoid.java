/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class TypeVoid extends Type {
    
    public TypeVoid() {
        super("void");
    }
    
   public String getCname() {
      return "void";
   }

}