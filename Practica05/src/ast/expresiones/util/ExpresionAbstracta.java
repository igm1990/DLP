package ast.expresiones.util;

import ast.expresiones.Expresion;
import ast.util.NodoPosicion;

public class ExpresionAbstracta extends NodoPosicion implements Expresion {
	private boolean LValue;

	public ExpresionAbstracta(int linea, int columna) {
		super(linea, columna);
	}

	public boolean getLValue() {
		return LValue;
	}

	public void setLValue(boolean lValue) {
		LValue = lValue;
	}

}
