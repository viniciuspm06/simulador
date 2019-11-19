package simulador;

import java.util.ArrayList;

public class Arquivo {
	
	private long numLinhas;
	private int tempoServico;
	
	public long getNumLinhas() {
		return numLinhas;
	}
	public void setNumLinhas(int num_linhas) {
		this.numLinhas = num_linhas;
	}
	public int getTempoServico() {
		return tempoServico;
	}
	public void setTempoServico(int tempo_servico) {
		this.tempoServico = tempo_servico;
	}
	
	//funlçao que monta um array list simulando o upload de varios arquivos para o sistema
	public static ArrayList<Arquivo> gerarArquivosUpload(int qtd) {
		
		ArrayList<Arquivo> arquivos = new ArrayList();
		Arquivo a;
		
		for(int i = 0; i < qtd; i++) {
			a = new Arquivo();
			a.numLinhas = Simulador.ga.getLinhasAleatorio();
			//adicionar tempo de serviço conforme num de linhas
			arquivos.add(a);
		}
		
		return arquivos;	
	}
	
}
