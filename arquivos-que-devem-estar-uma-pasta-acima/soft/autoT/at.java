import java.io.*;
import java.util.*;

public class at {

    public static final int MaxLinesOutput = 4;
    public static String ok[] = {
      "ok-chk01.kra", "Testa se a impressao do numero de vezes que cada metodo 'e chamado", "0",
      "ok-chk02.kra", "Teste se a producao das interfaces das classes esta correta. Um metodo", "0",
      "ok-chk03.kra", "Variavel de instancia 'e usada mas instanciada em atribuicao", "0",
      "ok-chk04.kra", "Variavel de instancia 'e usada mas instanciada em read", "0",
      "ok-chk05.kra", "uma classe 'e declarada mas nao usada. Mas uma de suas subclasses 'e.", "0",
      "ok-chk06.kra", "variaveis de tres classes sao declaradas. Mas nenhum objeto 'e criado.", "0",
      "ok-chk07.kra", "Este programa 'e usado para testar o 'refactoring'  'extract class'.", "0",
      "ok-ger01.kra", "Cada teste ok-ger*.kra possui uma instrucao write,", "0",
      "ok-ger02.kra", "Testa se o codigo para os operadores aritmeticos sao", "0",
      "ok-ger03.kra", "Este programa testa se o codigo para os operadores logicos", "0",
      "ok-ger04.kra", "Testa se codigo para while 'e gerado corretamente", "0",
      "ok-ger05.kra", "Testa se codigo para read e write 'e gerado", "0",
      "ok-ger06.kra", "Teste se codigo utilizando variaveis e expressoes 'e gerado", "0",
      "ok-ger07.kra", "Testa se o compilador considera o numero abaixo como zero", "0",
      "ok-ger08.kra", "Testa se a passagem de parametros e a chamada de metodos", "0",
      "ok-ger09.kra", "Testa se a geracao de codigo para this e super estao corretas.", "0",
      "ok-ger10.kra", "Teste se a geracao de codigo para variaveis de instancia esta", "0",
      "ok-ger11.kra", "Teste se a geracao de codigo para chamadas para self esta correta.", "0",
      "ok-ger12.kra", "Teste a geracao de codigo para super", "0",
      "ok-ger14.kra", "Teste se esta correta a geracao de codigo para variaveis de instancia", "0",
      "ok-ger15.kra", "Teste se esta correta a geracao de codigo para metodos privados.", "0",
      "ok-ger16.kra", "Teste se esta correta a geracao de codigo para heranca e polimorfismo", "0",
      "ok-ger17.kra", "testa geracao de codigo para metodo estatico", "0",
      "ok-ger18.kra", "testa geracao de codigo para metodos e variaveis estaticos", "0",
      "ok-ger19.kra", "testa geracao de codigo para metodos e variaveis estaticos", "0",
	  "ok-ger20.kra", "testa geracao de codigo para classes que referem-se a si mesmas", "0",
	  "ok-ger21.kra", "testa se o compilador gera c�digo correto se run n�o � o primeiro m�todo da classe", "0",
	  "ok-ger22.kra", "testa geracao de codigo para metodos e classes finais", "0",
	  
      "ok-lex02.kra", "Testa se nao aceita comentario aninhado como", "0",
      "ok-lex03.kra", "Testa varias alternativas de composicao de comentarios", "0",
      "ok-lex04.kra", "Testa limites validos de numeros inteiros", "0",
      "ok-lex05.kra", "Testa se o lexico considera os numeros abaixo como validos", "0",
      "ok-lex06.kra", "Testa se ^I ou  \t, carater de tabulacao, 'e considerado como", "0",
      "ok-lex07.kra", "Testa se o lexico conseguira encontrar uma palavra chave se ela", "0",
      "ok-lex08.kra", "Testa se o lexico considera validos identificadores com mais  de", "0",
      "ok-lex10.kra", "Testa se o lexico aceita tab's na primeira posicao do arquivo.", "0",
      "ok-sem03.kra", "testa se o compilador aceita comparacoes com == e != com", "0",
      "ok-sem04.kra", "testa se o compilador aceita comparacoes com == e != com", "0",
      "ok-sem05.kra", "Testa se o compilador aceita atribuicoes do tipo", "0",
      "ok-sem06.kra", "Testa se o compilador aceita variavel local e de instancia com mesmo", "0",
      "ok-sem07.kra", "breaks dentro de loops encaixados.", "0",
      "ok-sem08.kra", "Testa se o compilador aceita redefinicoes de metodos com tipos dos", "0",
      "ok-sem09.kra", "Testa se o compilador aceita this como expressao valida", "0",
      "ok-sem10.kra", "Testa se o compilador aceita envios de mensagens a metodos", "0",
      "ok-sem11.kra", "Testa se o compilador aceita metodo com mesmo nome", "0",
      "ok-sem12.kra", "Testa se o compilador aceita variavel com nome de classe", "0",
      "ok-sem13.kra", "Testa se o compilador aceita variavel local com mesmo", "0",
      "ok-sem14.kra", "Testa se o compilador aceita atribuicao a null e comparacoes", "0",
      "ok-sem15.kra", "Testa se o compilador aceita this onde se espera objeto de superclasse", "0",
      "ok-sem17.kra", "testa aceitacao de metodos e variaveis estaticos", "0",
      "ok-sem18.kra", "testa se o compilador aceita metodos estaticos com mesmo nome em classe e subclasse", "0",
	  "ok-sem19.kra", "testa opera��es usando vari�veis est�ticas", "0",
	  "ok-sem20.kra", "testa m�todos finais", "0",
	  
      "ok-sin02.kra", "Testa se o  analisador sintatico manipula corretamente as regras", "0",
      "ok-sin03.kra", "Testa expressoes aritmeticas e logicas, menos unario, &&, || e", "0",
      "ok-sin04.kra", "Confere se o sintatico consegue manipular comandos profundamente", "0",
      "ok-sin05.kra", "O analisador sintatico deve manipular um numero de declaracoes de variaveis", "0",
      "ok-sin06.kra", "Testa se o sintatico aceita algumas estruturas como expressoes", "0",
      "ok-sin07.kra", "Testa:", "0",
      "ok-sin08.kra", "Testa chamada de metodos ", "0",
      "ok-sin09.kra", "Testa :", "0",
      "ok-sin10.kra", "Testa escrita com super e this (variaveis de instancia e metodos).", "0",
      "ok-sin11.kra", "Testa se o compilador aceita ; como comando", "0",
      "ok-sin12.kra", "testa se aceita - antes de variavel", "0",
      "ok-sin13.kra", "testa se aceita - unario em uma sequencia de somas", "0",
      "ok-sin14.kra", "testa se aceita envio de mensagens a variaveis de instancia,", "0",
      "ok-sin15.kra", "tests Strings", "0"
    };
    
    public static String er[] = {
      "er-lex02.kra", "Testa se \\ nao 'e aceito como valido", "10",
      "er-lex03.kra", "Testa se o lexico considera o numero -32768 como incorreto.", "12",
      "er-lex04.kra", "Testa se o lexico sinaliza erro no numero 32768", "10",
      "er-lex05.kra", "Teste para conferir se o lexico sinaliza o numero correto da linha", "16",
      "er-lex06.kra", "Testa se o lexico nao permite sublinhado como primeiro carater", "10",
      "er-lex07.kra", "Testa se . 'e considerado ilegal", "10",
      "er-lex08.kra", "O compilador devera sinalizar 'comentario nao fechado'", "1",
      "er-lex09.kra", "O compilador devera sinalizar 'Numero fora dos limites'", "10",
      "er-lex11.kra", "Palavras chaves nao podem estar em maiuscula.", "10",
      "er-sem01.kra", "metodo com tipo de retorno deve ter pelo menos um comando return", "17",
      "er-sem02.kra", "Caracteres minusculos e maiusculos sao diferentes", "12",
      "er-sem03.kra", "Redeclaracao de i em duas declaracoes em sequencia.", "11",
      "er-sem04.kra", "Erro de tipos", "12",
      "er-sem05.kra", "Erro de tipos", "12",
      "er-sem06.kra", "Instrucao esperada, atribuicao a valor", "10",
      "er-sem07.kra", "Variavel inteira sendo utilizada como se fosse objeto", "14",
      "er-sem08.kra", "Uso de operador aritmetico com valores booleanos", "10",
      "er-sem09.kra", "Uso de operadores logicos com inteiros", "12",
      "er-sem11.kra", "Expressao do while deve ser booleana", "11",
      "er-sem12.kra", "Valor booleano em expressao inteira", "10",
      "er-sem13.kra", "Variaveis booleanas nao podem ser lidas.", "12",
      "er-sem14.kra", "Expressoes booleanas nao podem ser escritas.", "13",
      "er-sem15.kra", "! aplicado a inteiro.", "12",
      "er-sem16.kra", "Menos unario aplicado a booleano", "10",
      "er-sem17.kra", "Parametros de read devem ser variaveis.", "10",
      "er-sem18.kra", "Tipo 'e uma variavel", "11",
      "er-sem19.kra", "Tipo invalido", "10",
      "er-sem20.kra", "Palavra reservada utilizada como variavel", "10",
      "er-sem21.kra", "Palavra reservada utilizada como variavel", "10",
      "er-sem22.kra", "Palavra reservada utilizada como variavel", "10",
      "er-sem23.kra", "Palavra reservada utilizada como variavel", "10",
      "er-sem24.kra", "Palavra reservada utilizada como variavel", "10",
      "er-sem25.kra", "Palavra reservada utilizada como variavel", "10",
      "er-sem26.kra", "O compilador devera sinalizar erro 'break fora de while'", "10",
      "er-sem27.kra", "classe A herda de si mesma", "7",
      "er-sem28.kra", "Variavel i declarada duas vezes, mas nao em sequencia", "11",
      "er-sem29.kra", "put redefinida em B com parametros diferentes daqueles da superclasse", "18",
      "er-sem30.kra", "Metodo put em B possui valor de retorno. Na superclasse A, nao.", "18",
      "er-sem31.kra", "Metodo com nome igual a de variavel de instancia", "11",
      "er-sem32.kra", "Metodo publico e privado com mesmo nome", "12",
      "er-sem33.kra", "Dois metodos publicos com mesmo nome", "12",
      "er-sem34.kra", "Chamada  'a.m()' eh uma expressao e esta sendo utilizada como uma instrucao", "21",
      "er-sem35.kra", "Metodo que nao deve retornar nada com instrucao return", "10",
      "er-sem36.kra", "Instrucao 'i = a.m()' admite que m retorna algum valor", "22",
      "er-sem37.kra", "Metodo set nao pertence 'a classe A", "21",
      "er-sem38.kra", "A instrucao 'b = a' esta errada. Variaveis de subclasses nao podem receber objetos de superclasses.", "22",
      "er-sem39.kra", "Erro de tipos. A instrucao 'return A.new()' esta errada pois o tipo do valor de", "20",
      "er-sem40.kra", "Erro de tipos. A instrucao 'this.m(a)' esta errada: um objeto de uma superclasse", "42",
      "er-sem41.kra", "Objeto nao pode receber valores de tipos basicos", "16",
      "er-sem42.kra", "Variavel de tipo basico nao pode receber objetos", "16",
      "er-sem43.kra", "Variavel de tipo basico nao pode receber null", "12",
      "er-sem44.kra", "Nao se pode escrever objetos", "28",
      "er-sem45.kra", "Nao se pode ler objetos", "16",
      "er-sem46.kra", "super 'e utilizado somente quando ha superclasse", "10",
      "er-sem47.kra", "Chamada a metodo inexistente utilizando 'super'", "26",
      "er-sem48.kra", "Os nomes dos tipos basicos sao reservados e nao podem ser nomes de classes", "7",
      "er-sem49.kra", "Tipos basicos nao podem ser herdados, pois nao sao classes", "11",
      "er-sem50.kra", "O compilador devera sinalizar erro 'break fora de loop-end'", "21",
      "er-sem51.kra", "put redefinida em B com parametros diferentes daqueles da superclasse A", "19",
      "er-sem57.kra", "Testa se o compilador sinalize erro nas comparacoes com == ", "23",
      "er-sem58.kra", "Testa se o compilador sinalize erro nas comparacoes com != ", "23",
      "er-sem59.kra", "Chamada a metodo privado", "26",
      "er-sem60.kra", "Chamada a metodo privado da superclasse", "22",
      "er-sem61.kra", "Chamada a metodo inexistente", "34",
      "er-sem62.kra", "uso de variavel de instancia sem this", "17",
      "er-sem63.kra", "chamada incorreta a m�todo est�tico", "15",
      "er-sem64.kra", "chamada incorreta a m�todo est�tico", "15",
      "er-sem65.kra", "chamada incorreta a m�todo est�tico", "12",
      "er-sem66.kra", "chamada a m�todo n�o existente", "12",
      "er-sem67.kra", "chamada a m�todo n�o existente", "15",
      "er-sem68.kra", "chamada a m�todo n�o existente", "15",
      "er-sem69.kra", "chamada incorreta a m�todo est�tico", "15",
      "er-sem70.kra", "Metodo publico e privado com mesmo nome", "12",
      "er-sem71.kra", "acesso a variavel de instancia em metodo publico", "14",
      "er-sem72.kra", "chamada de metodo instancia publico em metodo estatico", "15",
      "er-sem73.kra", "dois metodos estaticos com mesmo nome", "14",
      "er-sem74.kra", "chamada ilegal a metodo estatico", "15",
      "er-sem75.kra", "chamada a metodo estatico privado", "17",
      "er-sem76.kra", "chamada a metodo estatico atraves de variavel", "19",
      "er-sem77.kra", "M�todo run ausent", "13",
      "er-sem78.kra", "classe Program ausente", "13",
      "er-sem79.kra", "M�todo run com par�metros", "10",
      "er-sem80.kra", "M�todo run deve retornar 'void'", "10",
      "er-sem81.kra", "M�todo run deve ser publico", "10",
      "er-sem82.kra", "M�todo run nao pode ser estatico", "10",
      "er-sem83.kra", "Classe final sendo herdada", "12",
	  "er-sem84.kra", "M�todo final sendo redeclarado", "14",
      "er-sem85.kra", "M�todo final em classe final", "8",
	  
      "er-sin01.kra", "Declaracoes de variaveis locais devem ter aparecer depois de {.", "10",
      "er-sin02.kra", "Depois de , deve vir um identificador", "12",
      "er-sin03.kra", "Falta o identificador", "12",
      "er-sin04.kra", "Falta o tipo da variavel", "12",
      "er-sin05.kra", ", seguida de ) em read", "12",
      "er-sin06.kra", ", seguida de ) em write", "13",
      "er-sin07.kra", "read sem argumentos", "12",
      "er-sin08.kra", "write sem argumentos", "12",
      "er-sin09.kra", "operadores de comparacao nao podem ser usados em sequencia", "12",
      "er-sin10.kra", "Falta ( depois de read", "13",
      "er-sin11.kra", "Falta ( depois de write", "12",
      "er-sin12.kra", "{-} usado como expressao", "13",
      "er-sin13.kra", "Sem  corpo do metodo", "9",
      "er-sin14.kra", "Classe esperada. Embora qualquer texto apos a classe Programa 'e inutil, o compilador", "18",
      "er-sin15.kra", "Nao pode existir comentario no meio de um numero", "11",
      "er-sin16.kra", "tipo usado como variavel", "11",
      "er-sin17.kra", "Falta o ; na declaracao de i", "10",
      "er-sin18.kra", "Falta o ;  apos atribuicao", "13",
      "er-sin19.kra", "Falta o ; apos read", "13",
      "er-sin20.kra", "Falta o ; apos write", "10",
      "er-sin21.kra", "Falta o ;  apos unico comando do while", "14",
      "er-sin22.kra", "Falta o ;  apos unico comando do if", "15",
      "er-sin23.kra", "else sem if", "12",
      "er-sin24.kra", "Testa se << nao 'e aceito como valido", "12",
      "er-sin25.kra", "Testa se >> nao 'e aceito como valido", "11",
      "er-sin26.kra", "Testa se => nao 'e aceito como valido", "12",
      "er-sin27.kra", "Testa se =< nao 'e aceito como valido", "11",
      "er-sin28.kra", "Palavras chaves nao podem estar em maiuscula.", "7",
      "er-sin29.kra", "classe sem corpo", "10",
      "er-sin30.kra", "carater # nao pertence a linguagem", "11",
      "er-sin31.kra", "; excedente", "10",
      "er-sin32.kra", ", excedente", "9",
      "er-sin33.kra", "Tipo ausente na declaracao de x", "10",
      "er-sin34.kra", "; excedente na declaracao de y", "10",
      "er-sin35.kra", ", ausente na declaracao de m", "10",
      "er-sin36.kra", "Tipo de retorno ausente na declaracao de m", "10",
      "er-sin37.kra", "expressao do if vazia", "11",
      "er-sin38.kra", "} do fim do while ausente", "16",
      "er-sin39.kra", "; depois do nome do metodo", "9",
      "er-sin40.kra", "Declaracao de variavel de instancia na parte publica. Este erro poderia", "11",
      "er-sin41.kra", "nome da classe ausente", "7",
      "er-sin43.kra", "testa se aceita declaracoes sem public/private", "10",
      "er-sin44.kra", "testa se aceita declaracoes sem public/private", "10",
      "er-sin51.kra", "erro sintaxe na declaracao de metodo est�tico", "11",
      "er-sin52.kra", "erro sintaxe na declaracao de metodo est�tico", "11",
      "er-sin53.kra", "erro sintaxe na declaracao de vari�vel est�tica", "11",
      "er-sin54.kra", "erro sintaxe na declaracao de vari�vel est�tica", "11",
      "er-sin55.kra", "erro sintaxe na declaracao de vari�vel est�tica", "11",
      "er-sin56.kra", "erro sintaxe na declaracao de metodo est�tico", "11"
    };

    private static String criticalWrongOk[], criticalWrongEr[];
    private static int wrongOk, wrongEr;

    private static Hashtable criticalTests;
    static {
       criticalTests = new Hashtable();
       criticalTests.put("ok-sem05.txt", "");
       criticalTests.put("ok-sem10.txt", "");
       criticalTests.put("ok-sem11.txt", "");
       criticalTests.put("er-sem73.txt", "");
       criticalTests.put("er-sem61.txt", "");
       criticalTests.put("er-sem63.txt", "");
       criticalTests.put("er-sem47.txt", "");
       criticalTests.put("er-sem40.txt", "");
       criticalTests.put("er-sem39.txt", "");
       criticalTests.put("er-sem38.txt", "");
       criticalTests.put("er-sem37.txt", "");
       criticalTests.put("er-sem19.txt", "");
       
       criticalWrongOk = new String[ok.length];
       criticalWrongEr = new String[er.length];
       wrongOk = wrongEr = 0;

    }
    
    private static void testCriticalOk(String name) {
       if ( criticalTests.get(name) != null ) 
          criticalWrongOk[wrongOk++] = name;
    }
    private static void testCriticalEr(String name) {
       if ( criticalTests.get(name) != null ) 
          criticalWrongEr[wrongEr++] = name;
    }
    
    public static void main(String []args) {
    
        if ( args.length >= 1 ) {
            System.out.println("Usage:\ntt outputFileName");
            return;
        }
        String outputFileName = ( args.length < 1 ) ?  
            "r.txt" : args[0];
            
        FileOutputStream fos = null;
        PrintWriter out;
        try {
           fos = new FileOutputStream(outputFileName);
           out = new PrintWriter(fos);
        } catch ( Exception e ) { 
            System.out.println("Error: could not create output file");
            return ; 
       }
       
       out.println("Este cabe�alho explica o formato em que est� este relat�rio de erros");
       out.println("");
       out.println("Cada linha corresponde a um arquivo de teste. Citaremos tr�s exemplos:");
       out.println("Primeiro exemplo");
       out.println("     ok-ger01 **(0/11) ");
       out.println("Significa que o seu compilador sinalizou erro na linha 11 mas ");
       out.println("n�o deveria ter sinalizado erro (0)");
       out.println();
       out.println("Segundo exemplo");
       out.println("     er-sem40   (42/42) [ : Wrong type ] (Erro de tipos. A instrucao 'this.m(a)' esta errada: um objeto de uma superclasse)");
       out.println("Significa que o seu compilador apontou o erro na linha 42, a linha correta, ");
       out.println("com a mensagem \" Wrong type \". A mensagem esperada era \"Erro de tipos\"");
       out.println();
       out.println("Terceiro exemplo");
       out.println("     er-sem41 **(16/12)");
       out.println("Significa que o seu compilador apontou o erro na linha 12 e o erro");
       out.println("estava na linha 16");
       out.println();
       out.println("Fim dos exemplos");
                   
       
       out.println();
       out.println("Foram considerados 'especiais' tr�s arquivos ok's e ");
       out.println("nove er's, cujas confer�ncias s�o mais dif�ceis de se fazer.");
       out.println("Estes arquivos s�o chamados 'critical ok' e 'critical er'.");
       out.println("Veja no fim deste texto um relatorio com os erros criticos que o seu compilador");
       out.println("deixou de apontar e com o total de erros ok's e er's");
                   
       out.println("----------  fim do cabe�alho -------------");
       out.println();
       out.println();
       
       int i, numOkErrors, numErErrors;
       renameAllTestFiles();
       for (i = 0; i < ok.length; i += 3) {
          ok[i] = ok[i].toLowerCase();
          int k = ok[i].indexOf('.');
          if ( k < 1 ) {
             System.out.println("Error in 'ok' vector: " + ok[i]);
             System.exit(1);
          }
          ok[i] = ok[i].substring(0, k);
       }
       for (i = 0; i < er.length; i += 3) {
          er[i] = er[i].toLowerCase();
          int k = er[i].indexOf('.');
          if ( k < 1 ) {
             System.out.println("Error in 'er' vector(" + i + "): " + er[i] );
             System.exit(1);
          }
          er[i] = er[i].substring(0, k);
       }

       numOkErrors = numErErrors = 0; 
       for (i = 0; i < ok.length; i += 3) {
           String name = ok[i] + ".txt";
           File f = new File(name);
           out.print(ok[i] + " ");
           
           if ( ! f.exists() ) {
              out.print("** Arquivo " + name + " n�o existe");
              numOkErrors++;
              testCriticalOk(name);
           }
           else {
              String outputString[] = readFile(f, out);
              if ( outputString == null || outputString[0] == null ) {
                 out.print("** Arquivo " + name + " vazio");
                 numOkErrors++;
                 testCriticalOk(name);
              }
              else if ( outputString[0].charAt(0) != '0' ) {
                 int numLinObtida = getNumLineWithError(outputString[0]);

                 if ( numLinObtida != 0 && outputString[0].indexOf(':') <= 0 ) { 
                    out.print("** Arquivo " + name + " danificado");
                    numOkErrors++;
                    testCriticalOk(name);
                 }
                 else {
                    out.print("**(0/" + numLinObtida + ")    (" + ok[i+1] + ")");
                    numOkErrors++;
                    testCriticalOk(name);
                 }
                 
              }
           }
           out.println();
       }
   
       
       for (i = 0; i < er.length; i += 3) {
          int numLinEsp = Integer.parseInt(er[i+2]);
          String message = er[i+1];
          String name = er[i] + ".txt";
          File f = new File(name);
          out.print(er[i] + " ");
          if ( ! f.exists() ) {
             out.print("** Arquivo " + name + " n�o existe");
             numErErrors++;
             testCriticalEr(name);
          }
          else {
             String outputString[] = readFile(f, out);
             if ( outputString == null || outputString[0] == null ) {
                out.print("** Arquivo " + name + " vazio");
                numErErrors++;
              testCriticalEr(name);
             }
             else {
                int numLinObtida = getNumLineWithError(outputString[0]);
                int n = 0;
                if ( numLinObtida != 0 && (n = outputString[0].indexOf(':')) <= 0 ) { 
                    out.print("** Arquivo " + name + " danificado");
                    numErErrors++;
                    testCriticalEr(name);
                }
                else {
                    if (  numLinEsp == numLinObtida ||
                          numLinEsp == numLinObtida - 1) {
                       //System.out.println(name + " - ");
                       out.print("  (" + numLinEsp + "/" + numLinObtida + ") " +
                         "[ " + outputString[0].substring(n) + " ] (" + er[i+1] + ")");
                       if ( out.checkError() )
                          System.out.println("An error occured in output");
                       //out.print("Obtido  : ");
                       //out.println(outputString[0].substring(n));
                    }
                    else {
                       out.print("**(" + numLinEsp + "/" + numLinObtida + ")    (" + er[i+1] + ")");
                       /*out.println("** Num. Linha c/erro incorreta em " +
                          er[i] + ".kra: esperado " + numLinEsp +
                          " obtido " + numLinObtida); */
                       numErErrors++;
                       testCriticalEr(name);
                    }
                }
             }
          }
       out.println();
          
       }
       int k;

       out.println();
       if ( wrongOk > 0 ) {
          out.println("Wrong critical ok: ");
          for( k = 0; k < wrongOk; k++ )
             out.print(criticalWrongOk[k] + "  ");
          out.println();
       }
       if ( wrongEr > 0 ) {
          out.println("Wrong critical er: ");
          for( k = 0; k < wrongEr; k++ )
             out.print(criticalWrongEr[k] + "  ");
          out.println();
       }
       
       out.println();
       out.println(numOkErrors + "/" + ok.length/3 + " erros nos arquivos ok-*.s");
       out.println(numErErrors + "/" + er.length/3 + " erros nos arquivos er-*.s");
       out.println((wrongEr + wrongOk) + "/" + criticalTests.size() + " erros criticos");
       out.println();
       out.println();

       out.println("Para uso do professor:");

       out.print(numOkErrors + "    " + numErErrors
          + "    " + (wrongEr + wrongOk) + "    " + (237*numOkErrors + numErErrors + 411) +
          "    " + (numOkErrors + 513*numErErrors + 945) + 
          "    " + (175*(wrongEr + wrongOk) + numErErrors +127) );
       out.print("    ");
       if ( wrongOk > 0 ) {
          for( k = 0; k < wrongOk; k++ )
             out.print(criticalWrongOk[k] + "  ");
       }
       if ( wrongEr > 0 ) {
          for( k = 0; k < wrongEr; k++ )
             out.print(criticalWrongEr[k] + "  ");
       }
       
       out.println();
       
       out.close();
       try {
          fos.close();
       } catch (IOException e) {
          e.printStackTrace();
       }       
    }

    private static void print(String v[], PrintWriter out) {
       int k = 0; 
       while ( k < v.length && v[k] != null ) {
          out.println(v[k]);
          k++;
       }
    }
    private static String []readFile( File f, PrintWriter out ) {
       
       String outputString[] = new String[MaxLinesOutput];
       BufferedReader br = null;
       try {
          br = new BufferedReader( new FileReader(f) );
       } catch (Exception e ) {
          out.println("Read error in file " + f.getName());
          return null;
       }
       boolean first = true;
       int j = 0;
       while ( j < MaxLinesOutput ) {
          outputString[j] = null;
          try {
             outputString[j] = br.readLine();
          } catch( Exception e ) {
             out.println("Read error in file " + f.getName());
             return null;
          }
          if ( outputString[j] == null ) {
             break;
          }
          j++;
       }
       try { 
          br.close();
       } catch( Exception e ) {
          out.println("Error closing file " + f.getName());
       }
       return outputString;
    }
    
    
    public static void renameAllTestFiles() {
        File f = new File(".");
        String []files = f.list();
        for (int i = 0; i < files.length; i++) {
           String fileName = files[i];
           String originalFileName = fileName;
           fileName = fileName.toLowerCase();
           boolean startWithEr = fileName.startsWith("er-");
           boolean startWithOk = fileName.startsWith("ok-");
           
           if ( fileName.endsWith(".txt") && (startWithEr || startWithOk) ) {
           
              File ft = new File(originalFileName);
              File newF = new File(fileName);
              if ( ! ft.renameTo(newF) ) {
                  System.out.println("Fails to rename " + fileName);
                  System.exit(1);
              }
           }
        }
    }
    
   private static int getNumLineWithError(String s) {
       int i = 0, n = 0;
       if ( s.charAt(i) == '0' ) {
          i++;
          while ( i < s.length() && s.charAt(i) != '\0' && s.charAt(i) != '\n' && s.charAt(i) != '\r') 
             if ( s.charAt(i) != ' ' )
                return -1;
             else
                i++;
          return 0;
       }
       else {
          if ( ! Character.isDigit(s.charAt(i)) ) {
             if ( Character.isWhitespace(s.charAt(i)) || 
                  i >= s.length() ||
                  s.charAt(i) == '\0' || s.charAt(i) == '\n' ||
                  s.charAt(i) == '\r' )
                return -2;
             else
                return -1;
          }
          else
             while ( Character.isDigit(s.charAt(i)) ) {
                n = 10*n + (s.charAt(i) - '0');
                i++;
             }
          return n;
       }
   }
    
}



