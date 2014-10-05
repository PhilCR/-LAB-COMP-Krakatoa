package ast;

import java.util.Iterator;

/*
 * Krakatoa Class
 */
public class KraClass extends Type {
	
   public KraClass( String name, boolean finalFlag ) {
      super(name);
      this.name = name;
      this.finalFlag = finalFlag;
      //Adicionando inicializacao para null, eh util na hora de inicializar as listas dentro da classe
      // ja que se o valor nao for null entao a lista ja foi inicializada 
      instanceVariableList = new InstanceVariableList();
      staticInstanceList = new InstanceVariableList();
      publicMethodList = new MethodList();
      privateMethodList = new MethodList();
      publicStaticMethodList = new MethodList(); 
      privateStaticMethodList = new MethodList();
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
		   if(var.getName().compareTo(name) == 0){
			   return var;
		   }
	   }
	   //se nao achar nenhuma, entao retorna null
	   return null;
   }
   
   public Method searchMethod(String name){
	   Iterator<Method> iterator = publicMethodList.elements();
	   Method m = null;
	   while(iterator.hasNext()){
		   m = iterator.next();
		   if(m.getName().compareTo(name) == 0)
			   return m;
	   }
	   iterator = privateMethodList.elements();
	   while(iterator.hasNext()){
		   m = iterator.next();
		   if(m.getName().compareTo(name) == 0)
			   return m;
	   }
	   iterator = publicStaticMethodList.elements();
	   while(iterator.hasNext()){
		   m = iterator.next();
		   if(m.getName().compareTo(name) == 0)
			   return m;
	   }
	   iterator = privateStaticMethodList.elements();
	   while(iterator.hasNext()){
		   m = iterator.next();
		   if(m.getName().compareTo(name) == 0)
			   return m;
	   }
	   return null;
   }
   
   public Method searchMethodOnlyNonStatic(String name){
	   Iterator<Method> iterator = publicMethodList.elements();
	   Method m = null;
	   while(iterator.hasNext()){
		   m = iterator.next();
		   if(m.getName().compareTo(name) == 0)
			   return m;
	   }
	   iterator = privateMethodList.elements();
	   while(iterator.hasNext()){
		   m = iterator.next();
		   if(m.getName().compareTo(name) == 0)
			   return m;
	   }
	   return null;
   }
   
   public Method searchMethodOnlyStatic(String name){
	   Iterator<Method>iterator = publicStaticMethodList.elements();
	   Method m = null;
	   while(iterator.hasNext()){
		   m = iterator.next();
		   if(m.getName().compareTo(name) == 0)
			   return m;
	   }
	   iterator = privateStaticMethodList.elements();
	   while(iterator.hasNext()){
		   m = iterator.next();
		   if(m.getName().compareTo(name) == 0)
			   return m;
	   }
	   return null;
   }
   
   public Method searchMethodOnlyNonStaticAndPublic(String name){
	   Iterator<Method> iterator = publicMethodList.elements();
	   Method m = null;
	   while(iterator.hasNext()){
		   m = iterator.next();
		   if(m.getName().compareTo(name) == 0)
			   return m;
	   }
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
			   staticInstanceList.addElement(i.next());
		   }
	   }else{
		   while(i.hasNext()){
			   instanceVariableList.addElement(i.next());
		   }
	   }
		   
   }
   
   public Method addMethod(Method d, boolean staticFlag, boolean isPrivate){
	   if(isPrivate){
		   if(staticFlag){
			   privateStaticMethodList.addElement(d);
			   return privateStaticMethodList.returnLast();
		   }else{
			   privateMethodList.addElement(d);
			   return privateMethodList.returnLast();
		   }
	   }else{
		   if(staticFlag){
			   publicStaticMethodList.addElement(d);
			   return publicStaticMethodList.returnLast();
		   }else{
			   publicMethodList.addElement(d);
			   return publicMethodList.returnLast();
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
   
   public boolean isFinal(){
	   return finalFlag;
   }
   
   public void genK(PW pw){
	   String initialPrint = "";
	   if(finalFlag){
		   initialPrint += "final ";
	   }
	   initialPrint += "class "+name+" ";
	   if(superclass != null){
		   initialPrint += "extends "+superclass.getName();
	   }
	   initialPrint += " {";
	   pw.printlnIdent(initialPrint);
	   pw.add();
	   
	   Iterator<InstanceVariable> instIt = instanceVariableList.elements();
	   
	   while(instIt.hasNext()){
		   pw.printIdent("private ");
		   instIt.next().genK(pw);
		   pw.print(";");
		   pw.println("");
	   }
	   pw.println("");
	   instIt = staticInstanceList.elements();
	   
	   while(instIt.hasNext()){
		   pw.printIdent("private static ");
		   instIt.next().genK(pw);
		   pw.print(";");
		   pw.println("");
	   }
	   pw.println("");
	   Method aux = null;
	   Iterator<Method> methodIt = publicMethodList.elements();
	   while(methodIt.hasNext()){
		   aux = methodIt.next();
		   if(aux.isFinal()){
			   pw.printIdent("final public ");
		   }else{
			   pw.printIdent("public ");
		   }
		   
		   aux.genK(pw);
	   }
	   
	   methodIt = privateMethodList.elements();
	   while(methodIt.hasNext()){
		   aux = methodIt.next();
		   pw.printIdent("private ");
		   aux.genK(pw);
	   }
	   
	   methodIt = publicStaticMethodList.elements();
	   while(methodIt.hasNext()){
		   aux = methodIt.next();
		   pw.printIdent("static public ");
		   aux.genK(pw);
	   }
	   
	   methodIt = privateStaticMethodList.elements();	
	   while(methodIt.hasNext()){
		   aux = methodIt.next();
		   pw.printIdent("static private ");
		   aux.genK(pw);
	   }
	   
	   pw.sub();
	   pw.printlnIdent("}");
   }
   
   private String name;
   private KraClass superclass;
   private boolean finalFlag;
   private InstanceVariableList instanceVariableList;
   private InstanceVariableList staticInstanceList;
   private MethodList publicMethodList, privateMethodList, publicStaticMethodList, privateStaticMethodList;
   
   // métodos públicos get e set para obter e iniciar as variáveis acima,
   // entre outros métodos
}
