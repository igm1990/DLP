package ast;

import visitor.Visitor;

/**
 * Interfaz para definir el tipo "NodoAST" del an�lizador lexico.
 */
public interface NodoAST {

	/**
	 * M�todo que devuelve el par�metro linea.
	 * 
	 * @return L�nea en la que se encuentra el token.
	 */
	public int getLinea();

	/**
	 * M�todo que devuelve el par�metro columna
	 * 
	 * @return Columna en la que se encuentra el token.
	 */
	public int getColumna();

	public Object accept(Visitor v, Object o);
}
