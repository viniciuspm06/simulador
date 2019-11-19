package simulador;

public class Servidor {
	
	private Fila fila;
	
	public void executarArquivo() {
		
	}

  public static void testeAleatorio() {
    // Colocando seed qualquer = teste = 1
    GeradorAleatorio ga = new GeradorAleatorio(1);
    Histograma histograma = new Histograma(1000, 30);
    // Simulando 182 arquivos, como no dataset
    for (int i = 0; i < 182; i++) {
      // Pegando numero aletório de linhas geradas usando distribuição exponencial
      long number = ga.getLinhasAleatorio();
      histograma.adicionarDado(number);
    }
    System.out.println("Histograma:");
    histograma.imprimir();
  }

  public static void main(String[] args) {
    testeAleatorio();
  }

}
