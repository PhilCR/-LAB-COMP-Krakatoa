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
