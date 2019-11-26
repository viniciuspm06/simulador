package simulador;

public class Relogio implements Runnable {
  private volatile boolean ligado;
  private volatile int tempoAtual;
  private Thread t;

  public Relogio() {

  }

  public synchronized int getTempoAtual() {
    return tempoAtual;
  }

  public synchronized void iniciar() {

    ligado = true;
    zerar();
    t = new Thread(this);
    t.start();
  }

  public synchronized void desligar() {
    ligado = false;
  }

  public synchronized void zerar() {
    tempoAtual = 0;
  }

  @Override
  public void run() {
    try {
      while (ligado) {
        tempoAtual++;
        Thread.sleep(1 * Simulador.multiplicador);
        // System.out.println("Tempo Atual: " + tempoAtual);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
