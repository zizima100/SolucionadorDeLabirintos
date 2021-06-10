package App.EstruturasDeDados;

/**
 * A classe Pilha é destinada a criação de pilhas padrão.
 *
 * Instâncias desta classe permitem que as operações padrão de uma pilha seja
 * feita.
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class Pilha<valorGenerico> implements Cloneable// Classe Pilha que pode receber qualquer tipo de valor
{
    private Object[] elemento;
    private int topo;

    /**
     * Instancia uma pilha do tamanho desejado.
     * 
     * @param tamanho representa o número de elementos que será suportado pela pilha
     *                inicialmente.
     * @throws Exception retorna erro caso o valor inserido seja menor ou igual a
     *                   zero.
     */
    public Pilha(int tamanho) throws Exception {
        if (tamanho <= 0)
            throw new Exception("Tamanho da pilha invalido");

        this.elemento = new Object[tamanho]; // declara o tamanho do vetor
        this.topo = -1;
    }

    /**
     * Instancia uma pilha idêntica à pilha modelo.
     * @param modelo pilha modelo.
     * @throws Exception retorna erro caso o modelo seja nulo.
     */
    public Pilha(Pilha<valorGenerico> modelo) throws Exception {
        if (modelo == null)
            throw new Exception("Modelo para clonar ausente");
        this.elemento = new Object[modelo.elemento.length];
        this.topo = modelo.topo;

        for (int i = 0; i < modelo.elemento.length; i++) {
            this.elemento[i] = modelo.elemento[i];
        }
    }

    /**
     * Insere um novo valor a Pilha
     * 
     * @param valor representa o valor a ser inserido na Pilha.
     * @throws Exception retorna erro caso o valor inserido seja menor ou igual a
     *                   zero.
     */
    public void push(valorGenerico valor) throws Exception { // push guarda elemento
        if (valor == null)
            throw new Exception("Tentou guardar NULL");

        if (this.topo + 1 == this.elemento.length)
            throw new Exception("Pilha esta cheia");

        this.topo++;
        if (valor instanceof Cloneable) {
            Clonador<valorGenerico> clonador = new Clonador<valorGenerico>();
            this.elemento[this.topo] = clonador.clone(valor);
        } else
            this.elemento[this.topo] = valor;
    }

    /**
     * Exibe o último valor armazenado na Pilha
     * 
     * @throws Exception retorna erro caso o valor inserido seja menor ou igual a
     *                   zero.
     */
    public Object top() throws Exception {
        if (this.topo == -1)
            throw new Exception("Pilha vazia");

        if (this.elemento[this.topo] instanceof Cloneable) {
            Clonador<valorGenerico> clonador = new Clonador<valorGenerico>();
            return clonador.clone((valorGenerico) this.elemento[this.topo]);
        } else {
            return (valorGenerico) this.elemento[this.topo];
        }
    }

    /**
     * Realiza o desempilhamento do último valor inserido na Pilha.
     * 
     * @throws Exception retorna erro caso o valor inserido seja menor ou igual a
     *                   zero.
     */
    public Object pop() throws Exception {
        if (this.topo == -1)
            throw new Exception("Pilha vazia");

        Object aux = this.elemento[this.topo];
        this.elemento[this.topo] = null;
        this.topo--;
        if (aux instanceof Cloneable) {
            Clonador<valorGenerico> clonador = new Clonador<valorGenerico>();
            return clonador.clone((valorGenerico) aux);
        } else {
            return (valorGenerico) aux;
        }
    }

    /**
     * Verifica se a Pilha está cheia, retornando um valor booleano true, caso a
     * Pilha esteja cheia, ou false, caso contrário.
     */
    public boolean isCheia() {
        return this.topo + 1 == this.elemento.length;
    }

    /**
     * Verifica se a Pilha está vazia, retornando um valor booleano true, caso a
     * Pilha esteja vazia, ou false, caso contrário.
     */
    public boolean isVazia() {
        return this.topo == -1;
    }

    /**
     * Verifica se a pilha modelo é igual à pilha instânciada.
     * 
     * @param modelo pilha modelo.
     * @return Retorna true se for igual e false se não.
     */
    @Override
    public boolean equals(Object modelo) {
        if (this == modelo)
            return true;

        if (modelo == null)
            return false;

        if (this.getClass() != modelo.getClass())
            return false;

        Pilha<valorGenerico> pAux = (Pilha<valorGenerico>) modelo;

        if (this.topo != pAux.topo)
            return false;

        if (this.elemento.length != pAux.elemento.length)
            return false;

        for (int i = 0; i <= this.topo; i++) {
            if (!this.elemento[i].equals(pAux.elemento[i]))
                return false;
        }
        return true;
    }

    /**
     * Gera o hashcode da pilha instânciada.
     * 
     * @return Retorna o hashcode da pilha instânciada.
     */
    @Override
    public int hashCode() {
        int ret = 666;

        Integer i = this.topo;

        ret = 3 * ret + i.hashCode();

        for (int cont = 0; cont <= this.topo; cont++) {
            ret = ret * 3 + this.elemento[cont].hashCode();
        }

        if (ret < 0)
            ret = -ret;

        return ret;
    }

    /**
     * Imprime informações sobre a pilha.
     * 
     * @return Retorna uma String que mostra quantos elementos estão presentes na pilha e qual elemento está no topo da pilha.
     */
    @Override
    public String toString() {
        String ret;

        if (this.topo == 0)
            ret = "1 elemento";
        else
            ret = (this.topo + 1) + " elementos";

        if (this.topo != -1)
            ret += ", sendo o ultimo " + this.elemento[this.topo];

        return ret;
    }

    /**
     * Cria uma cópia da pilha instânciada.
     * 
     * @return Retorna uma pilha igual à pilha instânciada.
     */
    public Object clone() {
        Pilha<valorGenerico> ret = null;

        try {
            ret = new Pilha<valorGenerico>(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}
