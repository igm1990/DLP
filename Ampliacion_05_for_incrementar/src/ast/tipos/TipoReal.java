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
		return "real";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	@Override
	public Tipo aritmetica(Tipo tipo) {
		if (tipo instanceof TipoReal || tipo instanceof TipoEntero)
			return tipo;
		if (tipo instanceof TipoCaracter)
			return this;
		return null;
	}

	@Override
	public Tipo aritmetica() {
		return this;
	}

	@Override
	public Tipo promocionaA(Tipo tipo) {
		if (tipo instanceof TipoReal)
			return this;
		return null;
	}

	@Override
	public Tipo cast(Tipo tipo) {
		return tipo;
	}

	@Override
	public int nBytes() {
		return 4;
	}

	@Override
	public String sufijo() {
		return "f";
	}

	@Override
	public Tipo Mayor(Tipo tipo) {
		return this;
	}
}
