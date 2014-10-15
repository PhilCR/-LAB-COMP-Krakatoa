/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

import java.util.*;

public class Program {

	public Program(ArrayList<KraClass> classList) {
		this.classList = classList;
	}


	public void genK(PW pw) {
		Iterator<KraClass> kraIterator = classList.iterator();
		while(kraIterator.hasNext()){
			KraClass klass = kraIterator.next();
			pw.println("");
			klass.genK(pw);;
		}
	}

	private ArrayList<KraClass> classList;
}