package ast.tipos;

public class TipoVoid implements Tipo {
	private static TipoVoid instancia;

	public static TipoVoid getInstancia() {
		if (instancia == null)
			instancia = new TipoVoid();
		return instancia;
	}

	@Override
	public String toString() {
		return "TipoVoid []";
	}

}
