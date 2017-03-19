package ast.definiciones;

import ast.NodoAST;
import ast.tipos.Tipo;

public interface Definicion extends NodoAST {

	public Tipo getTipo();

	public String getNombre();

}
