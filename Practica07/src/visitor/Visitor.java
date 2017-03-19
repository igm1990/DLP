package visitor;

import ast.Programa;
import ast.definiciones.*;
import ast.expresiones.*;
import ast.sentencias.*;
import ast.tipos.CampoRegistro;
import ast.tipos.TipoArray;
import ast.tipos.TipoCaracter;
import ast.tipos.TipoEntero;
import ast.tipos.TipoFuncion;
import ast.tipos.TipoReal;
import ast.tipos.TipoRegistro;
import ast.tipos.TipoVoid;

public interface Visitor {
	public Object visit(Programa programa);

	public Object visit(DefVariable defVariable);

	public Object visit(DefFuncion defFuncion);

	public Object visit(AccesoArray accesoArray);

	public Object visit(AccesoCampo accesoCampo);

	public Object visit(Aritmetica aritmetica);

	public Object visit(Cast cast);

	public Object visit(Comparacion comparacion);

	public Object visit(LiteralCaracter literalCaracter);

	public Object visit(LiteralEntero literalEntero);

	public Object visit(Logica logica);

	public Object visit(MenosUnario menosUnario);

	public Object visit(Negacion negacion);

	public Object visit(Variable variable);

	public Object visit(Asignacion asignacion);

	public Object visit(Escritura escritura);

	public Object visit(Invocacion invocacion);

	public Object visit(Lectura lectura);

	public Object visit(Return return1);

	public Object visit(SentenciaIf if1);

	public Object visit(SentenciaWhile sentenciaWhile);

	public Object visit(CampoRegistro campoRegistro);

	public Object visit(TipoArray tipoArray);

	public Object visit(TipoCaracter tipoCaracter);

	public Object visit(TipoEntero tipoEntero);

	public Object visit(TipoFuncion tipoFuncion);

	public Object visit(TipoReal tipoReal);

	public Object visit(TipoRegistro tipoRegistro);

	public Object visit(TipoVoid tipoVoid);

}