package ServidorDeLabirintos.Cliente;

import BancoDeDados.dbos.*;

// Classe que deve estar presente tanto no cliente quanto no servidor.

/**
 * Classe responsável por armazenar o comunicado de pedido de salvamento de um
 * novo labirinto.
 * 
 * <p>
 * Enviado o labirinto editado no cliente para o servidor armazenar.
 * </p>
 */
public class PedidoDeSalvamento extends Comunicado {
    Labirinto labirinto = null;

    public PedidoDeSalvamento(Labirinto labirinto) {
        this.labirinto = labirinto;
    }

    public Labirinto getlabirinto() {
        return this.labirinto;
    }
}
