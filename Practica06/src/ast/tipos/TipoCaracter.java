package ast.tipos;

public class TipoCaracter implements Tipo {
	private static TipoCaracter instancia;

	public static TipoCaracter getInstancia() {
		if (instancia == null)
			instancia = new TipoCaracter();
		return instancia;
	}

	@Override
	public String toString() {
		return "TipoCaracter []";
	}

	@Override
	public int getLinea() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumna() {
		// TODO Auto-generated method stub
		return 0;
	}

}
