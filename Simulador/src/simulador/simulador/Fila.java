package simulador;

import java.util.NoSuchElementException;

public abstract class Fila {

  public abstract Arquivo consome() throws NoSuchElementException;

  public abstract void adiciona(Arquivo arquivo);

  public abstract boolean estaVazia();

}
