package simulador;

public class Relogio implements Runnable{
	private boolean ligado;
	private int tempoAtual;
	private Thread t;
	
	public Relogio() {
		
	}
	
	public void iniciar() {
		
		ligado = true;
		zerar();
		t = new Thread();
		t.start();
	}
	
	public void desligar() {
		ligado = false;
	}
	
	public void zerar() {
		tempoAtual = 0;
	}

	@Override
	public void run() {
		try{
			while(ligado) {
				tempoAtual++;
				Thread.sleep(1 + Simulador.multiplicador);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
}
