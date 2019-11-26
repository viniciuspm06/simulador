package simulador;

public class Servidor {

  private Fila fila;

  private int id;
  private int tempoTotal;
  private int tempoOcioso;
  private volatile boolean processando;
  private volatile boolean parado;

  Servidor(int id, Fila fila) {
    setId(id);
    setFila(fila);
    tempoTotal = 0;
    tempoOcioso = 0;
    processando = false;
    parado = true;
  }

  public void iniciar() {
    new Thread() {
      @Override
      public void run() {

        try {
          if (!processando) {
            processando = true;
            parado = false;

            while (processando) {
              tempoTotal++;
              if (fila.estaVazia()) {
                // Delay do servidor para fila vazia = 10ms
                tempoOcioso++;
                Thread.sleep(10);
                continue;
              }

              Arquivo arquivo = fila.consome();
              // iniciar o processamento do arquivo
              arquivo.setTempoEspera(Simulador.relogio.getTempoAtual());
              Simulador.imprime(arquivo.toString());

              Usuario dono = arquivo.getDono();
              // int tempo = arquivo.getTempoServico() * 1000;
              int tempo = arquivo.getTempoServico() * Simulador.multiplicador;
              // Simula o processamento
              Thread.sleep(tempo);
              // Avisa o dono
              dono.informaProcessamento(arquivo);
            }
            // Informa utilizacao antes de parar
            Simulador.relatorio.atualizaUtilizacao(id, 100 - ((tempoOcioso / tempoTotal) * 100));
            parado = true;
            return;
          }
        } catch (Exception e) {
          e.printStackTrace();
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
