package ast.expresiones.util;

import ast.expresiones.Expresion;
import ast.util.NodoPosicion;

public abstract class ExpresionAbstracta extends NodoPosicion
		implements Expresion {

	public ExpresionAbstracta(int linea, int columna) {
		super(linea, columna);
	}

	@Override
	public boolean getLValue() {
		return lValue;
	}

	@Override
	public void setLValue(boolean lValue) {
		this.lValue = lValue;
	}

}
