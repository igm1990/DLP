%{
// * Declaraciones de código Java
// * Se sitúan al comienzo del archivo generado
// * El package lo añade yacc si utilizamos la opción -Jpackage
import lexico.Lexico;
import java.io.Reader;
import java.util.*;

import ast.*;
import ast.definiciones.*;
import ast.expresiones.*;
import ast.sentencias.*;
import ast.tipos.*;
%}

// * Declaraciones Yacc
%token CTE_ENTERA CTE_REAL CTE_CARACTER
%token READ WRITE WHILE IF ELSE FOR
%token INT DOUBLE CHAR STRUCT 
%token RETURN VOID MAIN ID 
%token Y O MAYORIGUALQUE MENORIGUALQUE DISTINTO IGUALDAD

// Más arriba, menos precedencia
%right '='
%left Y O 
%left '>' MAYORIGUALQUE MENORIGUALQUE '<' DISTINTO IGUALDAD
%left '+' '-'
%left '*' '/' '%'
%right MENOS_UNARIO
%right NEGACION
%left CASTP
%nonassoc '[' ']'
%left '.'
%nonassoc MENORQUEELSE
%nonassoc ELSE
%nonassoc '(' ')'
%nonassoc '{' '}'



%%
// * Gramática y acciones Yacc
programa: definiciones VOID MAIN '(' ')' '{' declaraciones sentencias '}'								{ 	List<Definicion> definiciones = (List<Definicion>)$1;																						
																											Definicion main = new DefFuncion(lexico.getLine(), lexico.getColumn(), "main",
																												new TipoFuncion(new ArrayList<DefVariable>(), TipoVoid.getInstancia()), 
																												(List<DefVariable>)$7, (List<Sentencia>)$8);
																											definiciones.add(main);
																																												
																											this.ast = new Programa(lexico.getLine(), lexico.getColumn(), definiciones); 
																										} ;
definiciones: definiciones definicion     																{	$$ = $1; 
																											List<Definicion> lista = (List<Definicion>)$$;																											
																											List<Definicion> listaDefiniciones = (List<Definicion>)$2;
																											for(int i = 0; i < listaDefiniciones.size(); i++) {
																												Definicion elemento = listaDefiniciones.get(i);
																											    lista.add(elemento);																
																											}
																										}
       |/*vacio*/																						{	$$ = new ArrayList<Definicion>(); }
       ;
  
definicion: tipoSimple ID '(' parametros ')' '{' declaraciones cuerpoDefinicion '}' /*funcion*/  		{ 	List<Definicion> lista = new ArrayList();
																											lista.add(new DefFuncion(lexico.getLine(), lexico.getColumn(), (String) $2,
																												new TipoFuncion((List<DefVariable>)$4, (Tipo)$1), 
																												(List<DefVariable>)$7, (List<Sentencia>)$8));
																											$$ = lista; 
																										}																										
	  | VOID ID '(' parametros ')' '{' declaraciones cuerpoDefinicion '}' /*procedimiento*/				{	List<Definicion> lista = new ArrayList();
																											lista.add(new DefFuncion(lexico.getLine(), lexico.getColumn(),  (String) $2,
																												new TipoFuncion((List<DefVariable>)$4, TipoVoid.getInstancia()), 
																												(List<DefVariable>)$7, (List<Sentencia>)$8));
																											$$ = lista;
																										}																										
	| declaracionVariable ';'																			{ 	$$ = $1;  }
	  ;	
	  
declaraciones: declaraciones declaracionVariable ';'													{ 	$$ = $1;
																											List<DefVariable> lista = (List<DefVariable>)$$;																											
																											for(DefVariable elemento : (List<DefVariable>)$2)
																												lista.add(elemento); 
																										}
			| /*vacio*/																					{ 	$$ = new ArrayList<DefVariable>();}
			;
	   
parametros: parametros ',' definicionVariable															{	$$ = $1; 
																											List<DefVariable> lista = (List<DefVariable>)$$;																												
																											for(DefVariable elemento : (List<DefVariable>) $3)
																												lista.add(elemento);																												
																										}
		  | definicionVariable																			{ 	$$ = $1; }
		  | /*vacio*/ 																					{ 	$$ = new ArrayList<DefVariable>(); }
		  ;
		  
definicionVariable: tipoSimple ID																		{ 	$$ = new ArrayList<DefVariable>();
																											((List<DefVariable>) $$).add(new DefVariable(lexico.getLine(), 
																												lexico.getColumn(), (String)$2, (Tipo)$1));  
																										}
			     ;
		 
cuerpoDefinicion: sentencias																			{ 	$$ = $1; }
	  | /*vacio*/																						{ 	$$ = new ArrayList<Sentencia>(); }
	  ;

sentencias: sentencias sentencia 																		{ 	$$ = $1;((List<Sentencia>)$$).add((Sentencia)$2); }
	     | sentencia 																					{ 	$$ = new ArrayList<Sentencia>(); ((List<Sentencia>)$$).add((Sentencia)$1); }
		 ;
		 	
sentencia: expresion '=' expresion ';' 							                						{ 	$$ = new Asignacion(lexico.getLine(), lexico.getColumn(), (Expresion)$1, (Expresion)$3); }			
		 | WHILE '(' expresion ')' cuerpoCondicional													{ 	$$ = new SentenciaWhile(lexico.getLine(), lexico.getColumn(), (Expresion)$3, (List<Sentencia>)$5); }
		 | IF '(' expresion ')' cuerpoCondicional %prec MENORQUEELSE									{ 	$$ = new SentenciaIf(lexico.getLine(), lexico.getColumn(), (Expresion)$3, (List<Sentencia>)$5, new ArrayList()); }
		 | IF '(' expresion ')' cuerpoCondicional ELSE cuerpoCondicional 								{ 	$$ = new SentenciaIf(lexico.getLine(), lexico.getColumn(), (Expresion)$3, (List<Sentencia>)$5, (List<Sentencia>)$7); }
		 | WRITE expresiones ';'																		{ 	$$ = new Escritura(lexico.getLine(), lexico.getColumn(),(List<Expresion>)$2); }
		 | READ expresiones ';'																			{ 	$$ = new Lectura(lexico.getLine(), lexico.getColumn(),(List<Expresion>)$2); }
		 | invocacion ';'																				{ 	$$ = $1; }
		 | RETURN expresion ';'																			{ 	$$ = new Return(lexico.getLine(), lexico.getColumn(), (Expresion)$2);}
		 | FOR '(' inicioFor ';'  finFor ';' pasoFor ')' cuerpoCondicional		                        {   $$ = new SentenciaFor(lexico.getLine(), lexico.getColumn(), (List<Sentencia>)$3, (Expresion)$5, (Sentencia)$7, (List<Sentencia>)$9);}
         ;

inicioFor: expresion '=' expresion																		{ 	$$ = new ArrayList<Sentencia>();
																											Asignacion a = new Asignacion(lexico.getLine(), lexico.getColumn(), (Expresion)$1, (Expresion)$3); 
																											((List<Sentencia>)$$).add(a);
																										}
		 | /*vacio*/																					{   $$ = new ArrayList<Sentencia>(); }
		 ;

finFor: expresionLogica																			        { 	$$ = $1;  }
	  | expresionComparacion																		    { 	$$ = $1;  }
	  ;

pasoFor: expresion '=' expresionAritmetica																{ 	$$ = new Asignacion(lexico.getLine(), lexico.getColumn(), (Expresion)$1, (Expresion)$3); }	
	   ;
         
cuerpoCondicional: '{' sentencias '}'																	{ 	$$ = $2;	}
		         | sentencia 																			{ 	$$ = new ArrayList<Sentencia>(); ((List<Sentencia>)$$).add((Sentencia)$1);  }
		         ;
      
declaracionVariable: tipoSimple identificador															{ 	List<DefVariable> variables = new ArrayList<DefVariable>();
																											for(String aux: (List<String>)$2)
																												variables.add(new DefVariable(lexico.getLine(), lexico.getColumn(), aux, (Tipo)$1));																												 
																											$$ = variables; 
																										}															 
		           | tipoSimple indices identificador 													{	List<Integer> indices = (List<Integer>)$2;
																											TipoArray tipo = new TipoArray(indices.get(0), (Tipo)$1);
																											for(int i = 1; i < indices.size(); i++)
																												tipo = new TipoArray(indices.get(i), tipo);																										           												
																											List<DefVariable> variables = new ArrayList();
																											for(String id: (List<String>)$3)
																												variables.add(new DefVariable(lexico.getLine(), lexico.getColumn(), id, tipo));
																											$$ = variables;  
																										}
		           | STRUCT '{' campos '}' identificador    											{ 	List<CampoRegistro> registrosStruct = new ArrayList();
		           																							for(DefVariable var : (List<DefVariable>) $3)
		           																								registrosStruct.add(new CampoRegistro(lexico.getLine(), lexico.getColumn(), var.getNombre(), var.getTipo()));		           												
																											TipoRegistro registro = new TipoRegistro(registrosStruct);		           												          												
																											List<DefVariable> variables = new ArrayList();
																											for(String aux: (List<String>)$5)
																												variables.add(new DefVariable(lexico.getLine(), lexico.getColumn(), aux, registro));
																											$$ = variables; 															 
																										}
				   | STRUCT '{' campos '}' indices identificador 										{
					   																						List<CampoRegistro> registrosStruct = new ArrayList();
		           																							for(DefVariable var : (List<DefVariable>) $3)
		           																								registrosStruct.add(new CampoRegistro(lexico.getLine(), lexico.getColumn(), var.getNombre(), var.getTipo()));		           												
																											TipoRegistro registro = new TipoRegistro(registrosStruct);

																											List<Integer> indices = (List<Integer>)$5;
																											TipoArray tipo = new TipoArray(indices.get(0), registro);

																											List<DefVariable> variables = new ArrayList();
																											for(String id: (List<String>)$6)
																												variables.add(new DefVariable(lexico.getLine(), lexico.getColumn(), id, tipo));
																											$$ = variables;
				   																						}
		           ;
				  
campos: campos declaracionVariable ';'																	{ 	$$ = $1;
																											List<DefVariable> lista = (List<DefVariable>)$$;
																											List<String> nombres = new ArrayList();																											
																											for(DefVariable nombre : lista)
																												nombres.add(nombre.getNombre());																											
																											for(DefVariable elemento : (List<DefVariable>)$2){ 
																											if(!nombres.contains(elemento.getNombre()))
																												(lista).add(elemento); 
																											else
																												new TipoError(lexico.getLine(), lexico.getColumn(),"Campo duplicado -> " + elemento.getNombre());
																											}
																										}
	  | declaracionVariable ';'																			{ $$ = $1; }
      ;

expresiones: expresiones ',' expresion																	{ 	$$ = $1; 
																											List<Expresion> lista = (List<Expresion>)$$;
																											Expresion elemento = (Expresion)$3;
																											lista.add(elemento);																																																								
																										}
		   | expresion																					{ 	$$ = new ArrayList<Expresion>(); ((List<Expresion>)$$).add((Expresion)$1);  	}
		   ;	
		      
expresion: ID																							{ 	$$ = new Variable(lexico.getLine(), lexico.getColumn(), (String) $1); 	}
		 | CTE_ENTERA                               													{ 	$$ = new LiteralEntero(lexico.getLine(), lexico.getColumn(), (Integer) $1);		}
         | CTE_REAL 																					{ 	$$ = new LiteralReal(lexico.getLine(), lexico.getColumn(), (Double) $1);	}
         | CTE_CARACTER 																				{ 	$$ = new LiteralCaracter(lexico.getLine(), lexico.getColumn(), (Character)$1);		}
         | expresionAritmetica																			{   $$ = $1; }
         | expresionComparacion																			{   $$ = $1; }
         | expresionLogica																				{   $$ = $1; }
         | '-' expresion %prec MENOS_UNARIO  															{ 	$$ = new MenosUnario(lexico.getLine(), lexico.getColumn(),  "-",(Expresion) $2);	}
         | expresion '.' ID					    													    { 	$$ = new AccesoCampo(lexico.getLine(), lexico.getColumn(), (Expresion) $1, (String) $3);	}
         | '(' expresion ')' 																			{ 	$$ = $2;}
         | '(' tipoSimple ')' expresion	%prec CASTP													    { 	$$ = new Cast(lexico.getLine(), lexico.getColumn(), (Tipo) $2, (Expresion) $4);	}
         | invocacion						 															{ 	$$ = $1;}
         | expresion '[' expresion ']'																	{ 	$$ = new AccesoArray(lexico.getLine(), lexico.getColumn(), (Expresion) $1, (Expresion) $3); }			   
         ;

expresionAritmetica: expresion '+' expresion 															{ 	$$ = new Aritmetica(lexico.getLine(), lexico.getColumn(), (Expresion) $1, "+", (Expresion) $3);		}
         | expresion '*' expresion  																	{ 	$$ = new Aritmetica(lexico.getLine(), lexico.getColumn(), (Expresion) $1, "*", (Expresion) $3);		}
         | expresion '/' expresion  																	{ 	$$ = new Aritmetica(lexico.getLine(), lexico.getColumn(), (Expresion) $1, "/", (Expresion) $3);		}
         | expresion '-' expresion 																		{ 	$$ = new Aritmetica(lexico.getLine(), lexico.getColumn(), (Expresion) $1, "-", (Expresion) $3);		}
         | expresion '%' expresion 																		{ 	$$ = new Aritmetica(lexico.getLine(), lexico.getColumn(), (Expresion) $1, "%", (Expresion) $3);		}
		 ;

expresionComparacion: expresion '>' expresion 															{ 	$$ = new Comparacion(lexico.getLine(), lexico.getColumn(), (Expresion) $1, ">", (Expresion) $3);	}
         | expresion '<' expresion  																	{ 	$$ = new Comparacion(lexico.getLine(), lexico.getColumn(), (Expresion) $1, "<", (Expresion) $3);	}
         | expresion MAYORIGUALQUE expresion  															{ 	$$ = new Comparacion(lexico.getLine(), lexico.getColumn(), (Expresion) $1, ">=", (Expresion) $3);	}
         | expresion MENORIGUALQUE expresion 															{ 	$$ = new Comparacion(lexico.getLine(), lexico.getColumn(), (Expresion) $1, "<=", (Expresion) $3);	}
         | expresion DISTINTO expresion 																{ 	$$ = new Comparacion(lexico.getLine(), lexico.getColumn(), (Expresion) $1, "!=", (Expresion) $3);	}
         | expresion IGUALDAD expresion  																{ 	$$ = new Comparacion(lexico.getLine(), lexico.getColumn(), (Expresion) $1, "==", (Expresion) $3);	}
		 ;
expresionLogica: expresion Y expresion  																{ 	$$ = new Logica(lexico.getLine(), lexico.getColumn(), (Expresion) $1, "&&", (Expresion) $3);	}
         | expresion O expresion 																		{ 	$$ = new Logica(lexico.getLine(), lexico.getColumn(), (Expresion) $1, "||", (Expresion) $3);	}
         | '!' expresion %prec NEGACION  																{ 	$$ = new Negacion(lexico.getLine(), lexico.getColumn(),  "!", (Expresion) $2);	}
		 ;
         
invocacion: expresion '(' argumentosLlamada ')' 														{ 	$$ = new Invocacion(lexico.getLine(), lexico.getColumn(), new Variable(lexico.getLine(), lexico.getColumn(), (String)$1),(List<Expresion>)$3); 	}
		   ;
			     
argumentosLlamada: expresiones 																			{ 	$$ = $1;}
				 | /*vacio*/  																			{ 	$$ = new ArrayList<Expresion>();	}
				 ;							  
				
         
indices: '[' CTE_ENTERA ']'	indices																		{ 	$$ = $4; 
																											List<Integer> lista = (List<Integer>)$$;
																											Integer elemento = (Integer)$2;
																											lista.add(elemento);
																										}
	   | '[' CTE_ENTERA ']'                 															{ 	$$ = new ArrayList<Integer>(); ((List<Integer>)$$).add((Integer)$2); 	}
	   ;
         
identificador: identificador ',' ID 																	{ 	$$ = $1; 
																											List<String> lista = (List<String>)$$;
																											String elemento = (String)$3;
																											if(!lista.contains(elemento))
																												lista.add(elemento);
																											else
																												new TipoError(lexico.getLine(), lexico.getColumn(),"Identificador duplicado -> " + elemento);																																																							
																										}
		     | ID																						{ 	$$ = new ArrayList(); ((List<String>)$$).add(((String) $1)); 	}	
		     ;
	
tipoSimple: INT																							{ 	$$ = TipoEntero.getInstancia(); 	}
	      | DOUBLE																						{ 	$$ = TipoReal.getInstancia(); 	}
	      | CHAR 																						{ 	$$ = TipoCaracter.getInstancia(); 	}
	      ;
%%

// * Código Java
// * Se crea una clase "Parser", lo que aquí ubiquemos será:
//	- Atributos, si son variables
//	- Métodos, si son funciones
//   de la clase "Parser"

private NodoAST ast;
public NodoAST getAST(){ return this.ast; }

// * Estamos obligados a implementar:
//	int yylex()
//	void yyerror(String)

// * Referencia al analizador léxico
private Lexico lexico;

// * Llamada al analizador léxico
private int yylex () {
    int token=0;
    try { 
	token=lexico.yylex();
	this.yylval = lexico.getYylval(); 
    } catch(Throwable e) {
	    System.err.println ("Error Léxico en línea " + lexico.getLine()+
		" y columna "+lexico.getColumn()+":\n\t"+e); 
    }
    return token;
}

// * Manejo de Errores Sintácticos
public void yyerror (String error) {
    System.err.println ("Error Sintáctico en línea " + lexico.getLine()+
		" y columna "+lexico.getColumn()+":\n\t"+error);
}

// * Constructor del Sintáctico
public Parser(Lexico lexico) {
	this.lexico = lexico;
}
