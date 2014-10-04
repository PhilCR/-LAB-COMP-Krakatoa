
package comp;

import ast.*;
import lexer.*;
import java.io.*;
import java.util.*;

public class Compiler {

	// compile must receive an input with an character less than
	// p_input.lenght
	public Program compile(char[] input, PrintWriter outError) {

		error = new CompilerError(new PrintWriter(outError));
		symbolTable = new SymbolTable();
		lexer = new Lexer(input, error);
		error.setLexer(lexer);
		insideWhile = 0;
		
		Program program = null;
		try {
			lexer.nextToken();
			if ( lexer.token == Symbol.EOF )
				error.show("Unexpected end of file");
			program = program();
			if ( lexer.token != Symbol.EOF ) {
				program = null;
				error.show("End of file expected");
			}
		}
		catch (Exception e) {
			// the below statement prints the stack of called methods.
			// of course, it should be removed if the compiler were
			// a production compiler.

			e.printStackTrace();
			program = null;
		}

		return program;
	}

	private Program program() {
		// Program ::= KraClass { KraClass }
		ArrayList<KraClass> kList = new ArrayList<KraClass>();
		kList.add(classDec());
		while (lexer.token == Symbol.CLASS || lexer.token == Symbol.FINAL)
			kList.add(classDec());
		
		boolean foundProgram = false;
		for(KraClass k : kList){
			if(k.getCname().compareTo("Program") == 0){
				foundProgram = true;
			}
		}
		if(!foundProgram)
			error.show("Couldn't find class Program.");
		return new Program(new ArrayList<KraClass>());
	}

	private KraClass classDec() {
		// Note que os métodos desta classe não correspondem exatamente às
		// regras
		// da gramática. Este método classDec, por exemplo, implementa
		// a produção KraClass (veja abaixo) e partes de outras produções.

		/*
		 * KraClass ::= ``class'' Id [ ``extends'' Id ] "{" MemberList "}"
		 * MemberList ::= { Qualifier Member } 
		 * Member ::= InstVarDec | MethodDec
		 * InstVarDec ::= Type IdList ";" 
		 * MethodDec ::= Qualifier Type Id "("[ FormalParamDec ] ")" "{" StatementList "}" 
		 * Qualifier ::= [ "static" ]  ( "private" | "public" )
		 */
		boolean finalFlag = false;
		boolean programClass = false;
		if(lexer.token == Symbol.FINAL){
			finalFlag = true;
			lexer.nextToken();
		}
		if ( lexer.token != Symbol.CLASS ) error.show("'class' expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.IDENT )
			error.show(CompilerError.ident_expected);
		String className = lexer.getStringValue();
		symbolTable.putInGlobal(className, new KraClass(className, finalFlag));
		// Pegando referencia de classe do hashmap pra poder modificar como necessario
		KraClass ref = symbolTable.getInGlobal(className);
		currentClass = ref; 
		lexer.nextToken();
		if ( lexer.token == Symbol.EXTENDS ) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				error.show(CompilerError.ident_expected);
			String superclassName = lexer.getStringValue();
			if(superclassName.compareTo(className) == 0){
				error.show("Trying to extend class from itself.");
			}
			//procurando a super classe caso a mesma exista 
			KraClass superclass = symbolTable.getInGlobal(superclassName);
			if(superclass == null)
				error.show("Cannot extend from Super class "+superclassName+", the class doesn't exist");
			if(superclass.isFinal())
				error.show("The class "+className+" is trying to extend from a class that is final.");
			//se a classe nao eh encontrada na symbol table entao sinalizar erro
			
			//se tudo der certo, entao setar superclasse da KraClass =)
			
			ref.setSuperClass(superclass);
			lexer.nextToken();
		}
		if ( lexer.token != Symbol.LEFTCURBRACKET )
			error.show("{ expected", true);
		lexer.nextToken();
		
		while (lexer.token == Symbol.PRIVATE || lexer.token == Symbol.PUBLIC  || lexer.token == Symbol.STATIC || lexer.token == Symbol.FINAL) {
			
			Symbol qualifier;
			//adicionando flag para verificar se houve declaracao disso como static
			boolean staticFlag;
			boolean finalMethodFlag;
			
			if(lexer.token == Symbol.FINAL){
				lexer.nextToken();
				finalMethodFlag = true;
			}else{
				finalMethodFlag = false;
			}
			if(finalMethodFlag == true && finalFlag == true){
				error.show("A final class can't have final methods.");
			}
			//se o token for static, vai pro proximo, e defina flag como true, senao volta pra falso
			if(lexer.token == Symbol.STATIC){
				lexer.nextToken();
				staticFlag = true;
			}else{
				staticFlag = false;
			}
			switch (lexer.token) {
			case PRIVATE:
				lexer.nextToken();
				qualifier = Symbol.PRIVATE;
				break;
			case PUBLIC:
				lexer.nextToken();
				qualifier = Symbol.PUBLIC;
				break;
			default:
				error.show("private, or public expected");
				qualifier = Symbol.PUBLIC;
			}
			Type t = type();
			if ( lexer.token != Symbol.IDENT )
				error.show("Identifier expected");
			String name = lexer.getStringValue();
			Method m = null;
			lexer.nextToken();
			if ( lexer.token == Symbol.LEFTPAR ){
				if(name.compareTo("run") == 0 && currentClass.getName().compareTo("Program") == 0){
					if(t.getName().compareTo("void") != 0)
						error.show("Method run in class Program can't return void.");
					if(staticFlag)
						error.show("Method run in class Program can't be static.");
					if(qualifier == Symbol.PRIVATE)
						error.show("Method run in class Program can't be private.");
				}
				Variable aux = currentClass.searchInstance(name, false);
				if(aux != null){
					error.show("Method "+name+" has the same name as instance.");
				}
				if(staticFlag){
					m = currentClass.searchMethodOnlyStatic(name);
				}else{
					m = currentClass.searchMethodOnlyNonStatic(name);
				}
				if(m != null){
					if(staticFlag)
						error.show("Redeclaration of "+ qualifier +" static method "+name);
					error.show("Redeclaration of "+ qualifier +" method "+name);
				}
				if(qualifier == Symbol.PRIVATE){
					if(finalFlag == true)
						error.show("It is not possible to declare a final private method inside a class.");
					m = new Method(t, name, true, staticFlag, finalMethodFlag);
					currentMethod = ref.addMethod(m, staticFlag, true);
				}else{
					m = new Method(t, name, false, staticFlag, finalMethodFlag);
					currentMethod = ref.addMethod(m, staticFlag, false);
				}
				hasReturn = false;
				methodDec(t, name);
			}else if ( qualifier != Symbol.PRIVATE){
				error.show("Attempt to declare a public instance variable");
			}else{
				//Adicionando criacao de instanceVarDec como objeto e adicionando a classe em questao
				InstanceVariableList vList = instanceVarDec(t, name, staticFlag);
				//Manda static flag pra ver se entra na lista de static ou nao
				ref.addElements(vList, staticFlag);
			}
		}
		
		if ( lexer.token != Symbol.RIGHTCURBRACKET )
			error.show("public/private or \"}\" expected");
		if(currentClass.getName().compareTo("Program") == 0){
			if(currentClass.searchMethod("run") == null){
				error.show("Class Program needs to have a method run().");
			}
		}
		lexer.nextToken();
		return ref;
	}

	//Modifiquei instanceVarDec para aceitar o nome da classe e a flag dizendo se eh static ou nao
	private InstanceVariableList instanceVarDec(Type type, String name, boolean staticFlag) {
		// InstVarDec ::= [ "static" ] "private" Type IdList ";"
		//cria nova lista de instancias
		InstanceVariableList varList = new InstanceVariableList();
		//Procura instancia sendo ela static ou nao dentro da lista de instancias da classe, se nao achar entao adicione
		// a instancia a lista
		if((currentClass.searchInstance(name, staticFlag)) == null)
			varList.addElement(new InstanceVariable(name, type));
		else{
			//senao mostre erro apropriado se ja existe
			if(staticFlag)
				error.show("Duplicate declaration for static instance "+name);
			else
				error.show("Duplicate declaration for instance "+name);
		}
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				error.show("Identifier expected");
			lexer.nextToken();
			String variableName = lexer.getStringValue();
			//Procura as seguintes declaracoes nas listas correspondentes novamente e adiciona, assim como antes
			if((currentClass.searchInstance(variableName, staticFlag)) == null)
				varList.addElement(new InstanceVariable(variableName, type));
			else{
				if(staticFlag)
					error.show("Duplicate declaration for static instance "+name);
				else
					error.show("Duplicate declaration for instance "+name);
			}
		}
		if ( lexer.token != Symbol.SEMICOLON )
			error.show(CompilerError.semicolon_expected);
		
		lexer.nextToken();
		return varList;
	}

	private void methodDec(Type type, String name) {
		/*
		 * MethodDec ::= Qualifier Type Id "("[ FormalParamDec ] ")" "{"
		 *                StatementList "}"
		 */
		
		ParamList formalParamList = null;
		StatementList sList = null;
		lexer.nextToken();
		if ( lexer.token != Symbol.RIGHTPAR ) formalParamList = formalParamDec();
		if ( lexer.token != Symbol.RIGHTPAR ) error.show(") expected");
		
		if(currentMethod.getName().compareTo("run") == 0 && currentClass.getName().compareTo("Program") == 0){
			if(formalParamList != null)
				error.show("Class Program cannot have method run with parameters.");
		}
		Method superMethod = findMethodInSuperClass();
		if(superMethod != null){
			if(superMethod.isFinal())
				error.show("Trying to redefine method that is final in superclass.");
			boolean checkParameters;
			if(superMethod.getType().getName().compareTo(type.getName()) != 0)
				error.show("Redefinition of method from superclass has a different return type.");
			checkParameters = compareParamLists(formalParamList, superMethod.getParamList());
			if(!checkParameters){
				error.show("Different declaration of parameters of method "+name+" compared to its definition in the superclass of "+currentClass.getName());
			}
		}
		
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTCURBRACKET ) error.show("{ expected");

		lexer.nextToken();
		sList = statementList();
		if(currentMethod.getType().getName().compareTo("void") == 0){
			if(hasReturn){
				error.show("Method "+currentMethod.getName()+" shouldnt have a return statement of any type.");
			}
		}else{
			if(!hasReturn)
				error.show("Method "+currentMethod.getName()+" should have at least one return statement.");
		}
		if ( lexer.token != Symbol.RIGHTCURBRACKET ) error.show("} expected");
		//ao fim da analise de metodo, pra nao me esquecer, limpe a localTable
		lexer.nextToken();
		symbolTable.removeLocalIdent();
		if(sList != null)
			currentMethod.setStatementList(sList);
		if(formalParamList != null)
			currentMethod.setParamList(formalParamList);
		
	}

	//Adicionando retorno de LocalVarList
	private Expr localDec() {
		// LocalDec ::= Type IdList ";"
		//Crio uma localDecList
		Expr e = null;
		LocalVarList localDecList = new LocalVarList();
		Type type = type();
		if ( lexer.token != Symbol.IDENT ) error.show("Identifier expected");
		Variable v = new Variable(lexer.getStringValue(), type);
		//Vou adicionando os elementos na lista 
		localDecList.addElement(v);
		if(symbolTable.getInLocal(lexer.getStringValue()) == null){
			symbolTable.putInLocal(lexer.getStringValue(), v);
		}else{
			error.show("Variable already declared inside this scope.");
		}
		lexer.nextToken();
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				error.show("Identifier expected");
			//Conforme o loop vai rodando tambem vou adicionando 
			v = new Variable(lexer.getStringValue(), type);
			localDecList.addElement(v);
			if(symbolTable.getInLocal(lexer.getStringValue()) == null){
				symbolTable.putInLocal(lexer.getStringValue(), v);
			}else{
				error.show("Variable already declared inside this scope.");
			}
			lexer.nextToken();
		}
		if ( lexer.token != Symbol.SEMICOLON){
			if(lexer.token == Symbol.DOT)
				error.show(". is an illegal character here.");
			error.show(CompilerError.semicolon_expected);
		}
		lexer.nextToken();
		return new AssignExprLocalDec(localDecList);
	}

	private ParamList formalParamDec() {
		// FormalParamDec ::= ParamDec { "," ParamDec }
		//Cria uma parameter list
		ParamList paramList;
		Parameter p = null;
		//Inicializo
		paramList = new ParamList();
		//Adiciono o parametro que recebo de paramDec na lista
		p = paramDec();
		if(p != null){
			paramList.addElement(p);
		}
		//E continuo adicionando enquanto houverem mais
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			p = paramDec();
			if(p != null){
				paramList.addElement(p);
			}
		}
		return paramList;
	}

	//Retorna Parameter
	private Parameter paramDec() {
		// ParamDec ::= Type Id
		Type t;
		String name;
		Variable p; 
		Parameter newP = null;
		
		
		t = type();
		
		
		if ( lexer.token != Symbol.IDENT ) error.show("Identifier expected");
		
		name = lexer.getStringValue();
		//verifico se ja nao existe parametro com mesmo nome na lista de parametros atraves da localTable
		if((p = symbolTable.getInLocal(lexer.getStringValue())) == null){
			//Crio um novo Parameter object
			newP = new Parameter(name, t);
			symbolTable.putInLocal(lexer.getStringValue(), newP);
		}else{
			error.show("Duplicate parameters for method");
		}
		
		lexer.nextToken();
		return newP;
	}

	private Type type() {
		// Type ::= BasicType | Id
		Type result;

		switch (lexer.token) {
		case VOID:
			result = Type.voidType;
			break;
		case INT:
			result = Type.intType;
			break;
		case BOOLEAN:
			result = Type.booleanType;
			break;
		case STRING:
			result = Type.stringType;
			break;
		case IDENT:
			// # corrija: faça uma busca na TS para buscar a classe
			// IDENT deve ser uma classe.
			
			//uso o metodo getInGlobal para procurar a definicao da classe na tabela global 
			result = symbolTable.getInGlobal(lexer.getStringValue());
			if(result == null) error.show(lexer.getStringValue()+" is not a class");
			break;
		default:
			error.show("Type expected");
			result = Type.undefinedType;
		}
		lexer.nextToken();
		return result;
	}

	private CompositeStatement compositeStatement() {

		lexer.nextToken();
		StatementList sList = statementList();
		if ( lexer.token != Symbol.RIGHTCURBRACKET )
			error.show("} expected");
		else
			lexer.nextToken();
		return new CompositeStatement(sList);
	}

	private StatementList statementList() {
		// CompStatement ::= "{" { Statement } "}"
		Symbol tk;
		StatementList sList = new StatementList();
		// statements always begin with an identifier, if, read, write, ...
		while ((tk = lexer.token) != Symbol.RIGHTCURBRACKET
				&& tk != Symbol.ELSE)
			sList.addElement(statement());
		return sList;
	}

	private Statement statement() {
		/*
		 * Statement ::= Assignment ``;'' | IfStat |WhileStat | MessageSend
		 *                ``;'' | ReturnStat ``;'' | ReadStat ``;'' | WriteStat ``;'' |
		 *               ``break'' ``;'' | ``;'' | CompStatement | LocalDec
		 */
		switch (lexer.token) {
		case THIS:
		case IDENT:
		case SUPER:
		case INT:
		case BOOLEAN:
		case STRING:
			return new AssignStatement(assignExprLocalDec());
		case RETURN:
			return returnStatement();
		case READ:
			return readStatement();
		case WRITE:
			return writeStatement();
		case WRITELN:
			return writelnStatement();
		case IF:
			return ifStatement();
		case BREAK:
			return breakStatement();
		case WHILE:
			return whileStatement();
		case SEMICOLON:
			return nullStatement();
		case LEFTCURBRACKET:
			return compositeStatement();
		default:
			error.show("Statement expected");
		}
		return null;
	}

	/*
	 * retorne true se 'name' é uma classe declarada anteriormente. É necessário
	 * fazer uma busca na tabela de símbolos para isto.
	 */
	private boolean isType(String name) {
		return this.symbolTable.getInGlobal(name) != null;
	}

	/*
	 * AssignExprLocalDec ::= Expression [ ``$=$'' Expression ] | LocalDec
	 */
	private Expr assignExprLocalDec() {
		if ( lexer.token == Symbol.INT || lexer.token == Symbol.BOOLEAN
				|| lexer.token == Symbol.STRING ||
				// token é uma classe declarada textualmente antes desta
				// instrução
				(lexer.token == Symbol.IDENT && isType(lexer.getStringValue()) && (lexer.lookAheadForDot() != Symbol.DOT && lexer.lookAheadForAttribution() != Symbol.ASSIGN))  ) {
				//lexer.lookAheadForAttribution();
				
			/*
			 * uma declaração de variável. 'lexer.token' é o tipo da variável
			 * 
			 * AssignExprLocalDec ::= Expression [ ``$=$'' Expression ] | LocalDec 
			 * LocalDec ::= Type IdList ``;''
			 */
			
			return localDec();
		}
		else {
			/*
			 * AssignExprLocalDec ::= Expression [ ``$=$'' Expression ]
			 */
			Expr first = expr();
			Expr second = null;
			if ( lexer.token == Symbol.ASSIGN ) {
				lexer.nextToken();
				second = expr();
				if(isType(first.getType().getName())){
					if(second.getType().getName().compareTo("null") != 0){
						boolean check = checkClassType(first.getType(), second.getType());
						if(!check){
							error.show("Type error: second type is not convertible to the type of first expression.");
						}
					}
				}else{
					if(first.getType().getName().compareTo(second.getType().getName()) != 0)
						error.show("Type error: types do not match between expressions.");
				}
			}
			if(first != null && second == null){
				if(first.getType().getName().compareTo("void") != 0){
					if(first.getType().getName().compareTo("int") == 0)
						error.show("Cannot use expression as instruction.");
				}
			}
			if ( lexer.token != Symbol.SEMICOLON )
				error.show(CompilerError.semicolon_expected);
			lexer.nextToken();
			if(second != null)
				return new AssignExprLocalDec(first, second);
			else{
				return new AssignExprLocalDec(first);
			}
		}
	}

	private ExprList realParameters() {
		ExprList anExprList = null;

		if ( lexer.token != Symbol.LEFTPAR ) error.show("( expected");
		lexer.nextToken();
		if ( startExpr(lexer.token) ) anExprList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) error.show(") expected");
		lexer.nextToken();
		return anExprList;
	}

	private WhileStatement whileStatement() {
		insideWhile++;
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) error.show("( expected");
		lexer.nextToken();
		Expr e = expr();
		if(e.getType().getName().compareTo("boolean") != 0)
			error.show("Expression inside while statement needs to of boolean type.");
		if ( lexer.token != Symbol.RIGHTPAR ) error.show(") expected");
		lexer.nextToken();
		Statement s = statement();
		insideWhile--;
		return new WhileStatement(e, s);
	}

	private IfStatement ifStatement() {
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) error.show("( expected");
		lexer.nextToken();
		Expr e = expr();
		if ( lexer.token != Symbol.RIGHTPAR ) error.show(") expected");
		lexer.nextToken();
		Statement s = statement();
		Statement elseS = null;
		if ( lexer.token == Symbol.ELSE ) {
			lexer.nextToken();
			elseS = statement();
		}
		if(elseS == null)
			return new IfStatement(e, s);
		else
			return new IfStatement(e, s, elseS);
	}

	private ReturnStatement returnStatement() {

		lexer.nextToken();
		Expr e = expr();
		if(isType(e.getType().getCname())){
			if(!checkClassType(currentMethod.getType(), e.getType())){
				error.show("Type error, return statement is of class type from method");
			}
		}else{
			if(currentMethod.getType().getName().compareTo(e.getType().getName()) != 0)
				error.show("Return statement different from type of method.");
		}
		if ( lexer.token != Symbol.SEMICOLON )
			error.show(CompilerError.semicolon_expected);
		lexer.nextToken();
		hasReturn = true;
		return new ReturnStatement(e);
	}

	
	private ReadStatement readStatement() {
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) error.show("( expected");
		lexer.nextToken();
		boolean self = false;
		boolean staticFlag = false;
		Variable v = null;
		while (true) {
			if ( lexer.token == Symbol.THIS ) {
				lexer.nextToken();
				self = true;
				if ( lexer.token != Symbol.DOT ) error.show(". expected");
				lexer.nextToken();
			}
			if ( lexer.token != Symbol.IDENT )
				error.show(CompilerError.ident_expected);
			String name = lexer.getStringValue();
			if(self){
				v = currentClass.searchInstance(name, false);
			}else{
				if(isType(name)){
					lexer.nextToken();
					staticFlag = true;
					if ( lexer.token != Symbol.DOT ) error.show(". expected");
					lexer.nextToken();
					String staticName = lexer.getStringValue();
					v = currentClass.searchInstance(staticName, true);
					if(v == null)
						error.show("Could not find static variable "+staticName);
				}else{
					if(symbolTable.getInLocal(name) == null){
						v = currentClass.searchInstance(name, false);
						if(v == null)
							error.show("Could not find reference to the instance to be read");
					}else
						v = symbolTable.getInLocal(name);
				}
			}
			lexer.nextToken();
			if ( lexer.token == Symbol.COMMA )
				lexer.nextToken();
			else
				break;
		}
		
		if ( lexer.token != Symbol.RIGHTPAR ) error.show(") expected");
		lexer.nextToken();
		if(v.getType().getName().compareTo("boolean") == 0){
			error.show("Can't read boolean");
		}
		if(isType(v.getType().getName())){
			error.show("Can't read objects.");
		}
		if ( lexer.token != Symbol.SEMICOLON )
			error.show(CompilerError.semicolon_expected);
		lexer.nextToken();
		
		return new ReadStatement(v, self, staticFlag);
	}

	private WriteStatement writeStatement() {
		
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) error.show("( expected");
		lexer.nextToken();
		ExprList eList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) error.show(") expected");
		lexer.nextToken();
		Iterator<Expr> ex = eList.elements();
		while(ex.hasNext()){
			Expr aux = ex.next();
			if(aux != null){
				if(aux.getType().getName().compareTo("boolean") == 0)
					error.show("Can't write to boolean.");
				if(isType(aux.getType().getName()))
					error.show("Can't write to objects");
			}
		}
		if ( lexer.token != Symbol.SEMICOLON )
			error.show(CompilerError.semicolon_expected);
		lexer.nextToken();
		return new WriteStatement(eList, false);
	}

	private WriteStatement writelnStatement() {
		
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) error.show("( expected");
		lexer.nextToken();
		ExprList eList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) error.show(") expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			error.show(CompilerError.semicolon_expected);
		lexer.nextToken();
		return new WriteStatement(eList, true);
	}

	private BreakStatement breakStatement() {
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			error.show(CompilerError.semicolon_expected);
		lexer.nextToken();
		if(insideWhile <= 0){
			error.show("Break outside while.");
		}
		return new BreakStatement();
	}

	private NullStatement nullStatement() {
		lexer.nextToken();
		return new NullStatement();
	}

	private ExprList exprList() {
		// ExpressionList ::= Expression { "," Expression }

		ExprList anExprList = new ExprList();
		anExprList.addElement(expr());
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			anExprList.addElement(expr());
		}
		return anExprList;
	}

	private Expr expr() {

		Expr left = simpleExpr();
		Symbol op = lexer.token;
		if ( op == Symbol.EQ || op == Symbol.NEQ || op == Symbol.LE
				|| op == Symbol.LT || op == Symbol.GE || op == Symbol.GT ) {
			lexer.nextToken();
			Expr right = simpleExpr();
			if(op == Symbol.EQ || op == Symbol.NEQ){
				if(isType(left.getType().getName()) && isType(right.getType().getName())){
					boolean check = checkClassType(left.getType(), right.getType());
					if(!check){
						check = checkClassType(right.getType(), left.getType());
						if(!check)
							error.show("Trying to compare different types");
					}
						
				}else{
					if(left.getType().getName().compareTo("null") != 0 && right.getType().getName().compareTo("null") != 0){
						if(left.getType().getName().compareTo(right.getType().getName()) != 0)
							error.show("Error trying to compare different types.");
					}
				}
				/*if(left.getType().getName().compareTo(right.getType().getName()) != 0){
					
					error.show("Comparison between different types.");
				}*/
			}
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

	private Expr simpleExpr() {
		Symbol op;

		Expr left = term();
		while ((op = lexer.token) == Symbol.MINUS || op == Symbol.PLUS
				|| op == Symbol.OR) {
			lexer.nextToken();
			Expr right = term();
			if(isType(right.getType().getName()) || isType(left.getType().getName()))
				error.show("Operation invalid for objects.");
			if(op == Symbol.MINUS || op == Symbol.PLUS){
					if(right.getType().getName().compareTo("boolean") == 0 || left.getType().getName().compareTo("boolean") == 0)
						error.show("Boolean type not compatible with arithmetic operations.");
			}
			if(op == Symbol.OR){
					if(right.getType().getName().compareTo("int") == 0 || left.getType().getName().compareTo("int") == 0)
						error.show("int type not compatible with logical operations.");
			}
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

	private Expr term() {
		Symbol op;

		Expr left = signalFactor();
		while ((op = lexer.token) == Symbol.DIV || op == Symbol.MULT
				|| op == Symbol.AND) {
			lexer.nextToken();
			Expr right = signalFactor();
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

	private Expr signalFactor() {
		Symbol op;
		if ( (op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS ) {
			lexer.nextToken();
			Expr e = factor();
			if(isType(e.getType().getName()))
				error.show("Unary operation not compatible for objects.");
			if(e.getType().getName().compareTo("boolean") == 0)
				error.show("Unary operation not valid for boolean.");
			return new SignalExpr(op, e);
		}
		else
			return factor();
	}

	/*
	 * Factor ::= BasicValue | "(" Expression ")" | "!" Factor | "null" |
	 *      ObjectCreation | PrimaryExpr
	 * 
	 * BasicValue ::= IntValue | BooleanValue | StringValue 
	 * BooleanValue ::=  "true" | "false" 
	 * ObjectCreation ::= "new" Id "(" ")" 
	 * PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"  | 
	 *                 Id  |
	 *                 Id "." Id | 
	 *                 Id "." Id "(" [ ExpressionList ] ")" |
	 *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
	 *                 "this" | 
	 *                 "this" "." Id | 
	 *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
	 *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
	 */
	private Expr factor() {

		Expr e;
		ExprList exprList;
		String messageName, ident;

		switch (lexer.token) {
		// IntValue
		case NUMBER:
			return literalInt();
			// BooleanValue
		case TRUE:
			lexer.nextToken();
			return LiteralBoolean.True;
			// BooleanValue
		case FALSE:
			lexer.nextToken();
			return LiteralBoolean.False;
			// StringValue
		case LITERALSTRING:
			String literalString = lexer.getLiteralStringValue();
			lexer.nextToken();
			return new LiteralString(literalString);
			// "(" Expression ")" |
		case LEFTPAR:
			lexer.nextToken();
			e = expr();
			if ( lexer.token != Symbol.RIGHTPAR ) error.show(") expected");
			lexer.nextToken();
			return new ParenthesisExpr(e);

			// "!" Factor
		case NOT:
			lexer.nextToken();
			e = expr();
			if(isType(e.getType().getName()))
				error.show("Cannot use NOT operation in an object.");
			if(e.getType().getName().compareTo("int") == 0)
				error.show("NOT operation not valid for int.");
			return new UnaryExpr(e, Symbol.NOT);
			// "null"
		case NULL:
			lexer.nextToken();
			return new NullExpr();
			// ObjectCreation ::= "new" Id "(" ")"
		case NEW:
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				error.show("Identifier expected");

			String className = lexer.getStringValue();
			/*
			 * // encontre a classe className in symbol table KraClass 
			 *      aClass = symbolTable.getInGlobal(className); 
			 *      if ( aClass == null ) ...
			 */
			ObjectCreation obj = null;
			if(isType(className)){
				KraClass c = symbolTable.getInGlobal(className);
				obj = new ObjectCreation(c);
			}else{
				error.show("Trying to create an object of an undefined class!");
			}
			lexer.nextToken();
			if ( lexer.token != Symbol.LEFTPAR ) error.show("( expected");
			lexer.nextToken();
			if ( lexer.token != Symbol.RIGHTPAR ) error.show(") expected");
			lexer.nextToken();
			/*
			 * return an object representing the creation of an object
			 */
			return obj;
			/*
          	 * PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"  | 
          	 *                 Id  |
          	 *                 Id "." Id | 
          	 *                 Id "." Id "(" [ ExpressionList ] ")" |
          	 *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
          	 *                 "this" | 
          	 *                 "this" "." Id | 
          	 *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
          	 *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
			 */
		case SUPER:
			// "super" "." Id "(" [ ExpressionList ] ")"
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				error.show("'.' expected");
			}
			else
				lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				error.show("Identifier expected");
			messageName = lexer.getStringValue();
			
			KraClass superClass = currentClass.getSuperClass();
			Method m = null;
			while(superClass != null){
				m = superClass.searchMethodOnlyNonStaticAndPublic(messageName);
				if(m == null)
					superClass = superClass.getSuperClass();
				else
					break;
			}
			/*
			 * para fazer as conferências semânticas, procure por 'messageName'
			 * na superclasse/superclasse da superclasse etc
			 */
			lexer.nextToken();
			exprList = realParameters();
			
			if(m == null)
				error.show("Couldn't find method "+messageName+" in any of the superclasses of "+currentClass.getCname());
			else{
				if(exprList != null){
					if(m.getParamListSize() != exprList.getSize())
						error.show("Number of parameters wrong in method call "+m.getName());
					Iterator<Variable> iterVar = m.getParamElements();
					Iterator<Expr> iterExpr = exprList.elements();
					while(iterVar.hasNext() && iterExpr.hasNext()){
						Variable v = iterVar.next();
						Expr ex = iterExpr.next();
						if(v.getType().getName().compareTo("int") == 0 || v.getType().getName().compareTo("String") == 0 || v.getType().getName().compareTo("boolean") == 0){
							if(v.getType().getName().compareTo(ex.getType().getName()) != 0)
								error.show("Different type assignment for method parameter in method "+m.getName());
						}else{
							if(v.getType().getName().compareTo("undefined") == 0 || ex.getType().getName().compareTo("undefined") == 0)
								error.show("Undefined type for variable or expression");
							
							if(!checkClassType(v.getType(), ex.getType()))
								error.show("The parameters seem to be from different classes");	
						}
					}
				}
			}
			return new MessageSendToSuper(m, exprList, superClass);
			
		case IDENT:
			/*
          	 * PrimaryExpr ::=  
          	 *                 Id  |
          	 *                 Id "." Id | 
          	 *                 Id "." Id "(" [ ExpressionList ] ")" |
          	 *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
			 */

			String firstId = lexer.getStringValue();
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				// Id
				// retorne um objeto da ASA que representa um identificador
				Variable v = null;
				v = symbolTable.getInLocal(firstId);
				if (v == null){
					v = currentClass.searchInstance(firstId, false);
					if(v != null)
						error.show("Using instance variable without 'this'.");
				}
				if(v == null)
					error.show("Identifier not found in method or inside class.");
				return new VariableExpr(v);
			}
			else { // Id "."
				lexer.nextToken(); // coma o "."
				if ( lexer.token != Symbol.IDENT ) {
					error.show("Identifier expected");
				}
				else {
					// Id "." Id
					lexer.nextToken();
					ident = lexer.getStringValue();
					if ( lexer.token == Symbol.DOT ) {
						// Id "." Id "." Id "(" [ ExpressionList ] ")"
						/*
						 * se o compilador permite variáveis estáticas, é possível
						 * ter esta opção, como
						 *     Clock.currentDay.setDay(12);
						 * Contudo, se variáveis estáticas não estiver nas especificações,
						 * sinalize um erro neste ponto.
						 */
						lexer.nextToken();
						if ( lexer.token != Symbol.IDENT )
							error.show("Identifier expected");
						messageName = lexer.getStringValue();
						lexer.nextToken();
						exprList = this.realParameters();
						
					}
					else if ( lexer.token == Symbol.LEFTPAR ) {
						Variable v = null;
						Variable v2 = null;
						Method m1 = null;
						KraClass k2 = null;
						boolean staticFlag = false;
						v = symbolTable.getInLocal(firstId);
						v2 = currentClass.searchInstance(firstId, false);
						if(v != null || v2 != null){
							if(v == null)
								v = v2;
							
							if(!isType(v.getType().getCname()))
								error.show("First identifier must be in class in order to call method."+v.getType().getCname());
							
							KraClass k = symbolTable.getInGlobal(v.getType().getCname());
							m1 = k.searchMethodOnlyNonStatic(ident);
							if(m1 == null){
								k = k.getSuperClass();
								while(k != null){
									m1 = k.searchMethodOnlyNonStaticAndPublic(ident);
									if(m1 == null)
										k = k.getSuperClass();
									else
										break;
								}
							}
							if(m1 == null)
								error.show("Method "+ident+" not found in member of class");
							else{
								if(m1.isPrivate())
									error.show("Can't call private method from class "+k.getName());
							}
							
						}else{
							staticFlag = true;
							k2 = symbolTable.getInGlobal(firstId);
							m1 = k2.searchMethodOnlyStatic(ident);
							
							if(m1 == null)
								error.show("There is no static method for class "+firstId);
							else{
								if(m1.isPrivate() && currentClass.getName().compareTo(firstId) != 0){
									error.show("Trying to call private static method.");
								}
							}
							
						}
												
						
						// Id "." Id "(" [ ExpressionList ] ")"
						exprList = this.realParameters();
						/*
						 * para fazer as conferências semânticas, procure por
						 * método 'ident' na classe de 'firstId'
						 */
						if(staticFlag)
							return new MessageSendToVariable(k2, m1, exprList);
						else
							return new MessageSendToVariable(v, m1, exprList);
					}
					else {
						//caso for chamada static entao verifica-se se o nome do primeiro Id eh classe
						KraClass k = symbolTable.getInGlobal(firstId);
						if(k == null)
							error.show("Identifier does not belong to a class.");
						Variable v = k.searchInstance(ident, true);
						if(v == null)
							error.show("Static instance"+ ident +"does not belong to class "+k.getCname());
						return new MessageSendToVariable(k, v);
					}
				}
			}
			break;
		case THIS:
			/*
			 * Este 'case THIS:' trata os seguintes casos: 
          	 * PrimaryExpr ::= 
          	 *                 "this" | 
          	 *                 "this" "." Id | 
          	 *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
          	 *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
			 */
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				
				// only 'this'
				// retorne um objeto da ASA que representa 'this'
				// confira se não estamos em um método estático
				if(currentMethod.isStatic())
					error.show("Trying to use this inside static method");
				return new MessageSendToSelf(currentClass);
			}
			else {
				lexer.nextToken();
				if ( lexer.token != Symbol.IDENT )
					error.show("Identifier expected");
				ident = lexer.getStringValue();
				lexer.nextToken();
				// já analisou "this" "." Id
				if ( lexer.token == Symbol.LEFTPAR ) {
					
					// "this" "." Id "(" [ ExpressionList ] ")"
					/*
					 * Confira se a classe corrente possui um método cujo nome é
					 * 'ident' e que pode tomar os parâmetros de ExpressionList
					 */
					
					Method m3 = currentClass.searchMethodOnlyNonStatic(ident);
					if(m3 == null){
						KraClass sClass = currentClass.getSuperClass();
						while(sClass != null){
							m3 = sClass.searchMethodOnlyNonStaticAndPublic(ident);
							if(m3 == null)
								sClass = sClass.getSuperClass();
							else
								break;
						}
					}
					
					
					
					if(m3==null)
						error.show("Couldn't find method in this class or its superclasses.");
					
					if(currentMethod.isStatic())
						error.show("Can't call instance inside static method.");
					
					exprList = this.realParameters();
					
					if(exprList != null){
						if(m3.getParamListSize() != exprList.getSize()){
							error.show("Call for method "+m3.getName()+" doesnt have the right amount of parameters.");
						}
						Iterator<Variable> iteraV = m3.getParamElements();
						Iterator<Expr> iteraE = exprList.elements();
						
						while(iteraV.hasNext()){
							Variable v1 = iteraV.next();
							Expr e1 = iteraE.next();
							if(isType(e1.getType().getName())){
								boolean check = checkClassType(v1.getType(), e1.getType());
								if(!check){
									error.show("Method call has different type than method declaration.");
								}
							}else{
								if(e1.getType().getName().compareTo(v1.getType().getName()) != 0)
									error.show("Method call has different type than method declaration.");
							}
						}
					}
					


					
					
					return new MessageSendToSelf(currentClass, m3, exprList);
				}
				else if ( lexer.token == Symbol.DOT ) {
					// "this" "." Id "." Id "(" [ ExpressionList ] ")"
					lexer.nextToken();
					if ( lexer.token != Symbol.IDENT )
						error.show("Identifier expected");
					String message = lexer.getStringValue();
					Method m3 = null;
					Variable v = currentClass.searchInstance(ident, false);
					if(v != null){
						if(isType(v.getType().getName())){
							KraClass sClass = symbolTable.getInGlobal(v.getType().getName());
							m3 = sClass.searchMethodOnlyNonStatic(message);
							if(m3 == null){
								sClass = sClass.getSuperClass();
								while(sClass != null){
									m3 = sClass.searchMethodOnlyNonStaticAndPublic(message);
									if(m3 == null)
										sClass = sClass.getSuperClass();
									else
										break;
								}
							}
							
						}else{
							error.show("This variable does not refer to an object to be able to call a method.");
						}
					}else{
						error.show("No instance found.");
					}
					
					if(m3 == null)
						error.show("The method does not exist.");
					lexer.nextToken();
					exprList = this.realParameters();
					
					if(exprList != null){
						if(m3.getParamListSize() != exprList.getSize()){
							error.show("Call for method "+m3.getName()+" doesnt have the right amount of parameters.");
						}
						Iterator<Variable> iteraV = m3.getParamElements();
						Iterator<Expr> iteraE = exprList.elements();
						
						while(iteraV.hasNext()){
							Variable v1 = iteraV.next();
							Expr e1 = iteraE.next();
							if(isType(e1.getType().getName())){
								boolean check = checkClassType(v1.getType(), e1.getType());
								if(!check){
									error.show("Method call has different type than method declaration.");
								}
							}else{
								if(e1.getType().getName().compareTo(v1.getType().getName()) != 0)
									error.show("Method call has different type than method declaration.");
							}
						}
					}
					
					
					return new MessageSendToSelf(currentClass, v, m3, exprList);
				}
				else {
					// retorne o objeto da ASA que representa "this" "." Id
					/*
					 * confira se a classe corrente realmente possui uma
					 * variável de instância 'ident'
					 */
					Variable v = currentClass.searchInstance(ident, false);
					if(v == null)
						error.show("There is no instance "+ident+" in class "+currentClass.getCname());
					if(currentMethod.isStatic())
						error.show("Can't call instance inside static method.");
					return new MessageSendToSelf(currentClass, v);
				}
			}
		default:
			error.show("Expression expected");
		}
		return null;
	}

	private LiteralInt literalInt() {
		// the number value is stored in lexer.getToken().value as an object of
		// Integer.
		// Method intValue returns that value as an value of type int.
		int value = lexer.getNumberValue();
		lexer.nextToken();
		return new LiteralInt(value);
	}

	private static boolean startExpr(Symbol token) {

		return token == Symbol.FALSE || token == Symbol.TRUE
				|| token == Symbol.NOT || token == Symbol.THIS
				|| token == Symbol.NUMBER || token == Symbol.SUPER
				|| token == Symbol.LEFTPAR || token == Symbol.NULL
				|| token == Symbol.IDENT || token == Symbol.LITERALSTRING;

	}
	
	private boolean checkClassType(Type t, Type t2){
		if(t.getCname().compareTo(t2.getCname()) == 0)
			return true;
		else{
			
			KraClass klass = symbolTable.getInGlobal(t2.getCname());
			if(klass != null){
				klass = klass.getSuperClass();
				while(klass != null){
					if(klass.getCname().compareTo(t.getCname()) == 0)
						return true;
					klass = klass.getSuperClass();
				}
			}
		}
		return false;
		
	}
	
	private Method findMethodInSuperClass(){
		Method m = null;
		KraClass klass;
		klass = currentClass.getSuperClass();
		while(klass != null){
			m = klass.searchMethodOnlyNonStaticAndPublic(currentMethod.getName());
			if(m != null){
				return m;
			}
			klass = klass.getSuperClass();
		}
		return null;
	}
	
	
	private boolean compareParamLists(ParamList p1, ParamList p2){
		Iterator<Variable> it1, it2;
		Variable v1, v2;
		if(p1 == null && p2 == null){
			return true;
		}else if(p1 == null || p2 == null)
			return false;
		if(p1.getSize() != p2.getSize())
			return false;
		it1 = p1.elements();
		it2 = p2.elements();
		while(it1.hasNext()){
			v1 = it1.next();
			v2 = it2.next();
			if(v1.getType().getName().compareTo(v2.getType().getName()) != 0)
					return false;
			
		}
		return true;
	}

	private KraClass currentClass;
	private Method currentMethod;
	private boolean hasReturn;
	private int insideWhile;
	private SymbolTable		symbolTable;
	private Lexer			lexer;
	private CompilerError	error;

}
