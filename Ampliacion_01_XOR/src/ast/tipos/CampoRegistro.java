package ast.tipos;

import ast.util.NodoPosicion;
import visitor.Visitor;

public class CampoRegistro extends NodoPosicion {
	private String nombre;
	private int offset;
	private Tipo tipo;

	public CampoRegistro(int linea, int columna, String nombre, Tipo tipo) {
		super(linea, columna);
		this.nombre = nombre;
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "(" + nombre + " x " + tipo + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + offset;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CampoRegistro other = (CampoRegistro) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (offset != other.offset)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	public String getNombre() {
		return nombre;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public Tipo getTipo() {
		return tipo;
	}
}
