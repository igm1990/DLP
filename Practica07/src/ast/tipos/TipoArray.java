package ast.tipos;

import visitor.Visitor;

public class TipoArray implements Tipo {
	private int tama�o;
	private Tipo tipo;

	public TipoArray(int tama�o, Tipo tipo) {
		this.tama�o = tama�o;
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "TipoArray [tama�o=" + tama�o + ", tipo=" + tipo + "]";
	}

	@Override
	public int getLinea() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumna() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public int getTama�o() {
		return tama�o;
	}

	public Tipo getTipo() {
		return tipo;
	}

}
