package ast.tipos.util;

import java.util.List;

import ast.tipos.Tipo;

public abstract class TipoAbstracto implements Tipo {

	@Override
	public boolean esLogico() {
		return false;
	}

	@Override
	public Tipo parentesis(List<Tipo> tipos) {
		return null;
	}

	@Override
	public int getLinea() {
		return 0;
	}

	@Override
	public int getColumna() {
		return 0;
	}

	@Override
	public Tipo aritmetica(Tipo tipo) {
		return null;
	}

	@Override
	public Tipo comparacion(Tipo tipo) {
		return null;
	}

	@Override
	public Tipo logica(Tipo tipo) {
		return null;
	}

	@Override
	public Tipo aritmetica() {
		return null;
	}

	@Override
	public Tipo logica() {
		return null;
	}

	@Override
	public Tipo promocionaA(Tipo tipo) {
		return null;
	}

	@Override
	public Tipo corchetes(Tipo tipo) {
		return null;
	}

	@Override
	public Tipo punto(String nombreCampo) {
		return null;
	}

	@Override
	public Tipo cast(Tipo tipo) {
		return null;
	}

	@Override
	public String sufijo() {
		throw new IllegalStateException();
	}

	@Override
	public Tipo Mayor(Tipo tipo) {
		throw new IllegalStateException();
	}

	@Override
	public int nBytes() {
		throw new IllegalStateException();
	}

	@Override
	public int offset(String id) {
		throw new IllegalStateException();
	}
}
