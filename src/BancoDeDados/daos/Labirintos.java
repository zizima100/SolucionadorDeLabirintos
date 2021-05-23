package BancoDeDados.daos;

import java.sql.*;

import BancoDeDados.*;
import BancoDeDados.core.*;
import BancoDeDados.dbos.*;

public class Labirintos {

    public static boolean cadastrado (int id) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM Labirintos " +
                  "WHERE id = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, id);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            retorno = resultado.first();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar labirinto");
        }

        return retorno; // True ou False 
    }

    public static void incluir (Labirinto labirinto) throws Exception
    {
        if (labirinto == null)
            throw new Exception ("O labirinto não foi fornecido...");

        try
        {
            String sql;

            sql = "INSERT INTO LABIRINTOS " +
                  "(emailCliente,conteudo,dataCriacao, dataEdicao) " +
                  "VALUES " +
                  "(?,?,?,?)";

            BDSQLServer.COMANDO.prepareStatement (sql); // preparando tal comando (String sql)

            // Estamos substituindo as interogaçoes por valores de tipos especificos
            BDSQLServer.COMANDO.setString (1, labirinto.getEmail());
            // BDSQLServer.COMANDO.setData    (2, labirinto. ());
            BDSQLServer.COMANDO.setString (2, labirinto.getConteudo());
            BDSQLServer.COMANDO.setDate (3, labirinto.getDataCriacao());
            BDSQLServer.COMANDO.setDate (4, labirinto.getDataEdicao());

            // Executamos os comandos de sql, insert e delete
            BDSQLServer.COMANDO.executeUpdate (); 
            BDSQLServer.COMANDO.commit        (); 
        }
        catch (SQLException erro)
        {
			BDSQLServer.COMANDO.rollback();
            throw new Exception ("Erro ao inserir labirinto");
        }
    }

    public static void alterar (Labirinto labirinto) throws Exception
    {
        if (labirinto==null)
            throw new Exception ("Labirinto não fornecido");

        if (!cadastrado (labirinto.getId()))
            throw new Exception ("Labirinto não cadastrado");

        try
        {
            String sql;

            sql = "UPDATE Labirintos " +
                  "SET dataEdicao = ?, " + // DEVE SER A NOVA DATA, A DATA ATUAL DA ATT
                  "conteudo = ? " +
                  "WHERE id = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setDate (1, labirinto.getDataEdicao());
            BDSQLServer.COMANDO.setString (2, labirinto.getConteudo());
            BDSQLServer.COMANDO.setInt (3, labirinto.getId());

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
			BDSQLServer.COMANDO.rollback();
            throw new Exception ("Erro ao atualizar dados de labirinto");
        }
    }

    // Método equivalente ao select
    /**
     * 
     * @param indice corresponde ao indice do labirinto que eu quero retirar, selecionar.
     * @return
     * @throws Exception
     */
    public static Labirinto getLabirinto (int id) throws Exception 
    {
        Labirinto labirinto = null; 

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM Labirintos " +
                  "WHERE id = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, id);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery (); // executeQuery para select e retorna objeto de meuResu

            if (!resultado.first()) // first é metodo de meuResultSet -> primeira linha do resultado
                throw new Exception ("Labirinto não cadastrado");

            labirinto = new Labirinto (resultado.getInt("id"), resultado.getString("emailCliente"), resultado.getString("conteudo"), resultado.getDate("dataCriacao"), resultado.getDate("dataEdicao"));
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar labirinto com tal indice");
        }

        return labirinto;
    }

    // Método para recuperar todos os labirintos
    public static MeuResultSet getLabirintos () throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM LABIRINTOS";

            BDSQLServer.COMANDO.prepareStatement (sql);

            resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery (); // executeQuery para metodos select
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar labirintos");
        }

        return resultado;
    }
}
