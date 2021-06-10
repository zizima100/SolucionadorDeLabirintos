package ServidorDeLabirintos.Compartilhado;

/**
 * Esta classe representa um comunicado de que o servidor será desligado e o
 * cliente, recebendo este comunicado, deverá alertar o usuário e encerrar em
 * seguida.
 * 
 * <p>
 * Classe que deve estar presente tanto no cliente quanto no servidor.
 * </p>
 * 
 * Essa classe deve ser oca.
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class ComunicadoDeDesligamento extends Comunicado {
    // Mensagem para todos clientes, notificando que o servidor está encerrando
    // (MENSAGEM DO SERVIDOR).
    // (No servidor) -> Desativar -> é criado e instaciado objeto dessa classe ->
    // mandado para todos clientes conectados
}
