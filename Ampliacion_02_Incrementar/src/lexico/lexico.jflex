// ************  Código a incluir ********************

package lexico;
import sintactico.Parser;

%%
// ************  Opciones ********************
//% debug // * Opción para depurar
%byaccj
%class Lexico
%public
%unicode
%line
%column

%{
// ************  Atributos y métodos ********************
// * Para acceder al número de línea (yyline es package)
public int getLine() { 
	// * Flex empieza en cero
	return yyline+1;
}

// * Para acceder al número de columna (yycolumn es package)
public int getColumn() { 
	// * Flex empieza en cero
	return yycolumn+1;
}

// * Valor semantico del token
private Object yylval;
public Object getYylval() {
	return this.yylval;
}

%}

// ************  Patrones (macros) ********************
ConstanteEntera = [0-9]+
ConstanteReal = ([0-9]+\.[0-9]+ | [0-9]*\.[0-9]+ | [0-9]+\.[0-9]* | [0-9]+)((e|E)("+"|"-")?[0-9]+)?
Identificador = [a-zA-ZáéíóúñÁÉÍÓÚÑ]+ [a-zA-Z0-9ZáéíóúñÁÉÍÓÚÑ_]*
Operador = ["<"">"";"":""("")""[""]""{""}"",""=""+""-""*""/"".""!""?""%"]
CaracterASCII = '"\\"[0-9]+' 
%%
// ************  Acciones ********************
"//" .*			        { } 
"/*" ~ "*/"        		{ }	
{ Operador }			{ this.yylval = new Character(yytext().charAt(0)); 
							return yytext().charAt(0); }	
"++"					{ this.yylval = yytext();
							return Parser.INCREMENTAR; }	
"=="					{ this.yylval = yytext();
							return Parser.IGUALDAD; }								
"!="					{ this.yylval = yytext();
							return Parser.DISTINTO; }													
">="					{ this.yylval = yytext();
							return Parser.MAYORIGUALQUE; }
"<="					{ this.yylval = yytext();
							return Parser.MENORIGUALQUE; }																												
"&&"			    	{ this.yylval = yytext();
							return Parser.Y; }
"||"                	{ this.yylval = yytext();
							return Parser.O; }
if   					{ this.yylval = yytext();
			           		return Parser.IF; }
int   					{ this.yylval = yytext();
							return Parser.INT; }
else                	{ this.yylval = yytext();
							return Parser.ELSE; }
read   					{ this.yylval = yytext();
							return Parser.READ; }
char   					{ this.yylval = yytext();
							return Parser.CHAR; }
void   					{ this.yylval = yytext();
							return Parser.VOID; }
main   					{ this.yylval = yytext();
							return Parser.MAIN; }
write   				{ this.yylval = yytext();
							return Parser.WRITE; }
while   				{ this.yylval = yytext();
							return Parser.WHILE; }
double   				{ this.yylval = yytext();
							return Parser.DOUBLE; }
struct   				{ this.yylval = yytext();
							return Parser.STRUCT; }
return   				{ this.yylval = yytext();
							return Parser.RETURN; }	
{ ConstanteEntera }	    { this.yylval = new Integer(yytext());
         			  		return Parser.CTE_ENTERA; }							
{ ConstanteReal }	    { this.yylval = new Double(yytext());
         			  		return Parser.CTE_REAL; }
{ Identificador }		{ this.yylval = new String(yytext());
							return Parser.ID; }	
'.'						{this.yylval = new Character(yytext().charAt(1));
							return Parser.CTE_CARACTER; }	
"'\\n'"					{ this.yylval = new Character('\n');
						  	return Parser.CTE_CARACTER; }
"'\\t'"					{ this.yylval = new Character('\t');
						  	return Parser.CTE_CARACTER; }							  						  								
{ CaracterASCII }		{ this.yylval = (char) Integer.parseInt(yytext().substring(2, yytext().length() - 1));
						  	return Parser.CTE_CARACTER; }											 	
[\n \r \t ]		        { }				
.						{ System.err.println("Ha fallado el token " + yytext()
							+ " en la linea " + getLine() + ", en la columna "
							+ getColumn()); }
