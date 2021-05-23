package BancoDeDados;

import java.sql.*;
import BancoDeDados.daos.*;
import BancoDeDados.core.*;
import BancoDeDados.dbos.*;

public class teste {
    public static void main(String[] args) {

        try {
            System.out.println(Labirintos.cadastrado(3));
        } catch (Exception erro) {
            System.err.println(erro.getMessage());
        }
    }
}
