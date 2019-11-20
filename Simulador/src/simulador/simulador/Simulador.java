package simulador;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Simulador {

  public static GeradorAleatorio ga;
  public static Fila fila;
  public static ArrayList<Servidor> servidores;
  public static boolean arquivosEnviados;

  public static void main(String args[]) {

    ga = new GeradorAleatorio(System.currentTimeMillis());
    fila = new FilaFIFO();

    try {
      int qtdReplicacoes = 1;
      int qtdUsuarios = 6;
      int qtdServidores = 2;

      // GeradorAleatorio ga = new GeradorAleatorio(System.currentTimeMillis());

      // quantidade de replicações que serão feitas nos testes
      for (int i = 0; i < qtdReplicacoes; i++) {
        imprime("Começando replicacao " + i);
        arquivosEnviados = false;
        // gerar arquivos com tamanhos aletórios
        // int qtdArquivos = ga.getQtdArquivoAleatorio();
        // teste com geração de arquivos aleatorioamente
        // ArrayList<Arquivo> arquivos =
        // Arquivo.gerarArquivosUpload(ga.getQtdArquivoAleatorio(), ga);
        // teste(arquivos);

        ArrayList<Usuario> usuarios = criaUsuarios(qtdUsuarios);
        enfileraArquivos(usuarios);
        iniciaServidores(qtdServidores);
        while (!fila.estaVazia() || !arquivosEnviados) {
          Thread.sleep(10);
        }
        // Fim da replicacao
        paraServidores();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

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
          int tempo = ga.getAleatorio(10) * 1000;
          imprime(usuario.toString());
          imprime("Esperando " + tempo / 1000 + " segundos...");
          // Espera tempo random (até 10 segundos) ate o proximo pacote de outro user
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
