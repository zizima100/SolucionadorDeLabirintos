package BancoDeDados.daos;

import java.sql.*;

import BancoDeDados.*;
import BancoDeDados.core.*;
import BancoDeDados.dbos.*;

public class labirintos {

    public static boolean cadastrado (int codigo) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM LABIRINTOS " +
                  "WHERE INDICE = ?";

            Connection.COMANDO.prepareStatement (sql);

            Connection.COMANDO.setInt (1, indice);

            MeuResultSet resultado = (MeuResultSet)Connection.COMANDO.executeQuery ();

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
                  "(INDICE,DATA,LABIRINTO) " +
                  "VALUES " +
                  "(?,?,?)";

            Connection.COMANDO.prepareStatement (sql); // preparando tal comando (String sql)

            // Estamos substituindo as interogaçoes por valores de tipos especificos
            Connection.COMANDO.setString (1, labirinto.getIndice ());
            // BDSQLServer.COMANDO.setData    (2, labirinto. ());
            Connection.COMANDO.setString  (3, labirinto.getLabirinto ());

            // Executamos os comandos de sql, insert e delete
            Connection.COMANDO.executeUpdate (); 
            Connection.COMANDO.commit        (); 
        }
        catch (SQLException erro)
        {
			Connection.COMANDO.rollback();
            throw new Exception ("Erro ao inserir labirinto");
        }
    }

    public static void alterar (labirinto labirinto) throws Exception
    {
        if (labirinto==null)
            throw new Exception ("Labirinto não fornecido");

        if (!cadastrado (labirinto.getCodigo()))
            throw new Exception ("Labirinto não cadastrado");

        try
        {
            String sql;

            sql = "UPDATE LABIRINTOS " +
                  "SET DATA=? " + // DEVE SER A NOVA DATA, A DATA ATUAL DA ATT
                  "SET LABIRINTO=? " +
                  "WHERE INDICE = ?";

            Connection.COMANDO.prepareStatement (sql);

            Connection.COMANDO.setString (1, labirinto.getData ());
            Connection.COMANDO.setFloat  (2, labirinto.getLabirinto ());
            Connection.COMANDO.setInt    (3, labirinto.getIndice ());

            Connection.COMANDO.executeUpdate ();
            Connection.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
			Connection.COMANDO.rollback();
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
    public static Labirinto getLabirintos (int indice) throws Exception 
    {
        Labirinto labirinto = null; 

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM LABIRINTOS " +
                  "WHERE INDICE = ?";

            Connection.COMANDO.prepareStatement (sql);

            Connection.COMANDO.setInt (1, indice);

            MeuResultSet resultado = (MeuResultSet)Connection.COMANDO.executeQuery (); // executeQuery para select e retorna objeto de meuResu

            if (!resultado.first()) // first é metodo de meuResultSet -> primeira linha do resultado
                throw new Exception ("Labirinto não cadastrado");

            labirinto = new labirinto (resultado.getInt   ("INDICE"),
                        resultado.getCalendar("DATA"),
                        resultado.getString ("LABIRINTO"));
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

            Connection.COMANDO.prepareStatement (sql);

            resultado = (MeuResultSet)Connection.COMANDO.executeQuery (); // executeQuery para metodos select
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar labirintos");
        }

        return resultado;
    }
}
