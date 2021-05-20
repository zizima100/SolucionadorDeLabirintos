package ServidorDeLabirintos.Cliente;

/**
 * Recebe a operação do cliente e envia o objeto (labirinto) do servidor para o cliente. 
 */
public class PedidoParaServidor {
    /**
     * No caso do servidor de continhas, é mandado a operacao e o valor, mas no nosso programa, vamos 
     * pedir opcoes do usuario: 
     *  - Criar novo labirinto do zero -> Vai para a interface, ele é criado e depois deve ser salvo pelo novo botao "Salvar no Server"
     *  - Abrir determinado labirinto, a partir do indice
     *  - Alterar determinado labirinto, a partir do indice
     *  
     * Mandar o valor, que é o labirinto em forma de String.
     */

    private char operacao; 
    private String labirinto; 

    public PedidoParaServidor (char Operacao, String labirinto) {
        this.operacao = operacao; 
        this.labirinto = labirinto; // melhor mandar o clone do labirinto já que é objeto  
    }

    public char getOperacao() {
        return this.operacao;
    }    

    public String getLabirinto() {
        return this.labirinto;
    }

    public String toString ()
    {
        return ("" + this.operacao + this.getLabirinto()); // pode retornar apenas o labirinto
    }
}
