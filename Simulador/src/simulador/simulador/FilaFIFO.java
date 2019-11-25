package simulador;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FilaFIFO extends Fila {
  private LinkedList<Arquivo> arquivos;

  FilaFIFO() {
    arquivos = new LinkedList<Arquivo>();
  }

  public synchronized Arquivo consome() throws NoSuchElementException {
    return arquivos.removeFirst();
  }

  public synchronized void adiciona(Arquivo arquivo) {
    arquivo.setTempoChegada(Simulador.relogio.getTempoAtual());
    arquivos.addLast(arquivo);
  }

  public synchronized boolean estaVazia() {
    return arquivos.isEmpty();
  }
}
