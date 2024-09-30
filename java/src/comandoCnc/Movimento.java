package comandoCnc;

public class Movimento {
    private float move;
   
    public Movimento() {
        this.move = 0;
    }

    // Método para mover para a esquerda ou direita no eixo 
    public float mover(int quantidade, float posicao) {
    	this.move = posicao;
    	this.move += quantidade;
        return move;
    }
    
    public String moverXp() {
    	return "G91 G00 X1 \r\n";
        		
    }
    public String moverXn() {
    	return "G91 G00 X-1 \r\n";
        		
    }

	public String moverYp(float y) {
		return "G91 G00 Y1 \r\n";
    }
	
	public String moverYn(float y) {
		return "G91 G00 Y-1 \r\n";
    }
	
	public String moverZp(float z) {
		return "G91 G00 Z1 \r\n";

    }
	
	public String moverZn(float z) {
		return "G91 G00 Z-1 \r\n";

    }
}
