package simulador;

import java.util.Random;

public class GeradorAleatorio {

  private long semente;
  private Random rng;

  GeradorAleatorio() {
    this.rng = new Random();
  }

  GeradorAleatorio(long semente) {
    this.rng = new Random();
    setSemente(semente);
  }

  public long getSemente() {
    return semente;
  }

  public void setSemente(long semente) {
    this.semente = semente;
    this.rng.setSeed(semente);
  }

  public int getAleatorio(int limite) {
    return this.rng.nextInt(limite);
  }

  public double getAleatorio() {
    return this.rng.nextDouble();
  }

  public long getLinhasAleatorio() {
    double numeroAleatorio = getAleatorio();
    // Mexer na taxa para alterar o comportamento da distribuiÃ§Ã£o
    double taxa = -0.0002;
    return (long) Math.ceil((Math.log(1 - numeroAleatorio) / taxa));
  }
  
  
  //função para gerar uma quantidade de arquivos aleatório por usuario
  public int getQtdArquivoAleatorio() {
	  
	  double media = 15;
	  double variancia = 5; 
	  //qtde de arquivos enviados por usuario
	  return (int) Math.ceil(media + rng.nextGaussian() * variancia);
	  
  }

}
