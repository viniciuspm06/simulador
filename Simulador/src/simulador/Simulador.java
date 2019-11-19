package simulador;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Simulador {
	
	public static GeradorAleatorio ga;
	
	public static void main(String args[]) {
		
		ga = new GeradorAleatorio(System.currentTimeMillis());
		
		try {
			int qtdReplicacoes = 1;
			int qtdUsuarios = 3;
			
			//GeradorAleatorio ga = new GeradorAleatorio(System.currentTimeMillis());
			
			//quantidade de replicações que serão feitas nos testes
			for(int i =0; i< qtdReplicacoes; i++){
							
				//gerar arquivos com tamanhos aletórios
				//int qtdArquivos = ga.getQtdArquivoAleatorio();
				//teste com geração de arquivos aleatorioamente
				ArrayList<Arquivo> arquivos = Arquivo.gerarArquivosUpload(ga.getQtdArquivoAleatorio());
				
				teste(arquivos);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void teste(ArrayList<Arquivo> arquivos) {
		int i = 0;
		for(Arquivo a: arquivos) {
			i++;
			System.out.println("Arquivo: " + i + " -- Numero de Linhas:" + a.getNumLinhas());
		}
	}

}
