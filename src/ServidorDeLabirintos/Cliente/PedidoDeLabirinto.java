package ServidorDeLabirintos.Cliente;

// Classe que deve estar presente tanto no cliente quanto no servidor.

/**
 * Classe responsável por armazenar o comunicado de pedido de labirinto.
 * 
 * <p>
 * Enviado o labirinto guardado do servidor para o cliente.
 * </p>
 * 
 * Servidor responde mandando objeto da classe Resultado para o cliente
 */
public class PedidoDeLabirinto extends Comunicado { 
    // Provavelmente precisamos apenas de um atributo chamado ID que irá armazenar o ID do labirinto solicitado.

    int idLabirinto;

    public PedidoDeLabirinto(int id) {
        this.idLabirinto = id;
    }

    /**
     * @return Retorna o valor do ID do labirinto.
     */
    public int getIdLabirinto() {
        return this.idLabirinto;
    }
}
