package ServidorDeLabirintos.Compartilhado;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Representa o servidor do cliente ou o cliente conectado no servidor (A depender de qual classe está sendo instânciado)
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class Parceiro
{
    private Socket conexao;
    private ObjectInputStream receptor;
    private ObjectOutputStream transmissor;
    
    private Comunicado proximoComunicado=null;

    private Semaphore mutEx = new Semaphore (1,true);

    public Parceiro (Socket conexao,
                     ObjectInputStream receptor,
                     ObjectOutputStream transmissor)
                     throws Exception // se parametro nulos
    {
        if (conexao==null) {
            throw new Exception ("Conexao ausente");
        }

        if (receptor==null) {
            throw new Exception ("Receptor ausente");
        }

        if (transmissor==null) {
            throw new Exception ("Transmissor ausente");
        }
        this.conexao = conexao;
        this.receptor = receptor;
        this.transmissor = transmissor;
    }

    /**
     * Faz com que o parceiro desse programa receba um comunicado
     * @param x comunicado 
     * @throws Exception
     */
    public void receba (Comunicado x) throws Exception
    {
        try
        {
            this.transmissor.writeObject (x);
            this.transmissor.flush ();
        }
        catch (IOException erro)
        {
            throw new Exception ("Erro de transmissao");
        }
    }

    /**
     * Faz com que o programa atual espie o próximo comunicado do parceiro 
     * @return retorna o próximo comunicado
     * @throws Exception
     */
    public Comunicado espie () throws Exception
    {
        try
        {
            this.mutEx.acquireUninterruptibly();
            if (this.proximoComunicado==null) {
                this.proximoComunicado = (Comunicado)this.receptor.readObject();
            }
            this.mutEx.release();
            return this.proximoComunicado;
        }
        catch (Exception erro)
        {
            erro.printStackTrace();
            throw new Exception ("Erro de recepcao espie");
        }
    }

    /**
     * Faz com que o parceiro do programa atual envie o próximo comunicado  
     * @return retorna o próximo comunicado 
     * @throws Exception
     */
    public Comunicado envie () throws Exception
    {
        try
        {
            if (this.proximoComunicado==null) {
                this.proximoComunicado = (Comunicado)this.receptor.readObject();
            }
            Comunicado ret = this.proximoComunicado;
            this.proximoComunicado = null;
            return ret;
        }
        catch (Exception erro)
        {
            throw new Exception ("Erro de recepcao");
        }
    }

    /**
     * Encerra a conexão do parceiro com o programa atual
     * @throws Exception
     */
    public void adeus () throws Exception
    {
        try
        {
            this.transmissor.close();
            this.receptor   .close();
            this.conexao    .close();
        }
        catch (Exception erro)
        {
            throw new Exception ("Erro de desconexao");
        }
    }
}
