/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class TypeBoolean extends Type {

   public TypeBoolean() { super("boolean"); }

   @Override
   public String getCname() {
      return "int";
   }

}
