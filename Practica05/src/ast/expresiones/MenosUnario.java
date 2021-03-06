package ast.expresiones;

import ast.expresiones.util.OperacionUnaria;

/**
 * Clase que simula en el an�lizador l�xico un token que es un menos unario.
 * (Vuelve a negativo el valor de la expresi�n a su derecha.)
 * 
 * @author Iv�n Gonz�lez Mahagamage
 *
 */
public class MenosUnario extends OperacionUnaria {

	public MenosUnario(int linea, int columna, String operador,
			Expresion expresion) {
		super(linea, columna, operador, expresion);
	}

	@Override
	public String toString() {
		return "MenosUnario [operador=" + operador + ", sentencias="
				+ sentencias + ", linea=" + linea + ", columna=" + columna
				+ "]";
	}

}
