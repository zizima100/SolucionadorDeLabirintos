package BancoDeDados.dbos;

import java.util.Calendar;

public class Labirinto implements Cloneable
{
    private int    indice;
    private Calendar data; 

    // private Calendar dia = Calendar.DAY_OF_MONTH;
    // private Calendar mes = Calendar.MONTH;
    // private Calendar ano = Calendar.YEAR; 

    // Calendar c = Calendar.getIntance();
    
    public void setIndice (int indice) throws Exception
    {
        if (indice <= 0)
            throw new Exception ("indice invalido");

        this.indice = indice;
    }   

    public int getIndice () 
    {
        return this.indice;
    }

    public Calendar getData () {
        return this.data;
    }

    public Calendar setData (Calendar data)  throws Exception
    {
        if (data == null)
        {
            throw new Exception ("Data está vazia..."); 
        }

        this.data = data;
    }

    public Labirinto (int indice, Calendar data, String labirinto) throws Exception
    {
        this.setIndice(indice);
        this.setData(data); 
        this.setLabirinto(labirinto);
    }

    public String toString ()
    {
        String ret="";

        ret+="Indice: "+this.indice+"\n";
        ret+="Data..: "+this.data  +"\n";
        ret+="Labirinto.: "+this.labirinto;

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

        if (this.indice!=lab.indice)
            return false;

        if (this.nome.equals(liv.nome))
            return false;

        if (this.preco!=liv.preco)
            return false;

        return true;
    }

    public int hashCode ()
    {
        int ret=666;

        ret = 7*ret + new Integer(this.codigo).hashCode();
        ret = 7*ret + this.nome.hashCode();
        ret = 7*ret + new Float(this.preco).hashCode();

        return ret;
    }


    public Livro (Livro modelo) throws Exception
    {
        this.codigo = modelo.codigo; // nao clono, pq nao eh objeto
        this.nome   = modelo.nome;   // nao clono, pq nao eh clonavel
        this.preco  = modelo.preco;  // nao clono, pq nao eh objeto
    }

    public Object clone ()
    {
        Livro ret=null;

        try
        {
            ret = new Livro (this);
        }
        catch (Exception erro)
        {} // nao trato, pq this nunca � null e construtor de
           // copia da excecao qdo seu parametro for null

        return ret;
    }
}