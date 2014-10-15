/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe C�sar Ramos 380415
 * */

package ast;

public class ObjectCreation extends Expr{
	
	
	private KraClass classObject;

	public ObjectCreation(KraClass object){
		classObject = object;
	}
	
	@Override
	public void genK(PW pw, boolean putParenthesis) {
		// TODO Auto-generated method stub
		pw.print("new "+classObject.getName()+"()");
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return classObject;
	}
}
