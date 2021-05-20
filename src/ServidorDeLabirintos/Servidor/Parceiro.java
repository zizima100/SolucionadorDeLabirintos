package ServidorDeLabirintos.Servidor;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Quando usado nessa classe, o Parceiro representa um dos vários cliente conectados no server
 * (Pois estamos no servidor e quem é parceiro dele é o cliente).
 * OBS: Vários parceiros, pois há varios clientes conectados ao servidor
 */
public class Parceiro
{
    private Socket             conexao;
    private ObjectInputStream  receptor; // associado ao socket // receber dados
    private ObjectOutputStream transmissor; // associado ao socket // transmitir dados
    
    private Comunicado proximoComunicado = null; // analisa qual é o comunicado que esta vindo sem consumir esse comunicado

    private Semaphore mutEx = new Semaphore (1,true); // mutua exclusao // Semaforo dispoe de um recurso e quando esse recurso for solicitado novamente,
                                                     // vai gerar bloqueio so solicitante

    // construtor de parceiro 
    // Parceiro vai ser um programa que se comunica com um programa
    //      No cliente - parceiro = servidor
    //      No servidor - parceiro = cliente
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

    // Metodo serve para o programa que executa transmitir e o parceiro receber 
    // exemplo: Se estou no Cliente e: servidor.receba() 
    //      quem transmitira será o servidor e quem vai receber será os clientes
    public void receba (Comunicado x) throws Exception
    {
        try
        {
            // objeto enviado pelo transmissor
            this.transmissor.writeObject (x);
            this.transmissor.flush       ();
        }
        catch (IOException erro)
        {
            throw new Exception ("Erro de transmissao");
        }
    }

    // Sabe o que foi mandado para mim sem consumir o que foi mandado
    public Comunicado espie () throws Exception
    {
        try
        {
            this.mutEx.acquireUninterruptibly(); // recurso do semaforo zerou e nao pode ser pedida novamente, pois recurso zerou 
            
            // condicao e leitura é uma operacao unitaria do thread
            if (this.proximoComunicado==null) 
                this.proximoComunicado = (Comunicado)this.receptor.readObject();// objeto do receptor é lido 
            this.mutEx.release(); // libera o recurso, que fica 1
            return this.proximoComunicado; // se for feito varios espie, vai retornar o mesmo comunicado de antes
        }
        catch (Exception erro)
        {
            throw new Exception ("Erro de recepcao");
        }
    }

    // Programa que executa recebe informacoes
    // EX: Se estou no cliente e: servidor.envie()
    //      Como estou no cliente, ele que vai receber 
    public Comunicado envie () throws Exception
    {
        try
        {
            if (this.proximoComunicado==null) 
                this.proximoComunicado = (Comunicado)this.receptor.readObject();
            Comunicado ret         = this.proximoComunicado; // vai enviar o comunicado lido em espie
            this.proximoComunicado = null;
            return ret;
        }
        catch (Exception erro)
        {
            throw new Exception ("Erro de recepcao");
        }
    }

    // interrompe conexao com parceiro
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

