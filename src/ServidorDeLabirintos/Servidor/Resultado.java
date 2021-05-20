package ServidorDeLabirintos.Servidor;

/**
 * Servidor envia um objeto de resposta para o cliente (Valor que tem guardado no servidor para aquele cliente)
 */
public class Resultado extends Comunicado {
    private String labirinto; 

    public Resultado (String labirinto) 
    {
        this.labirinto = labirinto; // Vamos guardar o valor recebido no atributo
    }

    public String getLabirinto () 
    {
        return this.labirinto;
    }

    public String toString () 
    {
        return ("" + this.labirinto);
    } 
}