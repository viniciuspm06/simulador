package simulador;

import java.util.ArrayList;

public class Usuario {

  private int totalArquivosEnviados;
  private int totalTempoServico;
  private GeradorAleatorio ga;
  private String nome;

  Usuario(String nome, GeradorAleatorio ga) {
    setNome(nome);
    setGeradorAleatorio(ga);
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setGeradorAleatorio(GeradorAleatorio ga) {
    this.ga = ga;
  }

  public int getTotalArquivosEnviados() {
    return this.totalArquivosEnviados;
  }

  public void setTotalArquivosEnviados(int totalArquivosEnviados) {
    this.totalArquivosEnviados = totalArquivosEnviados;
  }

  public int getTotalTempoServico() {
    return this.totalTempoServico;
  }

  public void setTotalTempoServico(int totalTempoServico) {
    this.totalTempoServico = totalTempoServico;
  }

  public ArrayList<Arquivo> enviaArquivos() {
    ArrayList<Arquivo> arquivos = Arquivo.gerarArquivosUpload(ga.getQtdArquivoAleatorio(), ga, this);
    setTotalArquivosEnviados(arquivos.size());
    return arquivos;
  }

  public synchronized void informaProcessamento(long qtdLinhas, int tempo) {
    //System.out.println("Processado arquivo do " + this.nome + " com " + qtdLinhas + " linhas no tempo de "
    //    + tempo / 1000 + " segundos");
	  System.out.println("Processado arquivo do " + this.nome + " com " + qtdLinhas + " linhas no tempo de "
			        + tempo + " milisegundos");
  }

  @Override
  public String toString() {
    return "Usuario " + nome + " com " + totalArquivosEnviados + " arquivos";
  }

}
