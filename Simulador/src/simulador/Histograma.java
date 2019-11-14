package simulador;

// Classe helper para gerar e imprimir histogramas simples, usamos para testar geradores aleatórios
public class Histograma {

  private int intervalo;
  private int nIntervalos;
  private long[] histograma;

  private String converteAsterisco(long n) {
    StringBuilder sb = new StringBuilder();
    for (int j = 0; j < n; j++) {
      sb.append('*');
    }
    return sb.toString();
  }

  Histograma(int intervalo, int nIntervalos) {
    setIntervalo(intervalo);
    setNumeroIntervalos(nIntervalos);
    criarHistograma();
  }

  public int getIntervalo() {
    return intervalo;
  }

  public void setIntervalo(int i) {
    intervalo = i;
  }

  public int getNumeroIntervalos() {
    return nIntervalos;
  }

  public void setNumeroIntervalos(int n) {
    nIntervalos = n;
  }

  public void criarHistograma() {
    this.histograma = new long[nIntervalos];
  }

  public void adicionarDado(long dado) {
    for (int i = 1; i <= nIntervalos; i++) {
      if (dado < intervalo * i) {
        histograma[i - 1]++;
        break;
      }
    }
  }

  public void imprimir() {
    for (int i = 0; i < histograma.length; i++) {
      String n = i + " : ";
      System.out.println(n + converteAsterisco(histograma[i]));
    }
  }

}
