package ast.tipos;

import ast.tipos.util.TipoAbstracto;
import visitor.Visitor;

public class TipoReal extends TipoAbstracto implements Tipo {
	private static TipoReal instancia;

	public static TipoReal getInstancia() {
		if (instancia == null)
			instancia = new TipoReal();
		return instancia;
	}

	@Override
	public String toString() {
		return "TipoReal []";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	@Override
	public boolean esLogico() {
		return true;
	}

	@Override
	public Tipo aritmetica() {
		return this;
	}

	@Override
	public Tipo aritmetica(Tipo tipo) {
		if (tipo instanceof TipoReal || tipo instanceof TipoEntero)
			return this;
		if (tipo instanceof TipoCaracter)
			return this;
		return null;
	}

	@Override
	public Tipo logica() {
		return this;
	}

	@Override
	public Tipo comparacion(Tipo tipo) {
		if (tipo instanceof TipoReal || tipo instanceof TipoEntero
				|| tipo instanceof TipoCaracter)
			return this;
		return null;
	}
}