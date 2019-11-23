package simulador;

import java.util.ArrayList;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ConfiguracaoHandler extends DefaultHandler {

  private Stack<String> pilhaElementos = new Stack<String>();
  private Stack<Parametros> pilhaParametros = new Stack<Parametros>();
  private ArrayList<Parametros> listaParametros = new ArrayList<Parametros>();
  private int semente = 0;

  private String elementoAtual() {
    return pilhaElementos.peek();
  }

  @Override
  public void startDocument() throws SAXException {
  }

  @Override
  public void endDocument() throws SAXException {
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    pilhaElementos.push(qName);
    // Nova config
    if ("config".equals(qName)) {
      Parametros parametros = new Parametros();
      this.pilhaParametros.push(parametros);
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    pilhaElementos.pop();
    // Parametros construidos, popula lista
    if ("config".equals(qName)) {
      Parametros parametros = this.pilhaParametros.pop();
      parametros.setSemente(semente);
      listaParametros.add(parametros);
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    String valor = new String(ch, start, length).trim();
    if (elementoAtual().equals("semente")) {
      semente = Integer.parseInt(valor);
    }
    if (valor.length() == 0 || pilhaParametros.empty()) {
      return;
    }
    // Lida com os valores baseado no tipo
    Parametros parametros = pilhaParametros.peek();
    String atual = elementoAtual();
    switch (atual) {
    case "replicacoes":
      parametros.setReplicacoes(Integer.parseInt(valor));
      break;
    case "velocidade":
      int valorInt = Integer.parseInt(valor);
      if (valorInt <= 0) {
        throw new Error("Velocidade precisa estar entre 1 e 100");
      }

      if (valorInt > 100) {
        throw new Error("Velocidade acima de 100");
      }
      parametros.setVelocidade(valorInt);
      break;
    case "fila":
      int tipoFila = Fila.FIFO;
      if (valor.toUpperCase().equals("PRIORIDADE")) {
        tipoFila = Fila.PRIORIDADE;
      }
      parametros.setTipoFila(tipoFila);
      break;
    case "qtdServidores":
      parametros.setQtdServidores(Integer.parseInt(valor));
      break;
    case "qtdUsuarios":
      parametros.setQtdUsuarios(Integer.parseInt(valor));
      break;
    case "identificacao":
        parametros.setIdentificacao(valor);
        break;
    default:
      return;
    }
  }

  public ArrayList<Parametros> getListaParametros() {
    return listaParametros;
  }

}
