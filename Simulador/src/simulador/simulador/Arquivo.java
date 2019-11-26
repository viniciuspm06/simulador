package simulador;

import java.util.ArrayList;

public class Arquivo {

  private long numLinhas;
  private int tempoServico;
  private int tempoChegada;
  private int tempoEspera;
  private Usuario dono;

  public long getNumLinhas() {
    return numLinhas;
  }

  public void setNumLinhas(long numLinhas) {
    this.numLinhas = numLinhas;
  }

  public int getTempoServico() {
    return tempoServico;
  }

  public void setTempoServico(int tempoServico) {
    this.tempoServico = tempoServico;
  }

  public Usuario getDono() {
    return this.dono;
  }

  public void setDono(Usuario dono) {
    this.dono = dono;
  }

  public int getTempoChegada() {
    return tempoChegada;
  }

  public void setTempoChegada(int tempoChegada) {
    this.tempoChegada = tempoChegada;
  }

  public void setTempoEspera(int tInicioProc) {
    tempoEspera = tInicioProc - tempoChegada;
  }

  public int getTempoEspera() {
    return tempoEspera;
  }

  // funl�ao que monta um array list simulando o upload de varios arquivos para o
  // sistema
  public static ArrayList<Arquivo> gerarArquivosUpload(int qtd, GeradorAleatorio ga, Usuario dono) {

    ArrayList<Arquivo> arquivos = new ArrayList<Arquivo>();
    Arquivo a;
    // Tempo por linha (chute com base no dataset)
    double tempoPorLinha = 0.014;
    for (int i = 0; i < qtd; i++) {
      long numLinhas = (long) ga.getLinhasAleatorio();
      a = new Arquivo();
      a.setDono(dono);
      a.setNumLinhas(numLinhas);
      a.setTempoServico((int) Math.ceil(numLinhas * tempoPorLinha));
      arquivos.add(a);
    }

    return arquivos;
  }

  @Override
  public String toString() {
    return "Tamanho: " + numLinhas + " - Tempo Serviço: " + tempoServico + " - Tempo Chegada: " + tempoChegada
        + " - Tempo Espera: " + tempoEspera + " - Dono: " + dono.getNome();
  }

}
