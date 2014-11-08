/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */


package ast;

import java.util.*;

public class InstanceVariableList {

    public InstanceVariableList() {
       instanceVariableList = new ArrayList<InstanceVariable>();
    }

    public void addElement(InstanceVariable instanceVariable) {
       instanceVariableList.add( instanceVariable );
    }

    public Iterator<InstanceVariable> elements() {
    	return this.instanceVariableList.iterator();
    }

    public int getSize() {
        return instanceVariableList.size();
    }

    public boolean isEmpty(){
    	return instanceVariableList.isEmpty();
    }
    
    public InstanceVariable findElement(String name){
    	Iterator<InstanceVariable> varIterator = elements();
    	while(varIterator.hasNext()){
 		   
 		   InstanceVariable var = varIterator.next();
 		   //Se achar uma instancia com o nome que procuro, retorne-a
 		   if(var.getName().compareTo(name) == 0){
 			   return var;
 		   }
 	   }
		return null;
    }
    
    private ArrayList<InstanceVariable> instanceVariableList;

}
