package ServidorDeLabirintos.Cliente;

import ServidorDeLabirintos.Compartilhado.*;

/**
 * Classe do cliente responsável por tratar o que acontecerá caso o cliente
 * receba um comunicado de desligamento.
 * 
 * O cliente será encerrado e aparecerá uma mensagem esclarecendo o que
 * aconteceu.
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class TratadoraDeComunicadoDeDesligamento extends Thread {
    private Parceiro servidor;

    public TratadoraDeComunicadoDeDesligamento(Parceiro servidor) throws Exception {
        if (servidor == null)
            throw new Exception("Porta invalida");

        this.servidor = servidor;
    }

    /**
     * Thread que espera e espia um comunicado de desligamento, para desligar o servidor
     */
    public void run() {
        for (;;) {
            try {
                if (this.servidor.espie() instanceof ComunicadoDeDesligamento) {
                    System.out.println("\nO servidor vai ser desligado agora;");
                    System.err.println("volte mais tarde!\n");
                    System.exit(0);
                }
            } catch (Exception erro) {
            }
        }
    }
}
