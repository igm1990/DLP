package ast.expresiones;

import ast.expresiones.util.ExpresionAbstracta;
import visitor.Visitor;

public class AccesoCampo extends ExpresionAbstracta {
	private Expresion expresion;
	private String nombreCampo;

	public AccesoCampo(int linea, int columna, Expresion expresion,
			String nombreCampo) {
		super(linea, columna);
		this.nombreCampo = nombreCampo;
		this.expresion = expresion;
	}

	@Override
	public String toString() {
		return "AccesoCampo [expresion=" + expresion + ", nombreCampo="
				+ nombreCampo + ", linea=" + linea + ", columna=" + columna
				+ "]";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public Expresion getExpresion() {
		return expresion;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}

}
