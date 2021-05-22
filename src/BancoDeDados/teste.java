package BancoDeDados;

import java.sql.*;
import BancoDeDados.daos.*;
import BancoDeDados.core.*;
import BancoDeDados.dbos.*;

public class teste {
    public static void main(String[] args) {
        try {
            String sql;

            sql = "SELECT * " + "FROM Labirintos";

            Connection.COMANDO.prepareStatement(sql);

            MeuResultSet resultado = (MeuResultSet) Connection.COMANDO.executeQuery();

            System.out.println(resultado.first());
        } catch (SQLException erro) {
            erro.printStackTrace();
        }
    }
}
