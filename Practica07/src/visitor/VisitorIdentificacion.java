package visitor;

import ast.Programa;
import ast.definiciones.DefFuncion;
import ast.definiciones.DefVariable;
import ast.definiciones.Definicion;
import ast.expresiones.AccesoArray;
import ast.expresiones.AccesoCampo;
import ast.expresiones.Aritmetica;
import ast.expresiones.Cast;
import ast.expresiones.Comparacion;
import ast.expresiones.Expresion;
import ast.expresiones.LiteralCaracter;
import ast.expresiones.LiteralEntero;
import ast.expresiones.LiteralReal;
import ast.expresiones.Logica;
import ast.expresiones.MenosUnario;
import ast.expresiones.Negacion;
import ast.expresiones.Variable;
import ast.sentencias.Asignacion;
import ast.sentencias.Escritura;
import ast.sentencias.Invocacion;
import ast.sentencias.Lectura;
import ast.sentencias.Return;
import ast.sentencias.Sentencia;
import ast.sentencias.SentenciaIf;
import ast.sentencias.SentenciaWhile;
import ast.tipos.CampoRegistro;
import ast.tipos.TipoArray;
import ast.tipos.TipoCaracter;
import ast.tipos.TipoEntero;
import ast.tipos.TipoError;
import ast.tipos.TipoFuncion;
import ast.tipos.TipoReal;
import ast.tipos.TipoRegistro;
import ast.tipos.TipoVoid;
import tablasimbolos.TablaSimbolos;

public class VisitorIdentificacion implements Visitor {

	private TablaSimbolos tabla = new TablaSimbolos();

	@Override
	public Object visit(Programa programa, Object o) {
		for (Definicion d : programa.getDefiniciones())
			d.accept(this, o);
		return null;
	}

	@Override
	public Object visit(DefFuncion defFuncion, Object o) {
		if (tabla.buscar(defFuncion.getNombre()) == null)
			tabla.insertar(defFuncion);
		else
			new TipoError(defFuncion, "Funcion ya declarada");
		tabla.set();
		defFuncion.getTipo().accept(this, o);
		for (DefVariable d : defFuncion.getVariablesLocales())
			d.accept(this, o);
		for (Sentencia s : defFuncion.getCuerpo())
			s.accept(this, o);
		tabla.reset();
		return null;
	}

	@Override
	public Object visit(DefVariable defVariable, Object o) {
		if (tabla.buscarAmbitoActual(defVariable.getNombre()) == null)
			tabla.insertar(defVariable);
		else
			new TipoError(defVariable, "Variable ya declarada "
					+ defVariable.getNombre() + " <- identificación");
		defVariable.getTipo().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Variable variable, Object o) {
		if (tabla.buscar(variable.getClave()) == null)
			new TipoError(variable, "Variable ya declarada "
					+ variable.getClave() + " <- identificación");
		return null;
	}

	@Override
	public Object visit(AccesoArray accesoArray, Object o) {
		accesoArray.getIzq().accept(this, o);
		accesoArray.getDer().accept(this, o);
		return null;
	}

	@Override
	public Object visit(AccesoCampo accesoCampo, Object o) {
		accesoCampo.getExpresion().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Aritmetica aritmetica, Object o) {
		aritmetica.getIzq().accept(this, o);
		aritmetica.getDer().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Cast cast, Object o) {
		cast.getTipoCast().accept(this, o);
		cast.getExpresion().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Comparacion comparacion, Object o) {
		comparacion.getIzq().accept(this, o);
		comparacion.getDer().accept(this, o);
		return null;
	}

	@Override
	public Object visit(LiteralCaracter literalCaracter, Object o) {
		return null;
	}

	@Override
	public Object visit(LiteralEntero literalEntero, Object o) {
		return null;
	}

	@Override
	public Object visit(LiteralReal literalReal, Object o) {
		return null;
	}

	@Override
	public Object visit(Logica logica, Object o) {
		logica.getIzq().accept(this, o);
		logica.getDer().accept(this, o);
		return null;
	}

	@Override
	public Object visit(MenosUnario menosUnario, Object o) {
		menosUnario.getExpresion().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Negacion negacion, Object o) {
		negacion.getExpresion().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Asignacion asignacion, Object o) {
		asignacion.getVariable().accept(this, o);
		asignacion.getValor().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Escritura escritura, Object o) {
		for (Expresion e : escritura.getExpresiones())
			e.accept(this, o);
		return null;
	}

	@Override
	public Object visit(Invocacion invocacion, Object o) {
		invocacion.getVariable().accept(this, o);
		for (Expresion e : invocacion.getExpresiones())
			e.accept(this, o);
		return null;
	}

	@Override
	public Object visit(Lectura lectura, Object o) {
		for (Expresion e : lectura.getExpresiones()) {
			e.accept(this, o);
		}
		return null;
	}

	@Override
	public Object visit(Return return1, Object o) {
		return1.getExpresion().accept(this, o);
		return o;
	}

	@Override
	public Object visit(SentenciaIf if1, Object o) {
		if1.getCondicion().accept(this, o);
		for (Sentencia e : if1.getCuerpoIf())
			e.accept(this, o);
		for (Sentencia e : if1.getCuerpoElse())
			e.accept(this, o);
		return null;
	}

	@Override
	public Object visit(SentenciaWhile sentenciaWhile, Object o) {
		sentenciaWhile.getCondicion().accept(this, o);
		for (Sentencia e : sentenciaWhile.getSentencias())
			e.accept(this, o);
		return null;
	}

	@Override
	public Object visit(CampoRegistro campoRegistro, Object o) {
		campoRegistro.getTipo().accept(this, o);
		return null;
	}

	@Override
	public Object visit(TipoArray tipoArray, Object o) {
		tipoArray.getTipo().accept(this, o);
		return null;
	}

	@Override
	public Object visit(TipoCaracter caracter, Object o) {
		return null;
	}

	@Override
	public Object visit(TipoEntero tipoEntero, Object o) {
		return null;
	}

	@Override
	public Object visit(TipoError error, Object o) {
		return null;
	}

	@Override
	public Object visit(TipoFuncion funcion, Object o) {
		for (DefVariable v : funcion.getParametros())
			v.accept(this, o);
		funcion.getRetorno().accept(this, o);
		return null;
	}

	@Override
	public Object visit(TipoReal real, Object o) {
		return null;
	}

	@Override
	public Object visit(TipoRegistro registro, Object o) {
		for (CampoRegistro c : registro.getCampos())
			c.accept(this, o);
		return null;
	}

	@Override
	public Object visit(TipoVoid tipoVoid, Object o) {
		return null;
	}
}
