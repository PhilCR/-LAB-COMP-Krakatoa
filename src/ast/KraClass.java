/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */


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
      virtualTable = new MethodList();
   }
   
   public String getCname() {
	  return "_class_"+getName();
   }
   
   public String getName(){
	   return this.name;
   }
   
   //Procura instancias dentro da classe, como instancia static podem ter o mesmo nome de uma instancia nao static,
   // entao procura-se nas suas devidas listas
   public InstanceVariable searchInstance(String name, boolean staticFlag){
	   //se a instancia eh static, entao procure na static instance list
	   if(staticFlag){
		   return staticInstanceList.findElement(name);
	   // senao procure na instance variable list normal
	   }else{
		   return instanceVariableList.findElement(name);
	   }
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
   
   public void buildVirtualTable(){
	   KraClass superC = superclass;
	   while(superC != null){
		   Iterator<Method> sMethodIt = superC.getPublicMethodElements();
		   
		   while(sMethodIt.hasNext()){
			   Method aux = sMethodIt.next();
			   if(this.searchMethodOnlyNonStaticAndPublic(aux.getName()) == null){
				   if(this.findMethodInVirtualTable(aux.getName()) == null)
					   virtualTable.addElement(aux);
			   }
		   }
		   
		   superC = superC.getSuperClass();
	   }
	   
	   Iterator<Method> currentClassMethodIt = this.publicMethodList.elements();
	   while(currentClassMethodIt.hasNext()){
		   Method aux = currentClassMethodIt.next();
		   virtualTable.addElement(aux);
	   }
	   
   }
   
   public Method findMethodInVirtualTable(String name){
	   Iterator<Method> iterator = virtualTable.elements();
	   Method m = null;
	   while(iterator.hasNext()){
		   m = iterator.next();
		   if(m.getName().compareTo(name) == 0)
			   return m;
	   }
	   return null;
   }
   
   public Iterator<InstanceVariable> getInstancesFromSuperclass(){
	   InstanceVariableList superInstances = new InstanceVariableList();
	   
	   KraClass superC = superclass;
	   while(superC != null){
		   Iterator<InstanceVariable> superI = superC.getElementsFromInstList();
		   
		   while(superI.hasNext()){
			   InstanceVariable superInstance = superI.next();
			   
			   
			   if(this.searchInstance(superInstance.getName(), false) == null){
				   if(superInstances.findElement(superInstance.getName()) == null){
					   superInstances.addElement(superInstance); 
				   }
			   }
			   
			   
		   }
		   
		   superC = superC.getSuperClass();
	   }
	   
	   return superInstances.elements();
   }
   
   
   
   public Iterator<InstanceVariable> getElementsFromInstList(){
	   return this.instanceVariableList.elements();
   }
   
   public Iterator<Method> getPublicMethodElements(){
	   return this.publicMethodList.elements();
   }
   
   public int findAndGetIndexFromVT(String name){
	   for (int i = 0; i < virtualTable.getSize(); i++){
	        Method aux = virtualTable.getElement(i);
	        if (name.equals(aux.getName())){
	            return i;
	        }
	    } 

	    return -1;
   }
   
   public void genC(PW pw){
	   
	   
	   pw.println("typedef");
	   pw.add();
	   pw.printlnIdent("struct _St_"+name+"{");
	   pw.add();
	   pw.printlnIdent("Func *vt;");
	   
	   Iterator<InstanceVariable> instIt = getInstancesFromSuperclass();
	   while(instIt.hasNext()){
		   pw.printIdent("");
		   instIt.next().genC(pw, false);
		   pw.print(";");
		   pw.println(""); 
	   }
	   
	   instIt = instanceVariableList.elements();
	   while(instIt.hasNext()){
		   pw.printIdent("");
		   instIt.next().genC(pw, false);
		   pw.print(";");
		   pw.println("");
	   }
	   
	   pw.sub();
	   pw.printlnIdent("} "+getCname()+";");
	   pw.sub();
	   
	   pw.println("");
	   pw.println(getCname()+" *new_"+getName()+"(void);");
	   pw.println("");
	   
	   instIt = staticInstanceList.elements();
	   while(instIt.hasNext()){
		   pw.printIdent("");
		   instIt.next().genC(pw, true);
		   pw.print(";");
		   pw.println("");
	   }
	   pw.println("");
	   
	   
	   Method aux = null;
	   Iterator<Method> methodIt = publicMethodList.elements();
	   while(methodIt.hasNext()){
		   aux = methodIt.next();
		   pw.println("");
		   aux.genC(pw);
	   }
	   
	   methodIt = privateMethodList.elements();
	   while(methodIt.hasNext()){
		   aux = methodIt.next();
		   pw.println("");
		   aux.genC(pw);
	   }
	   
	   methodIt = publicStaticMethodList.elements();
	   while(methodIt.hasNext()){
		   aux = methodIt.next();
		   pw.println("");
		   aux.genC(pw);
	   }
	   
	   methodIt = privateStaticMethodList.elements();	
	   while(methodIt.hasNext()){
		   aux = methodIt.next();
		   pw.println("");
		   aux.genC(pw);
	   }
	   
	   pw.println("");
	   pw.println("Func VTclass_"+getName()+"[] = {");
	   pw.add();
	   
	   aux = null;
	   methodIt = virtualTable.elements();
	   while(methodIt.hasNext()){
		   aux = methodIt.next();
		   pw.printIdent("( void (*)() ) "+aux.getCname());
		   if(methodIt.hasNext())
			   pw.print(",");
		   pw.println("");
	   }
	   pw.sub();
	   pw.println("};");
	   
	   pw.println("");
	   pw.println(getCname()+" *new_"+getName()+"(){");
	   pw.add();
	   pw.printlnIdent(getCname()+" *t;");
	   pw.println("");
	   pw.printlnIdent("if( (t = malloc(sizeof("+getCname()+"))) != NULL )");
	   pw.add();
	   pw.printlnIdent("t->vt = VTclass_"+getName()+";");
	   pw.sub();
	   pw.printlnIdent("return t;");
	   pw.sub();
	   pw.println("}");
	   
   }
   
   private String name;
   private KraClass superclass;
   private boolean finalFlag;
   private InstanceVariableList instanceVariableList;
   private InstanceVariableList staticInstanceList;
   private MethodList publicMethodList, privateMethodList, publicStaticMethodList, privateStaticMethodList;
   
   private MethodList virtualTable;
   
   // métodos públicos get e set para obter e iniciar as variáveis acima,
   // entre outros métodos
}
