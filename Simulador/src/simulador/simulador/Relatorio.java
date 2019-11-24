package simulador;

import java.util.ArrayList;

public class Relatorio {
	private int metricaTempoTotalServico;
	private ArrayList<Usuario> usuarios;
	
	public Relatorio() {
	}
	
	public void setTempoTotalServico(int tempoTotalServico) {
		this.metricaTempoTotalServico = tempoTotalServico;
	}
	
	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void calcularMetricas() {
		//total linhas por usuario
		//total tempo de serviço arquivos
		//total tempo de espera
		//tempo total de processamento dos arquivos
		
		//tempo total de processamento dos arquivos 
		//sem contar os tempos de ociosidade
	}
	
	public void exportar() {
		
		for(Usuario u: usuarios) {
			Simulador.imprime(u.toString());
		}
		
	}
	
	
}
