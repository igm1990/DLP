package ast.expresiones;

/**
 * Clase que simula en el an�lizador l�xico un token que es una operaci�n
 * aritm�tica.
 * 
 * @author Iv�n Gonz�lez Mahagamage
 *
 */
public class Aritmetica extends OperacionBinaria {

	public Aritmetica(int linea, int columna, Expresion izq, String operador,
			Expresion der) {
		super(linea, columna, izq, operador, der);
		// TODO Auto-generated constructor stub
	}

}