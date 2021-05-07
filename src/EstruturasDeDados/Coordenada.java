package EstruturasDeDados;

/**
 * A classe coordenada guarda um vetor de inteiros com 2 elementos.
 * <p>
 * Os elementos desse vetor correspondem à linha e à coluna de uma matriz,
 * respectivamente.
 * <p>
 */

public class Coordenada {
    private Integer[] elementos;

    /**
     * Cria um objecto do tipo coordenada vazio.
     */
    public Coordenada() {
        this.elementos = new Integer[2];
    }

    /**
     * Cria um objeto do tipo coordenada com os parâmetros passados.
     * 
     * @param linha  corresponde à linha que a coordenada indicará.
     * @param coluna corresponde à coluna que a coordenada indicará.
     * @throws Exception
     */
    public Coordenada(int linha, int coluna) throws Exception {
        this.elementos = new Integer[2];
        this.elementos[0] = linha;
        this.elementos[1] = coluna;
    }

    /**
     * 
     * @return retorna um vetor contendo a linha e a coluna da coordenada.
     */
    public Integer[] getCoordenada() {
        return this.elementos;
    }

    /**
     * Permite alterar a linha e a coluna da coordenada.
     * 
     * @param linha  altera a linha da coordenada atual.
     * @param coluna altera a coluna da coordenada atual.
     */
    public void setCoordenada(int linha, int coluna) {
        this.elementos[0] = linha;
        this.elementos[1] = coluna;
    }

    /**
     * @return retorna a linha da coordenada atual.
     * @throws Exception retorna erro caso o elemento linha do vetor esteja vazio.
     */
    public int getLinha() throws Exception {
        if (this.elementos[0] == null)
            throw new Exception("Nenhuma linha armazenada nessa coordenada.");
        return this.elementos[0];
    }

    /**
     * @return retorna a coluna da coordenada atual.
     * @throws Exception retorna erro caso o elemento coluna do vetor esteja vazio.
     */
    public int getColuna() throws Exception {
        if (this.elementos[1] == null)
            throw new Exception("Nenhuma coluna armazenada nessa coordenada.");
        return this.elementos[1];
    }

    @Override
    public String toString() {
        return "{" + elementos[0] + "," + elementos[1] + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Coordenada comparada = (Coordenada) obj;
        if (comparada.elementos[0] == this.elementos[0] && comparada.elementos[1] == this.elementos[1]) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 3 * hash + elementos[0].hashCode();
        hash = 3 * hash + elementos[1].hashCode();
        if (hash < 0) {
            hash = -hash;
        }
        return hash;
    }
}
