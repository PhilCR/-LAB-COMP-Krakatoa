package ast;

import java.util.Iterator;

/*
 * Krakatoa Class
 */
public class KraClass extends Type {
	
   public KraClass( String name ) {
      super(name);
   }
   
   public String getCname() {
      return getName();
   }
   
   public String getName(){
	   return this.name;
   }
   
   //Procura instancias dentro da classe, como instancia static podem ter o mesmo nome de uma instancia nao static,
   // entao procura-se nas suas devidas listas
   public InstanceVariable searchInstance(String name, boolean staticFlag){
	   //Cria o iterator pra percorrer a lista de instancias corretas
	   Iterator<InstanceVariable> varIterator;
	   //se a instancia eh static, entao procure na static instance list
	   if(staticFlag){
		   varIterator = staticInstanceList.elements();
	   // senao procure na instance variable list normal
	   }else{
		   varIterator = instanceVariableList.elements();
	   }
	   //loop pra procurar isso 
	   while(varIterator.hasNext()){
		   
		   InstanceVariable var = varIterator.next();
		   //Se achar uma instancia com o nome que procuro, retorne-a
		   if(var.getName().compareTo(name) > 0){
			   return var;
		   }
	   }
	   //se nao achar nenhuma, entao retorna null
	   return null;
   }
   
   private String name;
   private KraClass superclass;
   private InstanceVariableList instanceVariableList;
   private InstanceVariableList staticInstanceList;
   
   //private MethodList publicMethodList, privateMethodList, publicStaticMethodList, privateStaticMethodList;
   
   // métodos públicos get e set para obter e iniciar as variáveis acima,
   // entre outros métodos
}
