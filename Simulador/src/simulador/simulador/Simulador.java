package simulador;

import java.util.ArrayList;

public class Simulador {

  public static GeradorAleatorio ga;
  public static Fila fila;
  public static ArrayList<Servidor> servidores;
  public static boolean arquivosEnviados;
  public static Configuracao config;
  public static volatile int multiplicador;

  public static void main(String args[]) {

    config = new Configuracao("./config.xml");

    for (int i = 0, rodadas = config.getNumeroRodadas(); i < rodadas; i++) {
      executaRodada(i);
      // TODO: Gera relatório da rodada
    }

    // TODO: Gerar relatório final

  }

  public static void executaRodada(int rodada) {
    imprime("Comecando rodada: " + rodada);
    long tempoInicial = System.currentTimeMillis();
    // Calcula multiplicador com base na velocidade fornecida
    int velocidade = config.getVelocidade(rodada);
    multiplicador = (int) (1 / (0.01 * velocidade));

    // Determina fila a usar
    int tipoFila = config.getTipoFila(rodada);

    if (tipoFila == Fila.PRIORIDADE) {
      fila = new FilaPrioridade();
    } else {
      fila = new FilaFIFO();
    }

    try {
      int qtdReplicacoes = config.getNumeroReplicacoes(rodada);
      int qtdUsuarios = config.getQtdUsuarios(rodada);
      int qtdServidores = config.getQtdServidores(rodada);

      // quantidade de replicações que serão feitas na rodada
      for (int i = 0; i < qtdReplicacoes; i++) {
        int semente = config.getSemente(rodada);
        if (semente == 0) {
          ga = new GeradorAleatorio();
        } else {
          ga = new GeradorAleatorio(semente);
        }
        imprime("Começando replicacao " + i);
        arquivosEnviados = false;
        ArrayList<Usuario> usuarios = criaUsuarios(qtdUsuarios);
        enfileraArquivos(usuarios);
        iniciaServidores(qtdServidores);
        // Verificacao se o simulador terminou de rodar a replicação
        while (!fila.estaVazia() || !arquivosEnviados) {
          Thread.sleep(10);
        }
        // Fim da replicacao
        paraServidores();
        // TODO: Gera relatorio parcial da replicacao
      }
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    long tempoFinal = System.currentTimeMillis();
    imprime("Tempo da rodada: " + (tempoFinal - tempoInicial) + " milissegundos");
  }

  public static ArrayList<Usuario> criaUsuarios(int qtdUsuarios) {
    // Chumbando para testes, max 8 usuarios
    String nomes[] = { "A", "B", "C", "D", "E", "F", "G", "H" };
    ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    for (int i = 0; i < qtdUsuarios; i++) {
      Usuario usuario = new Usuario(nomes[i], ga);
      usuarios.add(usuario);
    }
    return usuarios;
  }

  public static void iniciaServidores(int qtdServidores) {
    servidores = new ArrayList<Servidor>();
    for (int i = 0; i < qtdServidores; i++) {
      Servidor servidor = new Servidor(i, fila);
      servidor.iniciar();
      servidores.add(servidor);
    }
  }

  public static void paraServidores() {
    for (Servidor servidor : servidores) {
      servidor.terminar();
    }
    servidores.clear();
  }

  public static void enfileraArquivos(ArrayList<Usuario> usuarios) {
    new Thread() {
      @Override
      public void run() {
        for (Usuario usuario : usuarios) {
          ArrayList<Arquivo> arquivos = usuario.enviaArquivos();
          for (Arquivo arquivo : arquivos) {
            fila.adiciona(arquivo);
          }
          int tempo = ga.getAleatorio(10) * multiplicador;
          imprime(usuario.toString());
          imprime("Esperando proximo usuario...");
          // Espera tempo random (até 10 unidades de tempo) ate o proximo pacote de outro
          // user
          try {
            Thread.sleep(tempo);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        arquivosEnviados = true;
      }
    }.start();
  }

  public synchronized static void imprime(String s) {
    System.out.println(s);
  }

  public static void teste(ArrayList<Arquivo> arquivos) {
    int i = 0;
    for (Arquivo a : arquivos) {
      i++;
      System.out.println("Arquivo: " + i + " -- Numero de Linhas:" + a.getNumLinhas());
    }
  }

}
