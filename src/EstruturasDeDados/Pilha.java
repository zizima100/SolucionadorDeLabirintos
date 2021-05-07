package EstruturasDeDados;
/**
 * A classe Pilha é destinada a criação de pilhas padrão.
 *
 * Instâncias desta classe permitem que as operações padrão de uma pilha seja
 * feita.
 */

public class Pilha<valorGenerico> // Classe Pilha que pode receber qualquer tipo de valor
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

        this.topo--;
        return (valorGenerico) this.elemento[this.topo];
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
        return (valorGenerico) aux;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        Pilha<valorGenerico> pAux = (Pilha<valorGenerico>) obj;

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
}
