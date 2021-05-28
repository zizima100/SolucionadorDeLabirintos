package ServidorDeLabirintos.Servidor;

import java.io.*;
import java.net.*;
import java.util.*;
import BancoDeDados.daos.*;
import BancoDeDados.dbos.*;
import BancoDeDados.BDSQLServer;
import BancoDeDados.core.MeuResultSet;

public class SupervisoraDeConexao extends Thread {
    private double valor = 0;
    private Parceiro usuario;
    private Socket conexao;
    private ArrayList<Parceiro> usuarios;

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

                if (comunicado == null)
                    return;
                else if (comunicado instanceof PedidoDeSalvamento) {
                    // pegar do comunicado o vetor cheio de desenhos
                    // e mais o nome do desenho e mais identificacao
                    // cliente, e mandar pro BD usando o DAO e o DBO
                    // que voce vai fazer
                    // -----
                    // desconectar o usuario
                } else if (comunicado instanceof PedidoDeEdicao) {
                    // pegar do comunicado o nome do desenho e a identificacao
                    // cliente, usar o DAO e DBO para recuperar do BD os dados,
                    // preencher um objeto do tipo Desenho e vai enviar pro
                    // cliente fazendo usuario.receba(desenho)
                    // -----
                    // desconecta o usuario
                } else if (comunicado instanceof PedidoDeLabirinto) {
                    PedidoDeLabirinto pedido = (PedidoDeLabirinto)comunicado;

                    pedido.setLabirinto(Labirintos.getLabirinto(pedido.getIdLabirinto()));

                    usuario.receba(pedido);
                } else if (comunicado instanceof PedidoDeLabirintos) {
                    PedidoDeLabirintos pedido = (PedidoDeLabirintos)comunicado;

                    pedido.setLabirintos(Labirintos.getLabirintos(pedido.getEmail()));

                    usuario.receba(pedido);
                } else if (comunicado instanceof PedidoParaSair) {
                    // pegar do comunicado o nome do desenho e a identificacao
                    // cliente, usar o DAO e DBO para recuperar do BD os dados,
                    // preencher um objeto do tipo Desenho e vai enviar pro
                    // cliente fazendo usuario.receba(desenho)
                    // -----
                    // desconecta o usuario
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
