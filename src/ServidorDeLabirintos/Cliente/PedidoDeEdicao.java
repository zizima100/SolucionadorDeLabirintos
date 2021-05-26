package ServidorDeLabirintos.Cliente;

import BancoDeDados.dbos.*;

// Classe que deve estar presente tanto no cliente quanto no servidor.

/**
 * Classe responsável por armazenar o comunicado de pedido de salvamento da
 * edicão do labirinto.
 * 
 * <p>
 * Enviado o labirinto editado no cliente para o servidor armazenar.
 * </p>
 */
public class PedidoDeEdicao extends Comunicado {
    Labirinto labEditado = null;

    public PedidoDeEdicao(Labirinto labEditado) {
        this.labEditado = labEditado;
    }

    public Labirinto getLabEditado() {
        return this.labEditado;
    }
}
