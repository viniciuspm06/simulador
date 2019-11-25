package simulador;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Relatorio {
  private int metricaTempoTotalServico;
  private ArrayList<String> linhasResultado;
  private String caminho;

  public Relatorio(String caminho) {
    this.caminho = caminho;
    linhasResultado = new ArrayList<String>();
  }

  public void setTempoTotalServico(int tempoTotalServico) {
    this.metricaTempoTotalServico = tempoTotalServico;
  }

  public void addResultado(String identificacao, int replicacao, ArrayList<Usuario> usuarios) {

    linhasResultado.add("Identificação: " + identificacao);
    quebraLinha();
    linhasResultado.add("Nº da Replicação: " + replicacao);
    quebraLinha();
    cabecalho();
    quebraLinha();

    for (Usuario u : usuarios) {
      linhasResultado.add(u.toString());
      quebraLinha();
    }

    quebraLinha();
    quebraLinha();
  }

  public void quebraLinha() {
    linhasResultado.add("%n");
  }

  public void cabecalho() {
    String s = "|" + formatarPosicoes("Usuario") + "|" + formatarPosicoes("Qtd Arquivos Enviado") + "|"
        + formatarPosicoes("Tempo Total Serviço") + "|" + formatarPosicoes("Tamanho Somado Arquivos") + "|"
        + formatarPosicoes("Tempo Total Espera") + "|";
    linhasResultado.add(s);
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
