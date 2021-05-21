package ServidorDeLabirintos.Servidor;

// Classe que deve estar presente tanto no cliente quanto no servidor.

/**
 * Enviado o labirinto guardado do servidor para o cliente.
 * Servidor responde mandando objeto da classe Resultado para o cliente
 */
public class PedidoDeLabirinto extends Comunicado { 
    // Provavelmente precisamos apenas de um atributo chamado ID que irá armazenar o ID do labirinto solicitado.

    int idLabirinto;

    public PedidoDeLabirinto(int id) {
        this.idLabirinto = id;
    }

    public int getIdLabirinto() {
        return this.idLabirinto;
    }
}
