package ast.definiciones;

import ast.tipos.Tipo;

public class DefVariable extends DefinicionAbstracta {
	private int ambito;
	private int OFFSET;

	public DefVariable(int linea, int columna, String nombre, Tipo tipo) {
		super(linea, columna, nombre, tipo); 
	}

	@Override
	public String toString() {
		return "DefVariable [ambito=" + ambito + ", OFFSET=" + OFFSET + ", linea=" + linea + ", columna=" + columna
				+ ", getTipo()=" + getTipo() + ", getNombre()=" + getNombre() + ", getLinea()=" + getLinea()
				+ ", getColumna()=" + getColumna() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}


	
	

}
