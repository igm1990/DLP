package visitor;

import ast.Programa;
import ast.definiciones.*;
import ast.expresiones.*;
import ast.sentencias.*;
import ast.tipos.CampoRegistro;
import ast.tipos.TipoArray;
import ast.tipos.TipoCaracter;
import ast.tipos.TipoEntero;
import ast.tipos.TipoError;
import ast.tipos.TipoFuncion;
import ast.tipos.TipoReal;
import ast.tipos.TipoRegistro;
import ast.tipos.TipoVoid;

public interface Visitor {
	public Object visit(Programa programa, Object o);

	public Object visit(DefVariable defVariable, Object o);

	public Object visit(DefFuncion defFuncion, Object o);

	public Object visit(AccesoArray accesoArray, Object o);

	public Object visit(AccesoCampo accesoCampo, Object o);

	public Object visit(Aritmetica aritmetica, Object o);

	public Object visit(Cast cast, Object o);

	public Object visit(Comparacion comparacion, Object o);

	public Object visit(LiteralCaracter literalCaracter, Object o);

	public Object visit(LiteralEntero literalEntero, Object o);

	public Object visit(LiteralReal literalReal, Object o);

	public Object visit(Logica logica, Object o);

	public Object visit(MenosUnario menosUnario, Object o);

	public Object visit(Negacion negacion, Object o);

	public Object visit(Variable variable, Object o);

	public Object visit(Asignacion asignacion, Object o);

	public Object visit(Escritura escritura, Object o);

	public Object visit(Invocacion invocacion, Object o);

	public Object visit(Lectura lectura, Object o);

	public Object visit(Return return1, Object o);

	public Object visit(SentenciaIf if1, Object o);

	public Object visit(SentenciaWhile sentenciaWhile, Object o);

	public Object visit(CampoRegistro campoRegistro, Object o);

	public Object visit(TipoArray tipoArray, Object o);

	public Object visit(TipoCaracter caracter, Object o);

	public Object visit(TipoEntero tipoEntero, Object o);

	public Object visit(TipoError error, Object o);

	public Object visit(TipoFuncion funcion, Object o);

	public Object visit(TipoReal real, Object o);

	public Object visit(TipoRegistro registro, Object o);

	public Object visit(TipoVoid tipoVoid, Object o);

}
