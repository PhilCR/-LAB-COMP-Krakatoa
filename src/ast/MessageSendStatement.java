/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe C�sar Ramos 380415
 * */

package ast;

public class MessageSendStatement extends Statement { 


   public void genK( PW pw ) {
      pw.printlnIdent("");
      messageSend.genK(pw, false);
      pw.print(";");
   }

   private MessageSend  messageSend;

}


