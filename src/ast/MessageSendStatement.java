/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class MessageSendStatement extends Statement { 


   public void genC( PW pw ) {
      pw.printlnIdent("");
      messageSend.genC(pw, false);
      pw.print(";");
   }

   private MessageSend  messageSend;

}


