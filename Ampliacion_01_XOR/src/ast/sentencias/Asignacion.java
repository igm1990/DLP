package ast.sentencias;

import ast.expresiones.Expresion;
import ast.sentencias.util.SentenciaAbstracta;
import visitor.Visitor;

/**
 * Clase que simula en el an�lizador l�xico un token que es una asignaci�n.
 * 
 * @author Iv�n Gonz�lez Mahagamage
 *
 */
public class Asignacion extends SentenciaAbstracta {
	private Expresion variable;
	private Expresion valor;

	/**
	 * Constructor con par�metros.
	 * 
	 * @param linea
	 *            L�nea en la que se encuentra el lexema.
	 * @param columna
	 *            Columna en la que se encuentra el lexema.
	 * @param variable
	 *            Variable a la que se le asigna un valor.
	 * @param valor
	 *            Valor a asignar a la variable.
	 */
	public Asignacion(int linea, int columna, Expresion variable,
			Expresion valor) {
		super(linea, columna);
		this.variable = variable;
		this.valor = valor;
	}

	/**
	 * M�todo que devuelve el par�metro variable.
	 * 
	 * @return Variable a la que se le asigna un valor.
	 */
	public Expresion getVariable() {
		return variable;
	}

	/**
	 * M�todo que devuelve el par�metro valor.
	 * 
	 * @return Valor a asignar a la variable.
	 */
	public Expresion getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return "Asignacion [variable=" + variable + ", valor=" + valor
				+ ", linea=" + linea + ", columna=" + columna + "]";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}
}
