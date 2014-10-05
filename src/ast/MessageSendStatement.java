package ast;

public class MessageSendStatement extends Statement { 


   public void genK( PW pw ) {
      pw.printlnIdent("");
      messageSend.genK(pw, false);
      pw.print(";");
   }

   private MessageSend  messageSend;

}


