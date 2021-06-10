package App.EstruturasDeDados;

/**
 * A classe coordenada guarda um vetor de inteiros com 2 elementos.
 * <p>
 * Os elementos desse vetor correspondem à linha e à coluna de uma matriz,
 * respectivamente.
 * <p>
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class Coordenada implements Cloneable {
    private Integer[] elementos = new Integer[2];

    /**
     * Cria um objecto do tipo coordenada vazio.
     */
    public Coordenada() {
    }

    /**
     * Cria um objeto do tipo coordenada idêntico ao modelo fornecido.
     * 
     * @param modelo Coordenada que será clonada.
     */
    public Coordenada(Coordenada modelo) throws Exception {
        if (modelo == null) {
            throw new Exception("Modelo a ser clonado ausente");
        }
        this.elementos[0] = modelo.elementos[0];
        this.elementos[1] = modelo.elementos[1];
    }

    /**
     * Cria um objeto do tipo coordenada com os parâmetros passados.
     * 
     * @param linha  corresponde à linha que a coordenada indicará.
     * @param coluna corresponde à coluna que a coordenada indicará.
     * @throws Exception
     */
    public Coordenada(int linha, int coluna) throws Exception {
        this.elementos[0] = linha;
        this.elementos[1] = coluna;
    }

    /**
     * Retorna o vetor da coordenada.
     * 
     * @return retorna um vetor contendo a linha e a coluna da coordenada.
     */
    public Integer[] getCoordenada() {
        return this.elementos.clone();
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
     * Retorna a linha da coordenada instânciada.
     * 
     * @return retorna a linha da coordenada instânciada.
     * @throws Exception retorna erro caso o elemento linha do vetor esteja vazio.
     */
    public int getLinha() throws Exception {
        if (this.elementos[0] == null)
            throw new Exception("Nenhuma linha armazenada nessa coordenada.");
        return this.elementos[0];
    }

    /**
     * Retorna a coluna da coordenada instânciada.
     * 
     * @return retorna a coluna da coordenada instânciada.
     * @throws Exception retorna erro caso o elemento coluna do vetor esteja vazio.
     */
    public int getColuna() throws Exception {
        if (this.elementos[1] == null)
            throw new Exception("Nenhuma coluna armazenada nessa coordenada.");
        return this.elementos[1];
    }

    /**
     * Retorna uma String que mostra a linha e a coluna armazenadas na coordenada, respectivamente.
     * 
     * @return Retorna uma String que mostra a linha e a coluna armazenadas na coordenada, respectivamente.
     */
    @Override
    public String toString() {
        return "{" + this.elementos[0] + "," + this.elementos[1] + "}";
    }

    /**
     * Verifica se a coordenada modelo é igual à coordenada instânciada.
     * 
     * @param modelo coordenada modelo.
     * @return Retorna true se for igual e false se não.
     */
    @Override
    public boolean equals(Object modelo) {
        if (this == modelo) {
            return true;
        }
        if (modelo.getClass() == null) {
            return false;
        }
        if (modelo.getClass() != this.getClass()) {
            return false;
        }
        Coordenada comparada = (Coordenada) modelo;
        if (comparada.elementos[0] == this.elementos[0] && comparada.elementos[1] == this.elementos[1]) {
            return true;
        }
        return false;
    }

    /**
     * Gera o hashcode da coordenada instânciada.
     * 
     * @return Retorna o hashcode da coordenada instânciada.
     */
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

    /**
     * Cria uma cópia da coordenada instânciada.
     * 
     * @return Retorna uma coordenada igual a coordenada instânciada.
     */
    public Object clone() {
        Coordenada ret = null;
        try {
            ret = new Coordenada(this);
        } catch (Exception e) {
        }
        return ret;
    }
}
