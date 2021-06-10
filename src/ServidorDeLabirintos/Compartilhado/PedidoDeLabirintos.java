package ServidorDeLabirintos.Compartilhado;

import BancoDeDados.dbos.*;

import java.util.Vector;

// Classe que deve estar presente tanto no cliente quanto no servidor.

/**
 * Classe responsável por armazenar o comunicado de pedido de labirintos a
 * partir do e-mail do cliente.
 * 
 * <p>
 * Enviado o email do cliente para o servidor pesquisar no banco de dados.
 * </p>
 * 
 * Servidor responde mandando vetor de objetos labirintos para o cliente
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class PedidoDeLabirintos extends Comunicado {
    String email;
    Vector<Labirinto> labirintos = null;

    /**
     * Construct do pedido do cliente para o servidor.
     * 
     * @param emailCliente email que o cliente envia para o servidor.
     */
    public PedidoDeLabirintos(String emailCliente) {
        this.email = emailCliente;
    }

    /**
     * Construct do pedido do servidor para o cliente.
     * 
     * @param labirintos vetor de labirintos enviado para o cliente.
     */
    public PedidoDeLabirintos(Vector<Labirinto> labirintos) {
        this.labirintos = labirintos;
    }

    public void setLabirintos(Vector<Labirinto> vLabirintos) {
        this.labirintos = vLabirintos;
    }

    /**
     * @return retorna o vetor de labirinto do servidor para o cliente.
     */
    public Vector<Labirinto> getLabirintos() {
        return this.labirintos;
    }

    /**
     * retorna o email enviado do cliente para o servidor.
     * 
     * @return
     */
    public String getEmail() {
        return this.email;
    }
}
