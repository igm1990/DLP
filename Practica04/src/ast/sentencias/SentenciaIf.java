package ast.sentencias;

import java.util.ArrayList;
import java.util.List;

import ast.expresiones.Expresion;
import ast.sentencias.util.SentenciaAbstracta;

public class SentenciaIf extends SentenciaAbstracta {
	private Expresion condicion;
	private List<Sentencia> cuerpoIf = new ArrayList<>();
	private List<Sentencia> cuerpoElse = new ArrayList<>();

	public SentenciaIf(int linea, int columna, Expresion condicion,
			List<Sentencia> cuerpoIf, List<Sentencia> cuerpoElse) {
		super(linea, columna);
		this.condicion = condicion;
		this.cuerpoIf = cuerpoIf;
		this.cuerpoElse = cuerpoElse;
	}

	@Override
	public String toString() {
		return "SentenciaIf [condicion=" + condicion + ", cuerpoIf=" + cuerpoIf
				+ ", cuerpoElse=" + cuerpoElse + ", linea=" + linea
				+ ", columna=" + columna + "]";
	}

}
