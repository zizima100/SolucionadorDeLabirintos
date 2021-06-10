package BancoDeDados.daos;

import java.sql.*;
import java.util.*;

import BancoDeDados.*;
import BancoDeDados.core.*;
import BancoDeDados.dbos.*;

/**
 * Classe utilizada para realizar operações básicas, relacionadas ao labirinto, no banco de dados 
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class Labirintos {

    /**
     * Executa queries, que procuram um labirinto especifico
     * @param id do labirinto que é desejado ser aberto
     * @return retorna o primeiro valor encontrado, que é o labirinto da querie
     * @throws Exception retorna a excessão caso o id do labirinto não seja encontrado 
     */
    public static boolean cadastrado(int id) throws Exception {
        boolean retorno = false;

        try {
            String sql;

            sql = "SELECT * " + "FROM Labirintos " + "WHERE id = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, id);

            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            retorno = resultado.first();
        } catch (SQLException erro) {
            throw new Exception("Erro ao procurar labirinto");
        }

        return retorno; // True ou False
    }

    /**
     * Inclusão do labirinto no banco de dados
     * @param labirinto que será adicionado no banco de dados 
     * @throws Exception retorna a excessão caso o labirinto não seja fornecido 
     */
    public static void incluir(Labirinto labirinto) throws Exception {
        if (labirinto == null)
            throw new Exception("O labirinto não foi fornecido...");

        try {
            String sql;

            sql = "INSERT INTO LABIRINTOS " + "(emailCliente,conteudo,dataCriacao, dataEdicao) " + "VALUES "
                    + "(?,?,?,?)";

            BDSQLServer.COMANDO.prepareStatement(sql); // preparando tal comando (String sql)

            // Estamos substituindo as interogaçoes por valores de tipos especificos
            BDSQLServer.COMANDO.setString(1, labirinto.getEmail());
            // BDSQLServer.COMANDO.setData (2, labirinto. ());
            BDSQLServer.COMANDO.setString(2, labirinto.getConteudo());
            BDSQLServer.COMANDO.setDate(3, labirinto.getDataCriacao());
            BDSQLServer.COMANDO.setDate(4, labirinto.getDataEdicao());

            // Executamos os comandos de sql, insert e delete
            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        } catch (SQLException erro) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao inserir labirinto");
        }
    }

    /**
     * Altera o conteúdo e data de edição de um labirinto já existe
     * @param labirinto que será alterado
     * @throws Exception retorna excessão caso o labirinto não seja fornecido
     */
    public static void alterar(Labirinto labirinto) throws Exception {
        if (labirinto == null)
            throw new Exception("Labirinto não fornecido");

        if (!cadastrado(labirinto.getId()))
            throw new Exception("Labirinto não cadastrado");

        try {
            String sql;

            sql = "UPDATE Labirintos " + "SET dataEdicao = ?, " + // DEVE SER A NOVA DATA, A DATA ATUAL DA ATT
                    "conteudo = ? " + "WHERE id = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setDate(1, labirinto.getDataEdicao());
            BDSQLServer.COMANDO.setString(2, labirinto.getConteudo());
            BDSQLServer.COMANDO.setInt(3, labirinto.getId());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        } catch (SQLException erro) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao atualizar dados de labirinto");
        }
    }

    // Método equivalente ao select
    /**
     * Seleciona determinado labirinto de acordo com o id correspondente
     * @param indice corresponde ao indice do labirinto que eu quero retirar,
     *               selecionar.
     * @return retorna o labirinto correspondente ao id do parâmetro 
     * @throws Exception retorna uma excessão caso não seja encontrado labirinto com tal indice 
     */
    public static Labirinto getLabirinto(int id) throws Exception {
        Labirinto labirinto = null;

        try {
            String sql;

            sql = "SELECT * " + "FROM Labirintos " + "WHERE id = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, id);

            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery(); // executeQuery para select e
                                                                                        // retorna objeto de meuResu

            if (!resultado.first()) // first é metodo de meuResultSet -> primeira linha do resultado
                throw new Exception("Labirinto não cadastrado");

            labirinto = new Labirinto(resultado.getInt("id"), resultado.getString("emailCliente"),
                    resultado.getString("conteudo"), resultado.getDate("dataCriacao"), resultado.getDate("dataEdicao"));
        } catch (SQLException erro) {
            throw new Exception("Erro ao procurar labirinto com tal indice");
        }

        return labirinto;
    }

    /**\
     * Verifica se o labirinto se encontra no banco de dados
     */
    public static boolean isInBanco(int id) throws Exception {
        try {
            String sql;

            sql = "SELECT * " + "FROM Labirintos " + "WHERE id = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, id);

            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery(); // executeQuery para select e
                                                                                        // retorna objeto de meuResu

            if (!resultado.first()) {
                return false;
            }
            return true;
        } catch (SQLException erro) {
            throw new Exception("Erro ao procurar labirinto com tal indice");
        }
    }

    // Método para recuperar todos os labirintos
    /**
     * Retorna todos os labirintos inseridos dentro de um determinado email 
     * @param email no qual queremos listar todos os labirintos cadastrados
     * @return retorna um vetor de labirinto
     * @throws Exception
     */
    public static Vector<Labirinto> getLabirintos(String email) throws Exception {
        MeuResultSet resultado = null;
        Vector<Labirinto> vLabirintos = new Vector<Labirinto>();

        try {
            String sql;

            sql = "SELECT * " + "FROM Labirintos " + "WHERE emailCliente = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setString(1, email);

            resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            if (!resultado.first()) {
                return vLabirintos;
                // throw new Exception("Labirinto não cadastrado");
            } // first é metodo de meuResultSet -> primeira linha do resultado

            do {
                vLabirintos.add(new Labirinto(resultado.getInt("id"), resultado.getString("emailCliente"),
                        resultado.getString("conteudo"), resultado.getDate("dataCriacao"),
                        resultado.getDate("dataEdicao")));
            } while (resultado.next());
        } catch (SQLException erro) {
            throw new Exception("Erro ao recuperar labirintos");
        }
        return vLabirintos;
    }

    /**
     * Retorna todos os labirintos inseridos da tabela do banco de dados, portanto: todos os labirintos
     * @return retorna um vetor contendo todos os labirintos
     * @throws Exception
     */
    public static Vector<Labirinto> getTodosLabirintos() throws Exception {
        MeuResultSet resultado = null;
        Vector<Labirinto> vLabirintos = new Vector<Labirinto>();

        try {
            String sql;

            sql = "SELECT * " + "FROM Labirintos";

            BDSQLServer.COMANDO.prepareStatement(sql);

            resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            if (!resultado.first()) {
                throw new Exception("Nenhum labirinto encontrado");
            } // first é metodo de meuResultSet -> primeira linha do resultado

            do {
                vLabirintos.add(new Labirinto(resultado.getInt("id"), resultado.getString("emailCliente"),
                        resultado.getString("conteudo"), resultado.getDate("dataCriacao"),
                        resultado.getDate("dataEdicao")));
            } while (resultado.next());
        } catch (SQLException erro) {
            throw new Exception("Erro ao recuperar labirintos");
        }
        return vLabirintos;
    }
}
