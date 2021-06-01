package BancoDeDados.dbos;

import java.io.Serializable;
import java.sql.Date;

public class Labirinto implements Cloneable, Serializable
{
    private int id;
    private String emailCliente;
    private String conteudo;
    private Date dataCriacao; 
    private Date dataEdicao;

    public Labirinto (int id, String emailCliente, String conteudo, Date dataCriacao, Date dataEdicao) throws Exception
    {
        this.id = id;
        this.emailCliente = emailCliente;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriacao;
        this.dataEdicao = dataEdicao;
    }

    public Labirinto (String emailCliente, String conteudo, Date dataCriacao, Date dataEdicao) throws Exception
    {
        this.emailCliente = emailCliente;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriacao;
        this.dataEdicao = dataEdicao;
    }

    public Labirinto (Labirinto clonado) throws Exception
    {
        if (clonado == null) {
            throw new Exception ("Labirinto a ser clonado está vazio");
        }
        this.id = clonado.id;
        this.emailCliente = clonado.emailCliente;
        this.conteudo = clonado.conteudo;
        this.dataCriacao = clonado.dataCriacao;
        this.dataEdicao = clonado.dataEdicao;
    }
    
    public int getId () 
    {
        return this.id;
    }

    public void setEmail (String novoEmail)
    {
        this.emailCliente = novoEmail;
    }

    public String getEmail () {
        return this.emailCliente;
    }

    public Date getDataCriacao () {
        return this.dataCriacao;
    }

    public void setDataEdicao (Date novaData)  throws Exception
    {
        if (novaData == null)
        {
            throw new Exception ("Data está vazia..."); 
        }

        this.dataEdicao = novaData;
    }

    public Date getDataEdicao () 
    {
        return this.dataEdicao;
    }

    public void setConteudo (String novoConteudo)
    {
        this.conteudo = novoConteudo;
    }

    public String getConteudo ()
    {
        return this.conteudo;
    }

    public String toString ()
    {
        String ret="";

        ret+="ID: "+this.id+"\n";
        ret+="Email: "+this.emailCliente+"\n";
        ret+="DataCriacao: "+this.dataCriacao+"\n";
        ret+="DataEdicao: "+this.dataEdicao +"\n";
        ret+="------------------------------------------------";

        return ret;
    }

    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (!(obj instanceof Labirinto))
            return false;

        Labirinto lab = (Labirinto)obj;

        if (this.id!=lab.id)
            return false;

        return true;
    }

    public int hashCode ()
    {
        int ret=666;

        ret = 7*ret + new Integer(this.id).hashCode();
        ret = 7*ret + this.emailCliente.hashCode();
        ret = 7*ret + this.conteudo.hashCode();
        ret = 7*ret + this.dataCriacao.hashCode();
        ret = 7*ret + this.dataEdicao.hashCode();

        return ret;
    }

    public Object clone() {
        Labirinto ret = null;

        try {
            ret = new Labirinto(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}