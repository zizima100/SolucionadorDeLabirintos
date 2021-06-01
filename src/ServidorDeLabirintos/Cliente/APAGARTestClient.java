package ServidorDeLabirintos.Cliente;

import java.net.*;
import java.util.*;
import BancoDeDados.dbos.*;
import ServidorDeLabirintos.Compartilhado.*;
import java.io.*;

public class APAGARTestClient {
    public static void main(String[] args) {
        Socket conexao = null;
        try {
            String host = Cliente.HOST_PADRAO;
            int porta = Cliente.PORTA_PADRAO;

            if (args.length > 0)
                host = args[0];

            if (args.length == 2)
                porta = Integer.parseInt(args[1]);

            conexao = new Socket(host, porta);
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectOutputStream transmissor = null;
        try {
            transmissor = new ObjectOutputStream(conexao.getOutputStream());
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            try {
                conexao.close();
            } catch (IOException e) {
            }
            return;
        }

        ObjectInputStream receptor = null;
        try {
            receptor = new ObjectInputStream(conexao.getInputStream());
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            try {
                conexao.close();
            } catch (IOException e) {
            }
            return;
        }

        Parceiro servidor = null;
        try {
            servidor = new Parceiro(conexao, receptor, transmissor);
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            return;
        }

        try {
            servidor.receba(new PedidoDeLabirintos("j@j.com"));
            Comunicado comunicado = null;
            do {
                comunicado = (Comunicado) servidor.espie();
            } while (!(comunicado instanceof PedidoDeLabirintos));
            PedidoDeLabirintos pedido = (PedidoDeLabirintos) servidor.envie();

            Vector<Labirinto> vLabirintos = pedido.getLabirintos();
            for (int i = 0; i < vLabirintos.size(); i++) {
                System.out.println(vLabirintos.get(i));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
