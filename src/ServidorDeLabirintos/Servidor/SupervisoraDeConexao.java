package ServidorDeLabirintos.Servidor;

import java.io.*;
import java.net.*;
import java.util.*;
import BancoDeDados.daos.*;
import BancoDeDados.dbos.Labirinto;
import ServidorDeLabirintos.Compartilhado.*;

/**
 * Thread responsável por aguardar pedidos dos clientes e tomar ações a depender do pedido recebido
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class SupervisoraDeConexao extends Thread {
    private Parceiro usuario;
    private Socket conexao;
    private ArrayList<Parceiro> usuarios;

    /**
     * Cria conexão entre cliente e servidor
     * @param conexao é o socket criado na aceitadoraDeConexao 
     * @param usuarios do servidor contidos em um ArrayList de parceiros
     * @throws Exception 
     */
    public SupervisoraDeConexao(Socket conexao, ArrayList<Parceiro> usuarios) throws Exception {
        if (conexao == null) {
            throw new Exception("Conexao ausente");
        }
        if (usuarios == null) {
            throw new Exception("Usuarios ausentes");
        }

        this.conexao = conexao;
        this.usuarios = usuarios;
    }

    /**
     * Aguarda pedidos dos clientes e toma ações a depender do pedido recebido 
     */
    public void run() {
        ObjectOutputStream transmissor;
        try {
            transmissor = new ObjectOutputStream(this.conexao.getOutputStream());
        } catch (Exception erro) {
            return;
        }

        ObjectInputStream receptor = null;
        try {
            receptor = new ObjectInputStream(this.conexao.getInputStream());
        } catch (Exception err0) {
            try {
                transmissor.close();
            } catch (Exception falha) {
            } // so tentando fechar antes de acabar a thread

            return;
        }

        try {
            this.usuario = new Parceiro(this.conexao, receptor, transmissor);
        } catch (Exception erro) {
        } // sei que passei os parametros corretos

        try {
            synchronized (this.usuarios) {
                this.usuarios.add(this.usuario);
            }

            while (true) {
                Comunicado comunicado = this.usuario.envie();

                if (comunicado == null) {
                    return;
                }
                else if (comunicado instanceof PedidoDeSalvamento) {
                    PedidoDeSalvamento pedido = (PedidoDeSalvamento)comunicado;

                    Labirinto labParaSalvar = pedido.getlabirinto();

                    if (Labirintos.isInBanco(labParaSalvar.getId())) {
                        Labirintos.alterar(labParaSalvar);
                    } else {
                        Labirintos.incluir(labParaSalvar);
                    }
                } else if (comunicado instanceof PedidoDeLabirinto) {
                    PedidoDeLabirinto pedido = (PedidoDeLabirinto)comunicado;

                    pedido.setLabirinto(Labirintos.getLabirinto(pedido.getIdLabirinto()));

                    usuario.receba(pedido);
                } else if (comunicado instanceof PedidoDeLabirintos) {
                    PedidoDeLabirintos pedido = (PedidoDeLabirintos)comunicado;

                    pedido.setLabirintos(Labirintos.getLabirintos(pedido.getEmail()));

                    usuario.receba(pedido);
                } else if (comunicado instanceof PedidoParaSair) {
                    usuario.adeus();
                }
            }
        } catch (Exception erro) {
            try {
                transmissor.close();
                receptor.close();
            } catch (Exception falha) {
            } // so tentando fechar antes de acabar a thread

            return;
        }
    }
}
