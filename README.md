# Simulador - Análise de desempenho de sistemas

Este simulador tem fim acadêmico e foi construído para a disciplina de análise de desempenho de sistemas, na Universidade Feevale.

O objetivo do projeto é simular um sistema de processamento de arquivos, com N usuários podendo enviar pacotes de arquivos ao sistema. O sistema deve enfilerar estes arquivos e processá-los para gerar um relatório com dados de desempenho.

## Configuração

Configurar o simulador é muito simples, o arquivo `config.xml` guarda todas as configurações do simulador.

O elemento `<simulador>` é a raiz do XML.

O elemento `<semente>` guarda a semente para usar no gerador de números aleatórios da simulação. Se omitida ou seta em `0`, o simulador vai gerar sua própria semente, com valor diferente a cada replicação.

Cada elemento `<config></config>` contém configurações para uma rodada de simulação.

As configurações disponíveis são:

- **identificacao**: Uma descrição para identificar a rodada de simulação no relatório final;
- **replicacoes**: Número de replicações que o simulador fará na rodada;
- **fila**: Tipo de fila a usar nesta rodada, aceita `FIFO` ou `PRIORIDADE`;
- **velocidade**: Velocidade da simulação de 0 a 100, quanto maior, mais rápido;
- **qtdServidores**: Quantidade de servidores que processarão os arquivos nesta rodada;
- **qtdUsuarios**: Quantidade de usuários que enviarão arquivos nesta rodada;

Exemplo de config completa para 2 rodadas de simulação:

```xml
<simulador>
  <semente>1</semente>
  <config>
    <identificacao>Estado Atual - 1 Servidor/ Fila FIFO</identificacao>
    <replicacoes>1</replicacoes>
    <fila>FIFO</fila>
    <velocidade>100</velocidade>
    <qtdServidores>1</qtdServidores>
    <qtdUsuarios>4</qtdUsuarios>
  </config>
  <config>
    <identificacao>Implementação Prioridade</identificacao>
    <replicacoes>1</replicacoes>
    <fila>PRIORIDADE</fila>
    <velocidade>100</velocidade>
    <qtdServidores>1</qtdServidores>
    <qtdUsuarios>4</qtdUsuarios>
  </config>
</simulador>
```

## Rodando o simulador

Disponibilizamos uma versão pré-compilada do simulador em um JAR na raiz do projeto. Para rodar basta executar:

```bash
java -jar Simulador.jar
```

**Lembrete**: Altere o `config.xml` para os seus parâmetros, antes de rodar.

## Como compilar

Você pode usar alguma IDE como Eclipse, NetBeans ou similar.

### Processo manual

Crie o diretório `bin` na raiz do projeto.

Compile as classes:

```bash
javac -classpath Simulador/src/simulador -d ./bin Simulador/src/simulador/simulador/*.java
```

Para gerar o JAR, rode:

```bash
jar cfe Simulador.jar simulador.Simulador -C ./bin simulador
```

## Breve resumo das classes

- **Simulador**: Classe principal, que roda a simulação.
- **Arquivo**: Apenas segura os dados relevantes de cada arquivo, como número de linhas, tempos de processamento e de espera, etc.
- **Configuracao**: Classe que lê o XML e retorna objeto com a configuração atual do simulador.
- **ConfiguracaoHandler**: Classe que interpreta o XML para a classe `Configuracao`.
- **Fila**: Classe abstrata que contém os métodos a serem implementados para filas.
- **FifaFIFO**: Implementação de `Fila` First-in-First-out (FIFO).
- **FilaPrioridade**: Implementação de `Fila` com prioridade, arquivos menores têm preferência.
- **GeradorAleatorio**: Classe que cuida da geração de valores aleatórios para o simulador.
- **Histograma**: Classe helper para debugar o gerador aleatório.
- **Parametros**: Classe que segura os parametros de cada rodada.
- **Relatorio**: Classe que gera o relatório final com base nas estatísticas coletadas.
- **Relogio**: Classe que conta o tempo decorrido em cada etapa da simulação.
- **Servidor**: Contém a implementação do consumidor de arquivos, cada objeto corresponde a um servidor.
- **Usuario**: Representa o usuário do sistema simulado, que gera e envia arquivos.

## Entendendo o relatório gerado

Ao final da execução, o Simulador gerará um arquivo `resultados.txt`.

Este arquivo contém um relatório com os resultados da simulação.

A primeira parte indica a performance do ponto de vista de cada usuário.

Depois, temos métricas a respeito do servidor, sobre como ele está conseguindo lidar com a fila.

## Autores

- Ângelo Máximo
- Gerson Lampert
- Matheus Kautzmann
- Rodrigo Molter
- Vinícius Machado
