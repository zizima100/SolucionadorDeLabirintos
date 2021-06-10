package ServidorDeLabirintos.Servidor;

import java.net.*;
import java.util.*;

import ServidorDeLabirintos.Compartilhado.*;

/**
 * Cria e valida o Socket entre cliente e servidor
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021 
 */
public class AceitadoraDeConexao extends Thread {
    private ServerSocket pedido;
    private ArrayList<Parceiro> usuarios;

    /**
     * Cria o serverSocket a partir da porta fornecida
     * @param porta do servidor 
     * @param usuarios c 
     * @throws Exception 
     */
    public AceitadoraDeConexao(String porta, ArrayList<Parceiro> usuarios) throws Exception {
        if (porta == null) {
            throw new Exception("Porta ausente");
        }
        
        try {
            this.pedido = new ServerSocket(Integer.parseInt(porta)); 
        } catch (Exception erro) {
            throw new Exception("Porta invalida");
        }

        if (usuarios == null)
            throw new Exception("Usuarios ausentes");

        this.usuarios = usuarios;
    }

    /**
     * Aguarda novas conexões com clientes para criar novas supervisorasDeConexao a partir do serverSocket criado 
     */
    public void run() {
        while (true) {
            Socket conexao = null;
            try {
                conexao = this.pedido.accept();
            } catch (Exception erro) {
                continue;
            }

            SupervisoraDeConexao supervisoraDeConexao = null;
            try {
                supervisoraDeConexao = new SupervisoraDeConexao(conexao, usuarios);
            } catch (Exception erro) {
            } // sei que passei parametros corretos para o construtor
            supervisoraDeConexao.start();
        }
    }
}
