package BancoDeDados;

import java.sql.*;
import BancoDeDados.daos.*;
import BancoDeDados.core.*;
import BancoDeDados.dbos.*;
import java.util.*;

public class teste {
    public static void main(String[] args) {

        try {
            Vector<Labirinto> todosOsLabirintos = Labirintos.getTodosLabirintos();

            for(int i = 0; i < todosOsLabirintos.size(); i++) {
                System.out.println(todosOsLabirintos.get(i));
            }
        } catch (Exception erro) {
            System.err.println(erro.getMessage());
        }
    }
}
