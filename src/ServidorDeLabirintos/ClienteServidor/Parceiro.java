package ServidorDeLabirintos.ClienteServidor;

import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;

// Classe que deve estar presente tanto no cliente quanto no servidor.

/**
 * Classe que representa o parceiro com o qual o programa está conectado. Se o programa for o cliente, o parceiro será o servidor e vice-versa.
 */
public class Parceiro
{
    private Socket             conexao;
    private ObjectInputStream  receptor;
    private ObjectOutputStream transmissor;
    
    private Comunicado proximoComunicado=null;

    private Semaphore mutEx = new Semaphore (1,true);

    public Parceiro (Socket             conexao,
                     ObjectInputStream  receptor,
                     ObjectOutputStream transmissor)
                     throws Exception // se parametro nulos
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (receptor==null)
            throw new Exception ("Receptor ausente");

        if (transmissor==null)
            throw new Exception ("Transmissor ausente");

        this.conexao     = conexao;
        this.receptor    = receptor;
        this.transmissor = transmissor;
    }

    /**
     * O parceiro do programa atual deve receber um comunicado que será enviado pelo programa.
     * @param x o comunicado enviado pelo programa.
     * @throws Exception Resulta em erros de transmissão, recepção e descone.
     */
    public void receba (Comunicado x) throws Exception
    {
        try
        {
            this.transmissor.writeObject (x);
            this.transmissor.flush       ();
        }
        catch (IOException erro)
        {
            throw new Exception ("Erro de transmissao");
        }
    }

    public Comunicado espie () throws Exception
    {
        System.out.println("Entrei no Espie!");
        try
        {
            System.out.println("Próximo comunicado inicio-espie: " + proximoComunicado);
            this.mutEx.acquireUninterruptibly();
            if (this.proximoComunicado==null) {
                this.proximoComunicado = (Comunicado)this.receptor.readObject();
            }
            this.mutEx.release();
            System.out.println("Próximo comunicado fim-espie: " + proximoComunicado);
            return this.proximoComunicado;
        }
        catch (Exception erro)
        {
            throw new Exception ("Erro de recepcao");
        }
    }

    /**
     * O parceiro do programa atual deve enviar um comunicado que será recebido pelo programa.
     * @return retorna o próximo comunicado como nulo (próximo comunicado foi enviado).
     * @throws Exception Resulta em erros de recepção e desconexão.
     */
    public Comunicado envie () throws Exception
    {
        try
        {
            System.out.println("Recebi para envio: " + this.proximoComunicado);
            if (this.proximoComunicado == null) {
                this.proximoComunicado = (Comunicado)this.receptor.readObject();
            }
            Comunicado ret = this.proximoComunicado;
            this.proximoComunicado = null;
            System.out.println("Sai para envio: " + ret);
            return ret;
        }
        catch (Exception erro)
        {
            throw new Exception ("Erro de recepcao");
        }
    }

    /**
     * Encerramento do programa atual.
     * @throws Exception Resulta em erro de desconexão.
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

