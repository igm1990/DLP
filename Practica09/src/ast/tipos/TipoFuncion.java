package ast.tipos;

import java.util.ArrayList;
import java.util.List;

import ast.definiciones.DefVariable;
import ast.tipos.util.TipoAbstracto;
import visitor.Visitor;

public class TipoFuncion extends TipoAbstracto {
	private List<DefVariable> parametros = new ArrayList<>();
	private Tipo retorno;

	public TipoFuncion(List<DefVariable> parametros, Tipo retorno) {
		for (DefVariable defVariable : parametros) {
			this.parametros.add(defVariable);
		}
		this.retorno = retorno;
	}

	@Override
	public String toString() {
		return "TipoFuncion [parametros=" + parametros + ", retorno=" + retorno
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((parametros == null) ? 0 : parametros.hashCode());
		result = prime * result + ((retorno == null) ? 0 : retorno.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoFuncion other = (TipoFuncion) obj;
		if (parametros == null) {
			if (other.parametros != null)
				return false;
		} else if (!parametros.equals(other.parametros))
			return false;
		if (retorno == null) {
			if (other.retorno != null)
				return false;
		} else if (!retorno.equals(other.retorno))
			return false;
		return true;
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public List<DefVariable> getParametros() {
		return parametros;
	}

	public Tipo getRetorno() {
		return retorno;
	}

	@Override
	public Tipo parentesis(List<Tipo> tipos) {
		if (tipos.size() != this.getParametros().size())
			return null;
		for (int i = 0; i < tipos.size(); i++) {
			if (tipos.get(i)
					.promocionaA(getParametros().get(i).getTipo()) == null)
				return null;
		}
		return this.getRetorno();
	}

	@Override
	public int nBytes() {
		return retorno.nBytes();
	}
}
