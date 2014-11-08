/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe C�sar Ramos 380415
 * */


package ast;

import java.util.*;

public class ExprList {

    public ExprList() {
        exprList = new ArrayList<Expr>();
    }

    public void addElement( Expr expr ) {
        exprList.add(expr);
    }

    public void genC( PW pw ) {

        int size = exprList.size();
        for ( Expr e : exprList ) {
            e.genC(pw, false);
            if ( --size > 0 )
               pw.print(", ");
            }
        
    }
    
    public Iterator<Expr> elements(){
		return exprList.iterator();
    }
    
    public int getSize(){
    	return exprList.size();
    }
    
    private ArrayList<Expr> exprList;

}
