package simulador;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Relatorio {
  private int metricaTempoTotalServico; // Tempo total dos servidores para processar a fila
  private int metricaTempoTotalEspera; // Tempo total de espera enquanto os arquivos processavam, queremos reduzir
  private int metricaTotalArquivosProcessados; // Total de arquivos processados
  private double metricaTempoMedioDaFila; // Tempo medio para o arquivo processado, queremos reduzir
  private ArrayList<Double> metricaUtilizacao; // Indica a % de utilizacao de cada servidor, quanto maior, melhor
  private ArrayList<String> linhasResultado;
  private String caminho;

  public Relatorio(String caminho) {
    this.caminho = caminho;
    linhasResultado = new ArrayList<String>();
    metricaUtilizacao = new ArrayList<Double>();
  }

  public void setTempoTotalServico(int tempoTotalServico) {
    this.metricaTempoTotalServico = tempoTotalServico;
  }

  public void setTempoTotalEspera(int tempoTotalEspera) {
    metricaTempoTotalEspera = tempoTotalEspera;
  }

  public void setTotalArquivosProcessados(int totalArquivosProcessados) {
    metricaTotalArquivosProcessados = totalArquivosProcessados;
  }

  public void setTempoMedioFila(double tempoMedioFila) {
    metricaTempoMedioDaFila = tempoMedioFila;
  }

  public void adicionaServidor(double utilizacao) {
    metricaUtilizacao.add(utilizacao);
  }

  public synchronized void atualizaUtilizacao(int index, double utilizacao) {
    metricaUtilizacao.set(index, utilizacao);
  }

  public void addResultado(String identificacao, int replicacao, ArrayList<Usuario> usuarios) {

    linhasResultado.add("Identificação: " + identificacao);
    quebraLinha();
    linhasResultado.add("Nº da Replicação: " + replicacao);
    quebraLinha();
    cabecalho();
    quebraLinha();

    for (Usuario u : usuarios) {
      metricaTempoTotalEspera += u.getTotalTempoEspera();
      metricaTempoTotalServico += u.getTotalTempoServico();
      metricaTotalArquivosProcessados += u.getTotalArquivosEnviados();
      linhasResultado.add(u.toString());
      quebraLinha();
    }

    quebraLinha();

    // Monta metricas do servidor
    addMetricasServidor();

    quebraLinha();
    quebraLinha();

    // Limpa metricas do servidor
    metricaUtilizacao.clear();
    setTempoMedioFila(0);
    setTempoTotalServico(0);
    setTotalArquivosProcessados(0);
    setTempoTotalEspera(0);
  }

  public void quebraLinha() {
    linhasResultado.add("%n");
  }

  public void cabecalho() {
    String s = "|" + formatarPosicoes("Usuario") + "|" + formatarPosicoes("Qtd Arquivos Enviados") + "|"
        + formatarPosicoes("Tempo Total Serviço") + "|" + formatarPosicoes("Tamanho Somado Arquivos") + "|"
        + formatarPosicoes("Tempo Total Espera") + "|";
    linhasResultado.add(s);
  }

  public void addMetricasServidor() {
    linhasResultado.add("Metricas do servidor:");
    quebraLinha();
    String cabecalho = "|" + formatarPosicoes("Arquivos processados") + "|"
        + formatarPosicoes("Utilização de servidores") + "|" + formatarPosicoes("Tempo esperando") + "|"
        + formatarPosicoes("Tempo processando") + "|" + formatarPosicoes("Tempo médio fila") + "|";
    linhasResultado.add(cabecalho);
    double mediaUtilizacao = 0;
    for (Double utilizacao : metricaUtilizacao) {
      mediaUtilizacao += utilizacao;
    }
    mediaUtilizacao = mediaUtilizacao / metricaUtilizacao.size();
    String porcentagemUtilizacao = String.format("%.2f", mediaUtilizacao);
    metricaTempoMedioDaFila = metricaTempoTotalEspera / metricaTotalArquivosProcessados;
    String formatarTempoMedio = String.format("%.2f / arquivo", metricaTempoMedioDaFila);
    String metricas = "|" + formatarPosicoes(Integer.toString(metricaTotalArquivosProcessados)) + "|"
        + formatarPosicoes(porcentagemUtilizacao) + "|" + formatarPosicoes(Integer.toString(metricaTempoTotalEspera))
        + "|" + formatarPosicoes(Integer.toString(metricaTempoTotalServico)) + "|"
        + formatarPosicoes(formatarTempoMedio) + "|";
    quebraLinha();
    linhasResultado.add(metricas);
  }

  public void exportar() {

    try {

      FileWriter arq = new FileWriter(caminho);
      PrintWriter gravarArq = new PrintWriter(arq);

      gravarArq.printf("Resultados%n");
      quebraLinha();

      // Adiciona data/hora de agora = identificar de quando é o arquivo
      DateFormat formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      Date data = new Date();
      gravarArq.printf("Data/hora desta simulacao: " + formataData.format(data) + "%n%n");

      for (String linha : linhasResultado) {
        gravarArq.printf(linha);
      }

      arq.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String formatarPosicoes(String s) {
    return String.format("%1$-25s", s);

  }

}
