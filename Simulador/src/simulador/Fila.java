package simulador;

import java.util.ArrayList;

public class Fila {
	
	private ArrayList<Arquivo> arquivos;

	public ArrayList<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(ArrayList<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}
	
	public void addArquivo(Arquivo arquivo){
		arquivos.add(arquivo);
	}
	
	
	
}
