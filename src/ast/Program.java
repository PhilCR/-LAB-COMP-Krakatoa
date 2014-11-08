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


	public void genC(PW pw) {
		pw.println("#include <malloc.h>");
		pw.println("#include <stdlib.h>");
		pw.println("#include <stdio.h>");
		pw.println("");
		pw.println("typedef int boolean;");
		pw.println("#define true 1");
		pw.println("#define false 0");
		pw.println("");
		pw.println("typedef");
		pw.add();
		pw.printlnIdent("void (*Func)();");
		pw.sub();
		
		
		Iterator<KraClass> kraIterator = classList.iterator();
		while(kraIterator.hasNext()){
			KraClass klass = kraIterator.next();
			pw.println("");
			klass.genC(pw);;
		}
		
		pw.println("");
		pw.println("int main() {");
		pw.add();
			pw.printlnIdent("_class_Program *program;");
			pw.printlnIdent("program = new_Program();");
			pw.printlnIdent("( ( void (*)(_class_Program *) ) program->vt[0] )(program);");
			pw.printlnIdent("return 0;");
		pw.sub();
		pw.println("}");
		
	}

	private ArrayList<KraClass> classList;
}