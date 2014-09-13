package ast;

import java.util.Iterator;

/*
 * Krakatoa Class
 */
public class KraClass extends Type {
	
   public KraClass( String name ) {
      super(name);
      //Adicionando inicializacao para null, eh util na hora de inicializar as listas dentro da classe
      // ja que se o valor nao for null entao a lista ja foi inicializada 
      instanceVariableList = null;
      staticInstanceList = null;
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
   
   //Inicializa lista de instancias se ela nao tiver sido inicializada
   public void initInstanceList(){
	   if(instanceVariableList == null){
		   instanceVariableList = new InstanceVariableList();
	   }
   }
   
   //Inicializa a lista de instancias estaticas da mesma forma que a anterior eh inicializada
   public void initStaticInstanceList(){
	   if(staticInstanceList == null){
		   staticInstanceList = new InstanceVariableList();
	   }
   }
   
   //Eh chamada do codigo da analise sintatica, recebe uma lista de instancias e se elas sao estaticas ou nao
   public void addElements(InstanceVariableList v, boolean staticFlag){
	   //cria iterador para os elementos que serao inseridos
	   Iterator<InstanceVariable> i;
	   i = v.elements();
	   //insere os elementos na lista correspondente que eles devem ser inseridos
	   if(staticFlag){
		   while(i.hasNext()){
			   instanceVariableList.addElement(i.next());
		   }
	   }else{
		   while(i.hasNext()){
			   staticInstanceList.addElement(i.next());
		   }
	   }
		   
   }
   
   //retorna super classe
   public KraClass getSuperClass(){
	   return superclass;
   }
   
   //seta super classe
   public void setSuperClass(KraClass superclass){
	   this.superclass = superclass;
   }
   
   private String name;
   private KraClass superclass;
   private InstanceVariableList instanceVariableList;
   private InstanceVariableList staticInstanceList;
   
   //private MethodList publicMethodList, privateMethodList, publicStaticMethodList, privateStaticMethodList;
   
   // métodos públicos get e set para obter e iniciar as variáveis acima,
   // entre outros métodos
}
