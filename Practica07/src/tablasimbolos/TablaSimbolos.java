package tablasimbolos;

import java.util.*;

import ast.definiciones.Definicion;

public class TablaSimbolos {

	private int ambito = 0;
	private List<Map<String, Definicion>> tabla;

	public TablaSimbolos() {
	}

	/*
	 * Incrementamos el ambito y creamos un nuevo map en la ultima posicion de
	 * la lista
	 */
	public void set() {
	}

	/*
	 * Lo contrario a set Decrementa el ambito y borra la ultima el ultimo map
	 */
	public void reset() {
	}

	// Meter en la tabla que diga el ambito de la lista de maps
	// en la ultima que has creado, getAmbito()
	// Cuando no puede insertar, esa variable ya esta declarada,
	// buscarAmbitoActual
	// 1 Busca si ya hay una variable
	// 2 si no la hay la insertar
	// 3 inserta el ambito
	// No lanzamos aqui el error, lanzarlo mejor en el visitor tiene toda la
	// informacion
	//
	public boolean insertar(Definicion simbolo) {
		return false;
	}

	/*
	 * Busca en todos los ambitos empezamos en el ultimo Si no encuentra
	 * devuelve null. Lanzas el error
	 */
	public Definicion buscar(String id) {
		return null;
	}

	public Definicion buscarAmbitoActual(String id) {
		return null;
	}
}