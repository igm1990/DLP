package ast.expresiones;

import ast.expresiones.util.ExpresionAbstracta;

public class AccesoCampo extends ExpresionAbstracta {
	private Expresion expresion;
	private String nombreCampo;

	public AccesoCampo(int linea, int columna, String nombreCampo,
			Expresion expresion) {
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

}
