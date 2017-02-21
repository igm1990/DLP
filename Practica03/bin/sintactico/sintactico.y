%{
// * Declaraciones de c�digo Java
// * Se sit�an al comienzo del archivo generado
// * El package lo a�ade yacc si utilizamos la opci�n -Jpackage
import lexico.Lexico;
import java.io.Reader;
%}

// * Declaraciones Yacc
%token CTE_ENTERA CTE_REAL CTE_CARACTER
%token READ WRITE WHILE IF ELSE 
%token INT DOUBLE CHAR STRUCT 
%token RETURN VOID MAIN ID 
%token Y O MAYORIGUALQUE MENORIGUALQUE DISTINTO IGUALDAD

// M�s arriba, menos precedencia
%right '='
%left Y O '!'
%left '>' MAYORIGUALQUE MENORIGUALQUE '<' DISTINTO IGUALDAD
%left '+' '-'
%left '*' '/' '%'
%right MENOS_UNARIO
%nonassoc '[' ']'
%left '.'
%nonassoc '(' ')'
%nonassoc '{' '}'

%%
// * Gram�tica y acciones Yacc
sentencias: sentencias sentencia
	     | sentencia
		 ;
		 	
sentencia: variable '=' expresion ';'
		 | estructura ';'
		 | WHILE '(' expresion ')' '{' sentencias '}'
		 | IF '(' expresion ')' '{' sentencias '}'
		 | IF '(' expresion ')' '{' sentencias '}' ELSE '{' sentencias '}'
		 | WRITE expresiones ';'
         ;
         
estructura: STRUCT '{' campos '}' ID
		   | variable
		   ;		   

// Campos dentro de un Struct		   
campos: campos estructura
	  | variable ';'
      ;

// Variables locales de las funciones      
variableFuncion: variableFuncion variable
		 | /*Vacio*/
		 ;

variable: identificador
		| identificador '[' CTE_ENTERA ']'
		| identificador '[' CTE_ENTERA ']' '[' CTE_ENTERA ']'
		| tipoSimple identificador 
		| tipoSimple '[' CTE_ENTERA ']' identificador  
		;

expresiones: expresiones ',' CTE_CARACTER
		   | CTE_CARACTER
		   ;	   
	  		     
expresion: identificador
         | CTE_ENTERA
         | CTE_REAL
         | CTE_CARACTER
         | expresion '+' expresion
         | expresion '*' expresion 
         | expresion '/' expresion 
         | expresion '-' expresion
         | expresion '%' expresion
         | expresion '>' expresion
         | expresion '<' expresion 
         | expresion MAYORIGUALQUE expresion 
         | expresion MENORIGUALQUE expresion
         | expresion DISTINTO expresion
         | expresion IGUALDAD expresion 
         | expresion Y expresion 
         | expresion O expresion
         | '-' expresion %prec MENOS_UNARIO 
         | '(' expresion ')'
         ;
         
identificador: identificador ',' ID 
		     | ID	
		     ;
	
tipoSimple: INT
	| DOUBLE
	| CHAR 
	;
%%

// * C�digo Java
// * Se crea una clase "Parser", lo que aqu� ubiquemos ser�:
//	- Atributos, si son variables
//	- M�todos, si son funciones
//   de la clase "Parser"

// * Estamos obligados a implementar:
//	int yylex()
//	void yyerror(String)

// * Referencia al analizador l�xico
private Lexico lexico;

// * Llamada al analizador l�xico
private int yylex () {
    int token=0;
    try { 
	token=lexico.yylex(); 
    } catch(Throwable e) {
	    System.err.println ("Error L�xico en l�nea " + lexico.getLine()+
		" y columna "+lexico.getColumn()+":\n\t"+e); 
    }
    return token;
}

// * Manejo de Errores Sint�cticos
public void yyerror (String error) {
    System.err.println ("Error Sint�ctico en l�nea " + lexico.getLine()+
		" y columna "+lexico.getColumn()+":\n\t"+error);
}

// * Constructor del Sint�ctico
public Parser(Lexico lexico) {
	this.lexico = lexico;
}