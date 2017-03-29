package visitor;

import ast.expresiones.*;
import ast.sentencias.*;
import ast.tipos.*;
import visitor.util.VisitorTemplate;

public class VisitorComprobacionTipos extends VisitorTemplate {

	@Override
	public Object visit(AccesoArray accesoArray, Object o) {
		super.visit(accesoArray, o);
		accesoArray.setLValue(true);
		return null;
	}

	@Override
	public Object visit(AccesoCampo accesoCampo, Object o) {
		super.visit(accesoCampo, o);
		accesoCampo.setLValue(true);
		return null;
	}

	@Override
	public Object visit(Aritmetica a, Object o) {
		super.visit(a, o);
		a.setLValue(false);
		a.setTipo(a.getIzq().getTipo().aritmetica(a.getDer().getTipo()));
		if (a.getTipo() == null)
			a.setTipo(new TipoError(a,
					"No son compatibles los tipos de los operandos"));
		return null;
	}

	@Override
	public Object visit(Cast cast, Object o) {
		super.visit(cast, o);
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
					"No se puede comparar " + c.getIzq().getTipo()
							+ " con el tipo " + c.getDer().getTipo()
							));
		return null;
	}

	@Override
	public Object visit(LiteralCaracter literalCaracter, Object o) {
		literalCaracter.setLValue(false);
		return null;
	}

	@Override
	public Object visit(LiteralEntero literalEntero, Object o) {
		literalEntero.setLValue(false);
		return null;
	}

	@Override
	public Object visit(LiteralReal literalReal, Object o) {
		literalReal.setLValue(false);
		return null;
	}

	@Override
	public Object visit(Logica logica, Object o) {
		super.visit(logica, o);
		logica.setLValue(false);
		return null;
	}

	@Override
	public Object visit(MenosUnario menosUnario, Object o) {
		super.visit(menosUnario, o);
		menosUnario.setLValue(false);
		return null;
	}

	@Override
	public Object visit(Negacion negacion, Object o) {
		super.visit(negacion, o);
		negacion.setLValue(false);
		return null;
	}

	@Override
	public Object visit(Variable variable, Object o) {
		variable.setLValue(true);
		return null;
	}

	@Override
	public Object visit(Asignacion asignacion, Object o) {
		super.visit(asignacion, o);
		if (!asignacion.getVariable().getLValue())
			new TipoError(asignacion,
					"Se esperaba LValue, asignaci�n -> " + this.getClass());
		return null;
	}

	@Override
	public Object visit(Lectura lectura, Object o) {
		for (Expresion e : lectura.getExpresiones()) {
			e.accept(this, o);
			if (!e.getLValue())
				new TipoError(lectura,
						"Se esperaba LValue, lectura -> " + this.getClass());
		}
		return null;
	}
}