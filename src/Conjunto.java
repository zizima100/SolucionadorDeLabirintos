import EstruturasDeDados.Clonador;

public class Conjunto<X> implements Cloneable {
    protected Object[] elem; // private X[] elem;
    protected int qtd = 0;
    protected int capInicial;

    public Conjunto(int capInicial) throws Exception {
        if (capInicial <= 0)
            throw new Exception("Capacidade invalida");

        // this.elem = new X [capInicial];
        this.elem = new Object[capInicial];

        this.capInicial = capInicial;
    }

    // construtor padrão
    public Conjunto() {
        // this.elem = new X [10];
        this.elem = new Object[10];
        this.capInicial = 10;
    }

    private void redimensioneSe(int novaCap) {
        // X[] novo = new X [novaCap];
        Object[] novo = new Object[novaCap];

        for (int i = 0; i < qtd; i++)
            novo[i] = this.elem[i];

        this.elem = novo;
    }

    // procura x em this.elem e, se achou:
    // na posicao 0, retorna 1;
    // na posicao 1, retorna 2;
    // na posicao 2, retorna 3;
    // e assim por diante; MAS, se não achou e o lugar para
    // inserir aquilo que foi buscado e não foi encontrado era:
    // a posicao 0, retorna -1;
    // a posicao 1, retorna -2;
    // a posicao 2, retorna -3;
    // e assim por diante.
    protected int ondeEsta(X x) {
        for (int i = 0; i < this.qtd; i++)
            if (x.equals(this.elem[i]))
                return i + 1;

        return -(this.qtd + 1);
    }

    public boolean tem(X x) throws Exception {
        if (x == null)
            throw new Exception("Elemento ausente");

        return this.ondeEsta(x) > 0;
    }

    public void inclua(X x) throws Exception {
        if (x == null)
            throw new Exception("Elemento ausente");

        int posicao = this.ondeEsta(x);

        if (posicao > 0)
            throw new Exception("Elemento repetido");

        posicao = -posicao;
        posicao--;

        if (this.qtd == this.elem.length)
            this.redimensioneSe(2 * this.elem.length);

        for (int i = this.qtd - 1; i >= posicao; i--)
            this.elem[i + 1] = this.elem[i];

        if (x instanceof Cloneable) {
            Clonador<X> clonador = new Clonador<X>();
            this.elem[posicao] = clonador.clone(x);
        } else
            this.elem[posicao] = x;

        this.qtd++;
    }

    public void remova(X x) throws Exception {
        if (x == null)
            throw new Exception("Elemento ausente");

        int posicao = this.ondeEsta(x);

        if (posicao < 0)
            throw new Exception("Elemento inexistente");

        posicao--;

        int i;
        for (i = posicao; i < this.qtd - 1; i++)
            this.elem[i] = this.elem[i + 1];

        this.elem[i] = null;
        this.qtd--;

        if (this.elem.length > this.capInicial && this.qtd <= ((int) (0.25 * this.elem.length)))
            this.redimensioneSe(this.elem.length / 2);
    }

    public X getElemento(int i) throws Exception {
        if (i < 0 || i >= this.qtd)
            throw new Exception("Elemento invalido");

        if (this.elem[i] instanceof Cloneable) {
            Clonador<X> clonador = new Clonador<X>();
            return clonador.clone((X) this.elem[i]);
        } else 
            return (X) this.elem[i];
    }

    // a=b.uniao(c);
    // este método vai unir os conjuntos this e conj
    public Conjunto<X> uniao(Conjunto<X> conj) throws Exception {
        if (conj == null)
            throw new Exception("Conjunto ausente");

        Conjunto<X> ret = new Conjunto<X>(this.qtd + conj.qtd);

        Conjunto<X> menor, maior;

        if (this.qtd < conj.qtd) {
            menor = this;
            maior = conj;
        } else {
            menor = conj;
            maior = this;
        }

        ret.qtd = maior.qtd;
        for (int i = 0; i < maior.qtd; i++)
            ret.elem[i] = maior.elem[i];

        for (int i = 0; i < menor.qtd; i++) {
            try {
                ret.inclua((X) menor.elem[i]);
            } catch (Exception erro) {
            } // ignoro repetidos e toco em frente
        }

        return ret;
    }

    // a=b.interseccao(c);
    public Conjunto<X> interseccao(Conjunto<X> conj) throws Exception {
        if (conj == null)
            throw new Exception("Conjunto ausente");

        Conjunto<X> menor, maior;

        if (this.qtd < conj.qtd) {
            menor = this;
            maior = conj;
        } else {
            menor = conj;
            maior = this;
        }

        Conjunto<X> ret = new Conjunto<X>(menor.qtd);

        for (int i = 0; i < menor.qtd; i++) {
            boolean tem = false;
            try {
                tem = maior.tem((X) menor.elem[i]);
            } catch (Exception erro) {
            } // sei que no conjunto menor nao ha null

            if (tem) {
                ret.elem[ret.qtd] = menor.elem[i];
                ret.qtd++;
            }
        }

        return ret;
    }

    // a=b.menos(c);
    public Conjunto<X> menos(Conjunto<X> conj) throws Exception {
        if (conj == null)
            throw new Exception("Conjunto ausente");

        Conjunto<X> ret = new Conjunto<X>(this.qtd);

        for (int i = 0; i < this.qtd; i++)
            if (conj.ondeEsta((X) this.elem[i]) < 0) {
                ret.elem[ret.qtd] = this.elem[i];
                ret.qtd++;
            }

        return ret;
    }

    // if (a.contem(b)) ...
    public boolean contem(Conjunto<X> conj) throws Exception {
        if (conj == null)
            throw new Exception("Conjunto ausente");

        for (int i = 0; i < conj.qtd; i++)
            if (this.ondeEsta((X) conj.elem[i]) < 0)
                return false;

        return true;
    }

    // if (a.estaContido(b)) ...
    public boolean estaContido(Conjunto<X> conj) throws Exception {
        if (conj == null)
            throw new Exception("Conjunto ausente");

        return conj.contem(this);
    }

    @Override
    public String toString() {
        String ret = "{";

        if (this.qtd > 0)
            ret += this.elem[0];

        for (int i = 1; i < this.qtd; i++)
            ret += "," + this.elem[i];

        ret += "}";

        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        Conjunto<X> conj = (Conjunto<X>) obj;

        if (this.qtd != conj.qtd)
            return false;

        // leva em conta a ordem
        // for (int i=0; i<this.qtd; i++)
        // if (!this.elem[i].equals(conj.elem[i]))
        // return false;

        // sem levar em conta a ordem
        for (int i = 0; i < conj.qtd; i++)
            if (this.ondeEsta((X) conj.elem[i]) < 0)
                return false;

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 1; // qualquer valor positivo serviria

        // 13 é e qualquer primo serviria no lugar do 13
        ret = 13 * ret + new Integer(this.qtd).hashCode();

        for (int i = 0; i < this.qtd; i++)
            // if (this.elem[i]!=null)
            ret = 13 * ret + this.elem[i].hashCode();

        if (ret < 0)
            ret = -ret;

        return ret;
    }

    // construtor de cópia
    public Conjunto(Conjunto<X> modelo) throws Exception {
        if (modelo == null)
            throw new Exception("Modelo ausente");

        this.capInicial = modelo.capInicial;
        this.qtd = modelo.qtd;

        // this.elem = new X [this.modelo.length];
        this.elem = new Object[modelo.elem.length];

        for (int i = 0; i < this.qtd; i++)
            this.elem[i] = modelo.elem[i];
    }

    public Object clone() {
        Conjunto<X> ret = null;

        try {
            ret = new Conjunto<X>(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}
