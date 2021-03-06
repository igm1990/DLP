package ast.sentencias.util;

import java.util.ArrayList;
import java.util.List;

import ast.expresiones.Expresion;
import ast.sentencias.Sentencia;
import ast.util.NodoPosicion;

public class CompositeExpresiones extends NodoPosicion implements Sentencia {
	protected List<Expresion> expresiones;

	/**
	 * Constructor con par�metros.
	 * 
	 * @param linea
	 *            L�nea en la que se encuentra el lexema.
	 * @param columna
	 *            Columna en la que se encuentra el lexema.
	 */
	public CompositeExpresiones(int linea, int columna) {
		super(linea, columna);
		expresiones = new ArrayList<>();
	}

	/**
	 * M�todo que devuelve el par�metro expresiones.
	 * 
	 * @return Lista de expresiones.
	 */
	public List<Expresion> getExpresiones() {
		return expresiones;
	}
}
