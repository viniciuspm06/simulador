package simulador;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class FilaPrioridade extends Fila {
  private PriorityQueue<Arquivo> fila;

  private Comparator<Arquivo> comparadorArquivo = new Comparator<Arquivo>() {
    @Override
    public int compare(Arquivo a1, Arquivo a2) {
      return (int) (a1.getNumLinhas() - a2.getNumLinhas());
    }
  };

  FilaPrioridade() {
    fila = new PriorityQueue<Arquivo>(comparadorArquivo);
  }

  public synchronized Arquivo consome() throws NoSuchElementException {
    Arquivo arquivo = fila.poll();
    if (arquivo == null) {
      throw new NoSuchElementException();
    }
    return arquivo;
  }

  public synchronized void adiciona(Arquivo arquivo) {
    fila.add(arquivo);
  }

  public synchronized boolean estaVazia() {
    return fila.isEmpty();
  }
}
