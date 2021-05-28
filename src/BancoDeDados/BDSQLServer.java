package BancoDeDados;

import BancoDeDados.core.*;
import BancoDeDados.daos.*;
// import java.sql.DatabaseMetaData;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;

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
                "jdbc:sqlserver://jdvpic.database.windows.net;databasename=ProjetoIntegradoC",
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