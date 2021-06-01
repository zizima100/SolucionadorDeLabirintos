package BancoDeDados;

import java.sql.*;
import BancoDeDados.daos.*;
import BancoDeDados.core.*;
import BancoDeDados.dbos.*;
import java.util.*;

public class teste {
    public static void main(String[] args) {

        try {
            String teste = "3245\njkdnfksdfkjsdf";
            System.out.println(teste.indexOf('\n'));
        } catch (Exception erro) {
            System.err.println(erro.getMessage());
        }
    }
}
