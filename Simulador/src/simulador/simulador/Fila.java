package simulador;

import java.util.NoSuchElementException;

public abstract class Fila {

  public static int FIFO = 0;

  public static int PRIORIDADE = 1;

  public abstract Arquivo consome() throws NoSuchElementException;

  public abstract void adiciona(Arquivo arquivo);

  public abstract boolean estaVazia();

}
