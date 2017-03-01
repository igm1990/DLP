package ast.expresiones;

public class Comparacion extends OperacionBinaria {

	public Comparacion(int linea, int columna, Expresion izq, String operador,
			Expresion der) {
		super(linea, columna, izq, operador, der);
	}

	@Override
	public String toString() {
		return "Comparacion [operador=" + operador + ", linea=" + linea + ", columna=" + columna + "]";
	}

	
}
