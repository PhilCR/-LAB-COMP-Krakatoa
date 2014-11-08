/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

import java.util.ArrayList;
import java.util.Iterator;

public class MethodList {
	public MethodList(){
		methodList = new ArrayList<Method>();
	}
	
	public void addElement(Method method){
		methodList.add(method);
	}
	
	public Iterator<Method> elements(){
		return this.methodList.iterator();
	}
	
	public int getSize(){
		return methodList.size();
	}
	
	public Method getElement(int i){
		return methodList.get(i);
	}
	
	public Method returnLast(){
		return methodList.get(methodList.size()-1);
	}
	
	private ArrayList<Method> methodList;
}
