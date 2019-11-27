package simulador;

import java.util.ArrayList;

public class Simulador {

  public static GeradorAleatorio ga;
  public static Fila fila;
  public static ArrayList<Servidor> servidores;
  public static volatile boolean arquivosEnviados;
  public static Configuracao config;
  public static volatile int multiplicador;
  public static Relogio relogio;
  public static Relatorio relatorio;

  public static void main(String args[]) {

    config = new Configuracao("./config.xml");
    relatorio = new Relatorio("./resultados.txt");
    relogio = new Relogio();
    relogio.iniciar();

    for (int i = 0, rodadas = config.getNumeroRodadas(); i < rodadas; i++) {
      imprime(config.getIdentificacao(i));
      executaRodada(i);
      relatorio.addEstatisticasRodada();
    }

    relogio.desligar();
    relatorio.exportar();

  }

  public static void executaRodada(int rodada) {
    imprime("ComeÃ§ando rodada: " + rodada);
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

      // quantidade de replicaÃ§Ãµes que serÃ£o feitas na rodada
      for (int i = 0; i < qtdReplicacoes; i++) {
        relogio.zerar();
        int semente = config.getSemente(rodada);
        if (semente == 0) {
          ga = new GeradorAleatorio();
        } else {
          ga = new GeradorAleatorio(semente);
        }
        imprime("ComeÃ§ando replicacao " + i);
        arquivosEnviados = false;
        ArrayList<Usuario> usuarios = criaUsuarios(qtdUsuarios);
        enfileraArquivos(usuarios);
        iniciaServidores(qtdServidores);
        // Verificacao se o simulador terminou de rodar a replicaÃ§Ã£o
        while (!fila.estaVazia() || !arquivosEnviados) {
          Thread.sleep(10);
        }

        // Fim da replicacao
        paraServidores();
        relatorio.addResultado(config.getIdentificacao(rodada), i + 1, usuarios);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    long tempoFinal = System.currentTimeMillis();
    imprime("Tempo da rodada: " + (tempoFinal - tempoInicial) + " milissegundos");
  }

  public static ArrayList<Usuario> criaUsuarios(int qtdUsuarios) {
    // Max 10 usuarios
    String nomes[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
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
      relatorio.adicionaServidor(0);
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
          usuario.setTempoEnvio(relogio.getTempoAtual());
          
          for (Arquivo arquivo : arquivos) {
            fila.adiciona(arquivo);
          }
          int tempo = ga.getAleatorio(10) * multiplicador;
          imprime(usuario.toString());
          imprime("Esperando proximo usuario...");
          // Espera tempo random (atÃ© 10 unidades de tempo) ate o proximo pacote de outro
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

}
