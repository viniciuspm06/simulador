package simulador;

public class Parametros {
  private String identificacao;
  private int replicacoes;
  private int tipoFila;
  private int velocidade;
  private int qtdServidores;
  private int qtdUsuarios;
  private int semente;
  
  
  
  public String getIdentificacao() {
	return identificacao;
  }

  public void setIdentificacao(String identificacao) {
	  this.identificacao = identificacao;
  }

  public int getReplicacoes() {
    return this.replicacoes;
  }

  public void setReplicacoes(int replicacoes) {
    this.replicacoes = replicacoes;
  }

  public int getTipoFila() {
    return this.tipoFila;
  }

  public void setTipoFila(int tipoFila) {
    this.tipoFila = tipoFila;
  }

  public int getVelocidade() {
    return this.velocidade;
  }

  public void setVelocidade(int velocidade) {
    this.velocidade = velocidade;
  }

  public int getQtdServidores() {
    return this.qtdServidores;
  }

  public void setQtdServidores(int qtdServidores) {
    this.qtdServidores = qtdServidores;
  }

  public int getQtdUsuarios() {
    return this.qtdUsuarios;
  }

  public void setQtdUsuarios(int qtdUsuarios) {
    this.qtdUsuarios = qtdUsuarios;
  }

  public int getSemente() {
    return this.semente;
  }

  public void setSemente(int semente) {
    this.semente = semente;
  }

}
