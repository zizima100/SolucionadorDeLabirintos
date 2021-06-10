package ServidorDeLabirintos.Compartilhado;

import BancoDeDados.dbos.*;

// Classe que deve estar presente tanto no cliente quanto no servidor.

/**
 * Classe responsável por armazenar o comunicado de pedido de labirinto.
 * 
 * <p>
 * Cliente pede um labirinto a partir do ID do labirinto listado.
 * </p>
 * 
 * Servidor responde mandando objeto da classe Resultado para o cliente
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class PedidoDeLabirinto extends Comunicado {
    // Provavelmente precisamos apenas de um atributo chamado ID que irá armazenar o
    // ID do labirinto solicitado.

    int idLabirinto;
    Labirinto labirinto = null;

    /**
     * Construct do pedido do cliente para o servidor.
     * 
     * @param id o id do labirinto que deseja receber do servidor.
     */
    public PedidoDeLabirinto(int id) {
        this.idLabirinto = id;
    }

    /**
     * Construct do pedido do servidor para o cliente.
     * 
     * @param labirinto o labirinto que o cliente pediu.
     */
    public PedidoDeLabirinto(Labirinto labirinto) {
        this.labirinto = labirinto;
    }

    public void setLabirinto(Labirinto labirinto) {
        this.labirinto = labirinto;
    }

    /**
     * @return labirinto pedido pelo cliente e enviado pelo servidor.
     */
    public Labirinto getLabirinto() {
        return this.labirinto;
    }

    /**
     * @return ID do labirinto enviado pelo cliente.
     */
    public int getIdLabirinto() {
        return this.idLabirinto;
    }
}
