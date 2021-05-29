package BancoDeDados;

import java.sql.*;
import BancoDeDados.daos.*;
import BancoDeDados.core.*;
import BancoDeDados.dbos.*;
import java.util.*;

public class teste {
    public static void main(String[] args) {

        try {
            Vector<Labirinto> vLabirintos = Labirintos.getLabirintos("j@j.com");

            for(int i = 0; i < vLabirintos.size(); i++) {
                System.out.println(vLabirintos.get(i));
            }
        } catch (Exception erro) {
            System.err.println(erro.getMessage());
        }
    }
}
