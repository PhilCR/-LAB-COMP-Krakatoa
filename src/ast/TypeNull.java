/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe C�sar Ramos 380415
 * */

package ast;

public class TypeNull extends Type{

	public TypeNull() {
        super("null");
    }
	
	@Override
	public String getCname() {
		return "null";
	}

}
