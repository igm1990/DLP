package visitor;

import ast.definiciones.DefFuncion;
import ast.definiciones.DefVariable;
import ast.expresiones.Variable;
import ast.sentencias.Sentencia;
import ast.tipos.TipoError;
import tablasimbolos.TablaSimbolos;
import visitor.util.VisitorTemplate;

public class VisitorIdentificacion extends VisitorTemplate {

	private TablaSimbolos tabla = new TablaSimbolos();

	@Override
	public Object visit(DefFuncion defFuncion, Object o) {
		if (tabla.buscar(defFuncion.getNombre()) == null)
			tabla.insertar(defFuncion);
		else
			new TipoError(defFuncion,
					"Funcion ya declarada -> " + this.getClass());
		tabla.set();
		defFuncion.getTipo().accept(this, o);
		for (DefVariable d : defFuncion.getVariablesLocales())
			d.accept(this, o);
		for (Sentencia s : defFuncion.getCuerpo())
			s.accept(this, o);
		tabla.reset();
		return null;
	}

	@Override
	public Object visit(DefVariable defVariable, Object o) {
		if (tabla.buscarAmbitoActual(defVariable.getNombre()) == null)
			tabla.insertar(defVariable);
		else
			new TipoError(defVariable, "Variable ya declarada, DefVariable "
					+ defVariable.getNombre() + " -> " + this.getClass());
		defVariable.getTipo().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Variable variable, Object o) {
		if (tabla.buscar(variable.getClave()) == null)
			new TipoError(variable, "Variable no declarada, Variable "
					+ variable.getClave() + " -> " + this.getClass());
		return null;
	}
}
