package simulador;

import java.util.NoSuchElementException;

public class Servidor {

  private Fila fila;

  private int id;
  private volatile boolean processando;
  private volatile boolean parado;

  Servidor(int id, Fila fila) {
    setId(id);
    setFila(fila);
    processando = false;
    parado = true;
  }

  public void iniciar() {
    new Thread() {
      @Override
      public void run() {
        if (!processando) {
          processando = true;
          parado = false;
          while (processando) {
            if (fila.estaVazia()) {
              // Delay do servidor para fila vazia = 10ms
              try {
                Thread.sleep(10);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              continue;
            }
            try {
              Arquivo arquivo = fila.consome();
              Usuario dono = arquivo.getDono();
              // int tempo = arquivo.getTempoServico() * 1000;
              int tempo = arquivo.getTempoServico() * Simulador.multiplicador;
              // Simula o processamento
              try {
                Thread.sleep(tempo);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              // Avisa o dono
              dono.informaProcessamento(arquivo.getNumLinhas(), tempo);
            } catch (NoSuchElementException e) {
            }
          }
          parado = true;
          return;
        }
      }
    }.start();
  }

  public void terminar() {
    processando = false;
    while (!parado) {
    }
    return;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Fila getFila() {
    return this.fila;
  }

  public void setFila(Fila fila) {
    this.fila = fila;
  }

  public boolean estaProcessando() {
    return this.processando;
  }

}
