package ast.tipos;

import java.util.ArrayList;
import java.util.List;

public class TipoRegistro implements Tipo {
	// Si eso cambiar a ArrayList
	private List<CampoRegistro> campos = new ArrayList<>();

	public TipoRegistro(List<CampoRegistro> campos) {
		for (CampoRegistro campoRegistro : campos) {
			this.campos.add(campoRegistro);
		}
	}

}
