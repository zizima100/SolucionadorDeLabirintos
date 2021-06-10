package BancoDeDados;

import BancoDeDados.core.*;
import BancoDeDados.daos.*;

/**
 * Conexão com o banco de dados SQL Server
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class BDSQLServer
{
    public static final MeuPreparedStatement COMANDO; // Constante Comando, de MeuPreparedStatement

    static
    {
    	MeuPreparedStatement comando = null;
        // Connection conn = null;
    	try
        {
            comando =
            new MeuPreparedStatement (
                "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                "jdbc:sqlserver://jdvpic2.database.windows.net;databasename=ProjetoIntegradoC",
                "grupo", "jdvpic123!");
        }
        catch (Exception erro)
        {
            System.err.println(erro.getMessage());
            System.err.println ("Problemas de conexao com o BD");
            System.exit(0); // aborta o programa
        }
        
        COMANDO = comando;
    }
}