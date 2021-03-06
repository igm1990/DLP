package visitor;

import java.util.ArrayList;
import java.util.List;

import ast.definiciones.DefFuncion;
import ast.definiciones.DefVariable;
import ast.expresiones.*;
import ast.sentencias.*;
import ast.tipos.*;
import visitor.util.VisitorAbstract;

public class VisitorComprobacionTipos extends VisitorAbstract {

	@Override
	public Object visit(AccesoArray a, Object o) {
		super.visit(a, o);
		a.setLValue(true);
		a.setTipo(a.getIzq().getTipo().corchetes(a.getDer().getTipo()));
		if (a.getTipo() == null)
			a.setTipo(new TipoError(a, "Error tipo AccesoArray -> "
					+ a.getIzq() + " - " + a.getDer()));
		return null;
	}

	@Override
	public Object visit(AccesoCampo a, Object o) {
		super.visit(a, o);
		a.setLValue(true);
		a.setTipo(a.getExpresion().getTipo().punto(a.getNombreCampo()));
		if (a.getTipo() == null)
			a.setTipo(new TipoError(a, "Error tipo AccesoCampo -> "
					+ a.getExpresion().getTipo() + " - " + a.getNombreCampo()));
		return null;
	}

	@Override
	public Object visit(Aritmetica a, Object o) {
		super.visit(a, o);
		a.setLValue(false);
		a.setTipo(a.getIzq().getTipo().aritmetica(a.getDer().getTipo()));
		if (a.getTipo() == null)
			a.setTipo(new TipoError(a, "Error tipo Aritmetica ->" + a.getIzq()
					+ " - " + a.getDer()));
		return null;
	}

	@Override
	public Object visit(Cast cast, Object o) {
		super.visit(cast, o);
		cast.setTipo(cast.getExpresion().getTipo().cast(cast.getTipoCast()));
		if (cast.getTipo() == null)
			cast.setTipo(new TipoError(cast, "Error tipo Cast -> "
					+ cast.getExpresion().getTipo() + " - "
					+ cast.getTipoCast()));
		cast.setLValue(false);
		return null;
	}

	@Override
	public Object visit(Comparacion c, Object o) {
		super.visit(c, o);
		c.setLValue(false);
		c.setTipo(c.getIzq().getTipo().comparacion(c.getDer().getTipo()));
		if (c.getTipo() == null)
			c.setTipo(new TipoError(c,
					"Error tipo Comparacion: No se puede comparar "
							+ c.getIzq().getTipo() + " con el tipo "
							+ c.getDer().getTipo()));
		return null;
	}

	@Override
	public Object visit(LiteralCaracter literalCaracter, Object o) {
		literalCaracter.setLValue(false);
		literalCaracter.setTipo(TipoCaracter.getInstancia());
		return null;
	}

	@Override
	public Object visit(LiteralEntero literalEntero, Object o) {
		literalEntero.setLValue(false);
		literalEntero.setTipo(TipoEntero.getInstancia());
		return null;
	}

	@Override
	public Object visit(LiteralReal literalReal, Object o) {
		literalReal.setLValue(false);
		literalReal.setTipo(TipoReal.getInstancia());
		return null;
	}

	@Override
	public Object visit(Logica l, Object o) {
		super.visit(l, o);
		l.setLValue(false);
		l.setTipo(l.getIzq().getTipo().logica(l.getDer().getTipo()));
		if (l.getTipo() == null)
			l.setTipo(new TipoError(l, "Error tipo l�gico -> " + l.getIzq()
					+ " - " + l.getDer()));
		return null;
	}

	@Override
	public Object visit(MenosUnario m, Object o) {
		super.visit(m, o);
		m.setLValue(false);
		m.setTipo(m.getExpresion().getTipo().aritmetica());
		if (m.getTipo() == null)
			m.setTipo(new TipoError(m, "Error tipo MenosUnario"));
		return null;
	}

	@Override
	public Object visit(Negacion n, Object o) {
		super.visit(n, o);
		n.setLValue(false);
		n.setTipo(n.getExpresion().getTipo().logica());
		if (n.getTipo() == null)
			n.setTipo(new TipoError(n, "Error tipo Negacion"));
		return null;
	}

	@Override
	public Object visit(Variable v, Object o) {
		v.setLValue(true);
		v.setTipo(v.getDefinicion().getTipo());
		return null;
	}

	@Override
	public Object visit(Asignacion asignacion, Object o) {
		super.visit(asignacion, o);
		if (!asignacion.getVariable().getLValue())
			new TipoError(asignacion, "Se esperaba LValue, asignaci�n -> "
					+ this.getClass());
		asignacion.getVariable().setTipo(
				asignacion.getValor().getTipo()
						.promocionaA(asignacion.getVariable().getTipo()));
		if (asignacion.getVariable().getTipo() == null)
			asignacion.getVariable().setTipo(
					new TipoError(asignacion, "Error tipo asignacion"));
		return null;
	}

	@Override
	public Object visit(Lectura lectura, Object o) {
		for (Expresion e : lectura.getExpresiones()) {
			e.accept(this, o);
			if (!e.getLValue())
				new TipoError(lectura, "Se esperaba LValue, lectura -> "
						+ this.getClass());
		}
		return null;
	}

	@Override
	public Object visit(Return r, Object o) {
		super.visit(r, o);
		r.getExpresion().setTipo(
				r.getExpresion().getTipo().promocionaA((Tipo) o));
		if (r.getExpresion().getTipo() == null)
			new TipoError(r, "No coincide el tipo de retorno "
					+ r.getExpresion().getTipo()
					+ " con el tipo de la funcion " + ((Tipo) o));
		return null;
	}

	@Override
	public Object visit(DefFuncion defFuncion, Object o) {
		defFuncion.getTipo().accept(this, o);
		for (DefVariable d : defFuncion.getVariablesLocales())
			d.accept(this, o);
		for (Sentencia s : defFuncion.getCuerpo())
			s.accept(this, ((TipoFuncion) defFuncion.getTipo()).getRetorno());
		return null;
	}

	@Override
	public Object visit(SentenciaWhile sentenciaWhile, Object o) {
		sentenciaWhile.getCondicion().accept(this, o);
		if (!sentenciaWhile.getCondicion().getTipo().esLogico())
			sentenciaWhile.getCondicion().setTipo(
					new TipoError(sentenciaWhile.getCondicion(),
							"Error tipo senteciaWhile "));
		for (Sentencia e : sentenciaWhile.getSentencias())
			e.accept(this, o);
		return null;
	}

	@Override
	public Object visit(SentenciaIf if1, Object o) {
		if1.getCondicion().accept(this, o);
		if (!if1.getCondicion().getTipo().esLogico())
			if1.getCondicion()
					.setTipo(
							new TipoError(if1.getCondicion(),
									"Error tipo SentenciaIf "));
		for (Sentencia e : if1.getCuerpoIf())
			e.accept(this, o);
		for (Sentencia e : if1.getCuerpoElse())
			e.accept(this, o);
		return null;
	}

	@Override
	public Object visit(Invocacion invocacion, Object o) {
		List<Tipo> tipos = new ArrayList<>();
		invocacion.getVariable().accept(this, o);
		for (Expresion l : invocacion.getListaExamen()) {
			l.accept(this, o);
		}
		tipos = invocacion.examen();
		invocacion
				.setTipo(invocacion.getVariable().getTipo().parentesis(tipos));
		
		for (Expresion e : invocacion.getExpresiones()) {
			e.accept(this, o);
		}

		if (invocacion.getTipo() == null)
			invocacion.setTipo(new TipoError(invocacion,
					"Error tipo invocacion"));

		return null;
	}

	@Override
	public Object visit(ListaExamen l, Object o) {
		l.getExpresion().accept(this, o);
		l.setTipo(l.getExpresion().getTipo());
		if (l.getTipo() == null)
			l.setTipo(new TipoError(l, "Error tipo ListaExamen"));
		return null;
	}
}