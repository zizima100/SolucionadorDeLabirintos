package ServidorDeLabirintos.Servidor;

import java.io.*;

/**
 * Permite a digitação através de comandos de uma linha na parte do servidor.
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class Teclado
{
    private static BufferedReader teclado = new BufferedReader (new InputStreamReader (System.in));

    /**
     * Lê uma string do teclado do servidor
     * @return retorna uma string
     */
    public static String getUmString ()
    {
        String ret = null;

        try
        {
            ret = teclado.readLine ();
        }
        catch (IOException erro)
        {} // sei que nao vai dar erro

        return ret;
    }

    /**
     * Transforma em Byte o que foi lido na linha do teclado do servidor
     * @return retorna um byte da linha lida 
     * @throws Exception
     */
    public static byte getUmByte () throws Exception
    {
        byte ret=(byte)0;

        try
        {
            ret = Byte.parseByte (teclado.readLine ());
        }
        catch (IOException erro)
        {} // sei que nao vai dar erro
        catch (NumberFormatException erro)
        {
            throw new Exception ("Byte invalido!");
        }

        return ret;
    }
 
    /**
     * Transforma em short o que foi lido na linha do teclado do servidor
     * @return retorna um short da linha lida
     * @throws Exception
     */
    public static short getUmShort () throws Exception
    {
        short ret=(short)0;

        try
        {
            ret = Short.parseShort (teclado.readLine ());
        }
        catch (IOException erro)
        {} // sei que nao vai dar erro
        catch (NumberFormatException erro)
        {
            throw new Exception ("Short invalido!");
        }

        return ret;
    }

    /**
     * Transforma em integer o que foi lido na linha do teclado do servidor 
     * @return retorna um valor inteiro lido
     * @throws Exception
     */
    public static int getUmInt () throws Exception
    {
        int ret=0;

        try
        {
            ret = Integer.parseInt (teclado.readLine ());
        }
        catch (IOException erro)
        {} // sei que nao vai dar erro
        catch (NumberFormatException erro)
        {
            throw new Exception ("Int invalido!");
        }

        return ret;
    }

    /**
     * Transforma em long o que foi lido na linha do teclado do servidor
     * @return retorna um valor long lido
     * @throws Exception
     */
    public static long getUmLong () throws Exception
    {
      //long ret=(long)0;
      //long ret=0;
        long ret=0L;

        try
        {
            ret = Long.parseLong (teclado.readLine ());
        }
        catch (IOException erro)
        {} // sei que nao vai dar erro
        catch (NumberFormatException erro)
        {
            throw new Exception ("Long invalido!");
        }

        return ret;
    }

    /**
     * Transforma em float o que foi lido na linha do teclado do servidor
     * @return retorna um valor float lido
     * @throws Exception
     */
    public static float getUmFloat () throws Exception
    {
      //float ret=0;
      //float ret=(float)0.0;
        float ret=0.0F;

        try
        {
            ret = Float.parseFloat (teclado.readLine ());
        }
        catch (IOException erro)
        {} // sei que nao vai dar erro
        catch (NumberFormatException erro)
        {
            throw new Exception ("Float invalido!");
        }

        return ret;
    }

    /**
     * Transforma em double o que foi lido na linha do teclado do servidor
     * @return retorna um double lido 
     * @throws Exception
     */
    public static double getUmDouble () throws Exception
    {
      //double ret=0;
      //double ret=(long)0;
      //double ret=0L;
        double ret=0.0;

        try
        {
            ret = Double.parseDouble (teclado.readLine ());
        }
        catch (IOException erro)
        {} // sei que nao vai dar erro
        catch (NumberFormatException erro)
        {
            throw new Exception ("Double invalido!");
        }

        return ret;
    }

    /**
     * Transforma em char o que foi lido na linha do teclado do servidor
     * @return retorna um char lido 
     * @throws Exception
     */
    public static char getUmChar () throws Exception
    {
        char ret=' ';

        try
        {
            String str = teclado.readLine ();

            if (str==null)
                throw new Exception ("Char invalido!");

            if (str.length() != 1)
                throw new Exception ("Char invalido!");

             ret = str.charAt(0);
        }
        catch (IOException erro)
        {} // sei que nao vai dar erro

        return ret;
    }

    /**
     * Transforma em bookean o que foi lido na linha do teclado do servidor
     * @return retorna um boolean lido 
     * @throws Exception
     */
    public static boolean getUmBoolean () throws Exception
    {
        boolean ret=false;

        try
        {
            String str = teclado.readLine ();

            if (str==null)
                throw new Exception ("Boolean invalido!");

            if (!str.equals("true") && !str.equals("false"))
                throw new Exception ("Boolean invalido!");

            ret = Boolean.parseBoolean (str);
        }
        catch (IOException erro)
        {} // sei que nao vai dar erro

        return ret;
    }
}