package tablasimbolos;

import java.util.*;

import ast.definiciones.Definicion;

public class TablaSimbolos {

	private int ambito = 0;
	private List<Map<String, Definicion>> tabla = new ArrayList<>();

	public TablaSimbolos() {
		tabla.add(new HashMap<>());
	}

	/**
	 * Incrementamos el ambito y creamos un nuevo map en la ultima posicion de
	 * la lista
	 */
	public void set() {
		tabla.add(new HashMap<>());
		ambito++;
	}

	/**
	 * Lo contrario a set Decrementa el ambito y borra la ultima el ultimo map
	 */
	public void reset() {
		tabla.remove(ambito);
		ambito--;
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
		Map<String, Definicion> aux = tabla.get(ambito);
		if (!aux.containsKey(simbolo.getNombre())) {
			aux.put(simbolo.getNombre(), simbolo);
			simbolo.setAmbito(ambito);
			return true;
		}
		return false;
	}

	/**
	 * Busca en todos los ambitos empezamos en el ultimo Si no encuentra
	 * devuelve null. Lanzas el error
	 */
	public Definicion buscar(String id) {
		for (int i = ambito; i >= 0; i--) {
			if (tabla.get(i).containsKey(id))
				return tabla.get(i).get(id);
		}
		return null;
	}

	public Definicion buscarAmbitoActual(String id) {
		if (tabla.get(ambito).containsKey(id))
			return tabla.get(ambito).get(id);
		return null;
	}
}
