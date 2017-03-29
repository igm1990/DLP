package ast.tipos;

import java.util.List;

import ast.NodoAST;

public interface Tipo extends NodoAST {
	public boolean esLogico();

	public Tipo aritmetica(Tipo tipo);

	public Tipo comparacion(Tipo tipo);

	public Tipo logica(Tipo tipo);

	public Tipo aritmetica();

	public Tipo logica();

	public Tipo promocionaA(Tipo tipo);

	public Tipo parentesis(List<Tipo> tipos);

	public Tipo corchetes(Tipo tipo);

	// solo accesocampo
	public Tipo punto(String nombreCampo);

	// reutiulizar uno para el cast
	// el tipo de unsa sentencia return es compatible con el tipo de retorno
	// definido
}
