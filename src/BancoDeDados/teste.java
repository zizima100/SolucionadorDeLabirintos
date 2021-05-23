package BancoDeDados;

import java.sql.*;
import BancoDeDados.daos.*;
import BancoDeDados.core.*;
import BancoDeDados.dbos.*;

public class teste {
    public static void main(String[] args) {

        try {
            Labirinto teste = Labirintos.getLabirinto(3);
            teste.setConteudo("###teste-alterado###");
            Labirintos.alterar(teste);
        } catch (Exception erro) {
            System.err.println(erro.getMessage());
        }
    }
}
