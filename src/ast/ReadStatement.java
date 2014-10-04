package ast;

public class ReadStatement extends Statement{
	public void genC(PW pw){
		
	}
	
	public ReadStatement(Variable r, boolean self, boolean staticFlag){
		readable = r;
		selfClass = self;
		this.staticFlag = staticFlag;
	}
	
	private Variable readable; 
	private boolean selfClass;
	private boolean staticFlag;
	
}
