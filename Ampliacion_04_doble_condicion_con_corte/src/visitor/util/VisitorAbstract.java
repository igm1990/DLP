package visitor.util;

import ast.Programa;
import ast.definiciones.*;
import ast.expresiones.*;
import ast.sentencias.*;
import ast.tipos.*;
import visitor.Visitor;

public class VisitorAbstract implements Visitor {

	@Override
	public Object visit(Programa programa, Object o) {
		for (Definicion d : programa.getDefiniciones())
			d.accept(this, o);
		return null;
	}

	@Override
	public Object visit(DefFuncion defFuncion, Object o) {
		defFuncion.getTipo().accept(this, o);
		for (DefVariable d : defFuncion.getVariablesLocales())
			d.accept(this, o);
		for (Sentencia s : defFuncion.getCuerpo())
			s.accept(this, o);
		return null;
	}

	@Override
	public Object visit(DefVariable defVariable, Object o) {
		defVariable.getTipo().accept(this, o);
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
	public Object visit(Aritmetica a, Object o) {
		a.getIzq().accept(this, o);
		a.getDer().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Cast cast, Object o) {
		cast.getTipoCast().accept(this, o);
		cast.getExpresion().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Comparacion c, Object o) {
		c.getIzq().accept(this, o);
		c.getDer().accept(this, o);
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
	public Object visit(Logica l, Object o) {
		l.getIzq().accept(this, o);
		l.getDer().accept(this, o);
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
	public Object visit(Variable variable, Object o) {
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
		for (Expresion e : invocacion.getExpresiones()) {
			e.accept(this, o);
		}
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
		return null;
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

	@Override
	public Object visit(CondicionDoble c, Object o) {
		c.getIzq().accept(this, o);
		c.getCen().accept(this, o);
		c.getDer().accept(this, o);
		return null;
	}
}