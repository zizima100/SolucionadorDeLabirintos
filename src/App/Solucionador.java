package App;
import App.EstruturasDeDados.*;

/**
 * A Classe Solucionador recebe um labirinto e possibilita solucioná-lo.
 * <p>
 * Caso o contrário, erros podem acontecer.
 * <p>
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class Solucionador {
    private Character[][] matrizLabirinto;
    private Coordenada atual;
    private int linhas, colunas;
    private Pilha<Coordenada> caminho;
    private Pilha<Pilha<Coordenada>> possibilidades;

    /**
     * Construct do objeto Solucionador. Os parametros devem ser inseridos após
     * passar por um processo de validação presente na classe ValidadorDeLabririnto.
     * 
     * @param matrizLabirinto corresponde à matriz previamente validada do
     *                        labirinto.
     * @throws Exception retorna erro quando a matrizLabirinto inserida está vazia.
     */
    public Solucionador(Character[][] matrizLabirinto) throws Exception {
        if (matrizLabirinto == null)
            throw new Exception("A matrizLabirinto está vazia.");
        this.matrizLabirinto = matrizLabirinto;
        this.linhas = this.matrizLabirinto.length;
        this.colunas = this.matrizLabirinto[0].length;
        this.atual = new Coordenada();
        this.caminho = new Pilha<Coordenada>(this.linhas * this.colunas);
        this.possibilidades = new Pilha<Pilha<Coordenada>>(this.linhas * this.colunas);
    }

    /**
     * Soluciona o labirinto.
     * 
     * @throws Exception retorna erro quando é impossível resolver o labirinto por
     *                   falta de possibilidades.
     */
    public void solucionar() throws Exception {
        posInicio();
        progressivo();
        // Bloco printa o labirinto solucionado.
        // for (int i = 0; i < 5; i++) {
        //     System.out.printf("\n\n\n\n\n");
        // }
        // System.out.printf("Labirinto Solucionado:\n");

        // printLabirinto();
    }

    private void progressivo() throws Exception {
        while (true) {
            boolean progressivo = true;

            while (progressivo) {
                Pilha<Coordenada> adjacentes = new Pilha<Coordenada>(3);

                if (verificarAdjacentes(adjacentes)) {
                    atual = (Coordenada) adjacentes.pop();

                    if (matrizLabirinto[atual.getLinha()][atual.getColuna()] == 'S')
                        return;

                    // Caminha.
                    matrizLabirinto[atual.getLinha()][atual.getColuna()] = '*';
                    caminho.push(atual);

                    possibilidades.push(adjacentes);
                } else {
                    progressivo = false;
                }
            }
            regressivo();
        }
    }

    private void regressivo() throws Exception {
        while (true) {
            try {
                atual = (Coordenada) caminho.pop();
            } catch (Exception e) {
                throw new Exception("Impossível resolver o labirinto. Acabaram as possibilidades.");
            }
            matrizLabirinto[atual.getLinha()][atual.getColuna()] = ' ';

            Pilha<Coordenada> adjacentes = new Pilha<Coordenada>(linhas * colunas);

            if (!possibilidades.isVazia()) {
                adjacentes = (Pilha<Coordenada>) possibilidades.pop();

                if (!adjacentes.isVazia()) {
                    atual = (Coordenada) adjacentes.pop();

                    matrizLabirinto[atual.getLinha()][atual.getColuna()] = '*';

                    caminho.push(atual);

                    possibilidades.push(adjacentes);

                    return;
                }
            }
        }
    }

    private Boolean verificarAdjacentes(Pilha<Coordenada> adjacentes) throws Exception {
        Integer[] a = atual.getCoordenada();
        int contAdj = 0;

        // Verifica cima.
        Coordenada cima = new Coordenada(a[0] - 1, a[1]);

        if (a[0] > 0) {
            switch (matrizLabirinto[a[0] - 1][a[1]]) {
                case 'S':
                    adjacentes.push(cima);
                    return true;
                case ' ':
                    adjacentes.push(cima);
                    contAdj++;
                    break;
                default:
                    break;
            }
        }

        // Verifica baixo.
        Coordenada baixo = new Coordenada(a[0] + 1, a[1]);

        if (a[0] < linhas - 1) {
            switch (matrizLabirinto[a[0] + 1][a[1]]) {
                case 'S':
                    adjacentes.push(baixo);
                    return true;
                case ' ':
                    adjacentes.push(baixo);
                    contAdj++;
                    break;
                default:
                    break;
            }
        }

        // Verifica esquerda.
        Coordenada esquerda = new Coordenada(a[0], a[1] - 1);

        if (a[1] > 0) {
            switch (matrizLabirinto[a[0]][a[1] - 1]) {
                case 'S':
                    adjacentes.push(esquerda);
                    return true;
                case ' ':
                    adjacentes.push(esquerda);
                    contAdj++;
                    break;
                default:
                    break;
            }
        }

        // Verifica direita.
        Coordenada direita = new Coordenada(a[0], a[1] + 1);

        if (a[1] < colunas - 1) {
            switch (matrizLabirinto[a[0]][a[1] + 1]) {
                case 'S':
                    adjacentes.push(direita);
                    return true;
                case ' ':
                    adjacentes.push(direita);
                    contAdj++;
                    break;
                default:
                    break;
            }
        }
        if (contAdj > 0)
            return true;
        return false;
    }

    private void posInicio() throws Exception {
        // Verifica a primeira e última coluna.
        for (int i = 0; i < linhas; i++) {
            switch (matrizLabirinto[i][0]) {
                case 'E':
                    atual.setCoordenada(i, 0);
                    return;
            }

            switch (matrizLabirinto[i][colunas - 1]) {
                case 'E':
                    atual.setCoordenada(i, colunas - 1);
                    return;
            }
        }

        // Verifica a primeira e última linha.
        for (int j = 0; j < colunas; j++) {
            switch (matrizLabirinto[0][j]) {
                case 'E':
                    atual.setCoordenada(0, j);
                    return;
            }

            switch (matrizLabirinto[linhas - 1][j]) {
                case 'E':
                    atual.setCoordenada(linhas - 1, j);
                    return;
            }
        }
        throw new Exception("Entrada não encontrada.");
    }

    private void printLabirinto() {
        System.out.printf("\n\nPrintando o labirinto\n");
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.printf("%c ", matrizLabirinto[i][j]);
            }
            System.out.printf("\n");
        }
    }
}
