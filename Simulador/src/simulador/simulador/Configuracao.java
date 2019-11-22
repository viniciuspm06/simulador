package simulador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.SAXException;

public final class Configuracao {

  private ArrayList<Parametros> listaParametros;

  Configuracao(String caminhoArquivo) {
    try {
      File configXML = new File(caminhoArquivo);
      carregaConfig(configXML);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void carregaConfig(File config) throws ParserConfigurationException, SAXException, IOException {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setValidating(true);
    SAXParser saxParser = factory.newSAXParser();
    ConfiguracaoHandler handler = new ConfiguracaoHandler();
    saxParser.parse(config, handler);
    listaParametros = handler.getListaParametros();
  }

  public int getNumeroRodadas() {
    return listaParametros.size();
  }

  public int getSemente(int index) {
    return listaParametros.get(index).getSemente();
  }

  public int getNumeroReplicacoes(int index) {
    return listaParametros.get(index).getReplicacoes();
  }

  public int getTipoFila(int index) {
    return listaParametros.get(index).getTipoFila();
  }

  public int getVelocidade(int index) {
    return listaParametros.get(index).getVelocidade();
  }

  public int getQtdServidores(int index) {
    return listaParametros.get(index).getQtdServidores();
  }

  public int getQtdUsuarios(int index) {
    return listaParametros.get(index).getQtdUsuarios();
  }

}
