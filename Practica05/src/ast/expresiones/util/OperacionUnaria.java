package ast.expresiones.util;

import java.util.ArrayList;
import java.util.List;

import ast.expresiones.Expresion;
import ast.sentencias.Sentencia;

public class OperacionUnaria extends ExpresionAbstracta {
	protected String operador;
	private Expresion expresion;
	protected List<Sentencia> sentencias = new ArrayList<Sentencia>();

	public OperacionUnaria(int linea, int columna, String operador,
			Expresion expresion) {
		super(linea, columna);
		this.expresion = expresion;
		this.operador = operador;
	}

	@Override
	public String toString() {
		return "OperacionUnaria [operador=" + operador + ", expresion="
				+ expresion + ", sentencias=" + sentencias + ", linea=" + linea
				+ ", columna=" + columna + "]";
	}

}
