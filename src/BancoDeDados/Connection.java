package BancoDeDados;

import BancoDeDados.core.*;
import BancoDeDados.daos.*;

public class Connection
{
    public static final MeuPreparedStatement COMANDO; // Constante Comando, de MeuPreparedStatement

    static
    {
    	MeuPreparedStatement comando = null;

    	try
        {
            comando =
            new MeuPreparedStatement (
            "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            "jdbc:sqlserver://DESKTOP-HGH6J95\\SQLEXPRESS:1433;databasename=ProjetoIntegrado",
            "grupo", "0000");
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