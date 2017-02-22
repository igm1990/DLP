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
%right NEGACION
%nonassoc '[' ']'
%left '.'
%nonassoc '(' ')'
%left SINCORCHETES
%nonassoc '{' '}'

%%
// * Gram�tica y acciones Yacc
programa: funciones main
		;

main: VOID MAIN '('')' '{' sentencias '}'
    ;
      
funciones: funciones funcion
		 | /*vacio*/
		 ;
		 
funcion: tipoFuncion ID '(' parametro ')' '{' cuerpo '}'
	   ;
	   
llamadaFuncion: ID '(' expresiones ')'
			  ;
				
tipoParametro: ID
			 | CTE_ENTERA
			 | CTE_REAL
			 ;
	   
parametro: parametro ',' tipoSimple ID
		 | tipoSimple ID
		 | /*vacio*/
		 ;
		 
cuerpo: variables sentencias retorno
	  | /*vacio*/
	  ;

retorno: RETURN expresion ';'
		| /*vacio*/
		;

tipoFuncion: VOID
		   | tipoSimple
		   ;

sentencias: sentencias sentencia
	     | sentencia
		 ;
		 	
sentencia: llamadaVariable
		 | declaracionVariable
		 | llamadaVariable '=' expresion ';' // Asignaci�n
		 | declaracionVariable '=' expresion ';' // Asignaci�n
		 | ID '.' ID '=' expresion ';'
		 | estructura ';'
		 | WHILE '(' expresion ')' '{' sentencias '}'
		 | IF '(' expresion ')' '{' sentencias '}'
		 | IF '(' expresion ')' sentencia 
		 | ELSE '{' sentencias '}'
		 | ELSE sentencia
		 | WRITE expresiones ';'
		 | READ expresiones ';'
		 | ID '(' parametroLlamada ')' ';' /*Llamada a funcion*/
		 | llamadaFuncion ';'
         ;
         
parametroLlamada: parametroLlamada ',' tipoParametro
				| /*vacio*/
				;
         
estructura: STRUCT '{' campos '}' ID
		   ;		   

// Campos dentro de un Struct		   
campos: campos estructura
	  | campos llamadaVariable
	  | campos declaracionVariable
	  | llamadaVariable
	  | declaracionVariable
      ;

llamadaVariable: ID
		       | ID '[' expresiones ']'
		       | ID '[' expresiones ']' '[' expresiones ']'
		       ;
		
declaracionVariable: tipoSimple identificador ';'
		           | tipoSimple '[' expresiones ']' identificador ';' 
		           | tipoSimple '[' expresiones ']' '[' expresiones ']' identificador ';'
		           ;

expresiones: expresiones ',' expresion
		   | expresion
		   ;	   
	  		     
expresion: llamadaVariable
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
         | expresion '.' expresion
         | '-' expresion %prec MENOS_UNARIO 
         | '!' expresion %prec NEGACION 
         | '(' expresion ')'
         | '(' tipoSimple ')' expresion
         | llamadaFuncion
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
