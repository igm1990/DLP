package ast.expresiones;

import ast.expresiones.util.ExpresionAbstracta;
import ast.tipos.Tipo;

public class Cast extends ExpresionAbstracta {
	private Tipo tipoCast;
	private Expresion expresion;

	public Cast(int linea, int columna, Tipo tipoCast, Expresion expresion) {
		super(linea, columna);
		this.tipoCast = tipoCast;
		this.expresion = expresion;
	}

	@Override
	public String toString() {
		return "Cast [tipoCast=" + tipoCast + ", expresion=" + expresion
				+ ", linea=" + linea + ", columna=" + columna + "]";
	}

}
