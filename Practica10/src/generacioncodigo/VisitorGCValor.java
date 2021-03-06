package generacioncodigo;

import ast.expresiones.Aritmetica;
import ast.expresiones.Cast;
import ast.expresiones.Comparacion;
import ast.expresiones.LiteralCaracter;
import ast.expresiones.LiteralEntero;
import ast.expresiones.LiteralReal;
import ast.expresiones.Logica;
import ast.expresiones.Negacion;
import ast.expresiones.Variable;
import ast.tipos.Tipo;

public class VisitorGCValor extends AbstractVisitorGC {
	private static VisitorGCValor valor;

	private GeneradorCodigo GC;
	private VisitorGCDireccion direccion;

	public static VisitorGCValor getInstance(String entrada, String salida) {
		if (valor == null)
			valor = new VisitorGCValor(entrada, salida);
		return valor;
	}

	public VisitorGCValor(String entrada, String salida) {
		GC = GeneradorCodigo.getInstancia(entrada, salida);
		direccion = VisitorGCDireccion.getInstance(entrada, salida);
	}

	@Override
	public Object visit(LiteralCaracter c, Object o) {
		GC.push(c.getValor());
		return null;
	}

	@Override
	public Object visit(LiteralEntero c, Object o) {
		GC.push(c.getValor());
		return null;
	}

	@Override
	public Object visit(LiteralReal c, Object o) {
		GC.push(c.getValor());
		return null;
	}

	@Override
	public Object visit(Variable v, Object o) {
		v.accept(direccion, o);
		GC.load(v.getTipo().sufijo());
		return null;
	}

	@Override
	public Object visit(Cast c, Object o) {
		c.getExpresion().accept(this, o);
		GC.convertirA(c.getExpresion().getTipo(), c.getTipoCast());
		return null;
	}

	public Object visit(Aritmetica a, Object o) {
		a.getIzq().accept(this, o);
		GC.convertirA(a.getIzq().getTipo(), a.getTipo());
		a.getDer().accept(this, o);
		GC.convertirA(a.getDer().getTipo(), a.getTipo());
		GC.aritmetrica(a.getTipo(), a.getOperador());
		return null;
	}

	public Object visit(Comparacion a, Object o) {
		Tipo mayor = a.getIzq().getTipo().Mayor(a.getDer().getTipo());
		a.getIzq().accept(this, o);
		GC.convertirA(a.getIzq().getTipo(), mayor);
		a.getDer().accept(this, o);
		GC.convertirA(a.getDer().getTipo(), mayor);
		GC.comparacion(a.getTipo(), a.getOperador());
		return null;
	}

	public Object visit(Logica a, Object o) {
		a.getIzq().accept(this, o);
		a.getDer().accept(this, o);
		GC.logica(a.getOperador());
		return null;
	}

	public Object visit(Negacion a, Object o) {
		a.getExpresion().accept(this, o);
		GC.logica(a.getOperador());
		return null;
	}

}
