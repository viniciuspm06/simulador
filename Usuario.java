package simulador;

import java.util.ArrayList;

public class Usuario {

  private int tempoEnvio;
  private int tempoTerminoExecucao;
  private int totalArquivosEnviados;
  private int totalTempoServico;
  private int totalTempoEspera;
  private int totalTamanhoArquivo;
  private GeradorAleatorio ga;
  private String nome;
  private int qtdArquivoProcessamento;

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
    this.qtdArquivoProcessamento = totalArquivosEnviados;
  }

  public int getTotalTempoServico() {
    return this.totalTempoServico;
  }

  public void setTotalTempoServico(int totalTempoServico) {
    this.totalTempoServico = totalTempoServico;
  }

  public int getTotalTempoEspera() {
    return totalTempoEspera;
  }
  
  public int getTempoEnvio() {
	return tempoEnvio;
  }

  public void setTempoEnvio(int tempoEnvio) {
	this.tempoEnvio = tempoEnvio;
  }

  public ArrayList<Arquivo> enviaArquivos() {
    ArrayList<Arquivo> arquivos = Arquivo.gerarArquivosUpload(ga.getQtdArquivoAleatorio(), ga, this);
    setTotalArquivosEnviados(arquivos.size());
    return arquivos;
  }

  public synchronized void informaProcessamento(Arquivo arquivo) {
    totalTempoServico += arquivo.getTempoServico();
    totalTamanhoArquivo += arquivo.getNumLinhas();
    totalTempoEspera += arquivo.getTempoEspera();
    qtdArquivoProcessamento--;
    
    if(qtdArquivoProcessamento == 0) {
    	tempoTerminoExecucao = Simulador.relogio.getTempoAtual();
    }
  }
  
  public int calcularTempoEsperaUsuario() {
	  return tempoTerminoExecucao - tempoEnvio;
  }

  @Override
  public String toString() {
    return "|" + Relatorio.formatarPosicoes(nome) + "|"
        + Relatorio.formatarPosicoes(Integer.toString(totalArquivosEnviados)) + "|"
        + Relatorio.formatarPosicoes(Integer.toString(totalTempoServico)) + "|"
        + Relatorio.formatarPosicoes(Integer.toString(totalTamanhoArquivo)) + "|"
        + Relatorio.formatarPosicoes(Integer.toString(calcularTempoEsperaUsuario())) + '|';
  }

}
