package App;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Classe utilizada para validar um labirinto
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class ValidacaoLabirinto {
    private Integer linhas;
    private Integer colunas;
    private Character[][] matrizLabirinto;

    /**
     * Valida a consistência do labirinto 
     * @param labirinto
     * @param qntLinhas
     * @throws Exception
     */
    public void validar(String labirinto, int qntLinhas) throws Exception {
        linhas = qntLinhas;

        // Conta a quantidade de colunas da primeira linha.
        int contColunasPriLinha = 0;
        for (int i = 0; labirinto.charAt(i) != '\n'; i++) {
            contColunasPriLinha++;
        }
        colunas = contColunasPriLinha;

        int contColunasPorLinha = 0;
        for (int i = 0; i < labirinto.length(); i++) {
            if (labirinto.charAt(i) == '\n') {
                if (contColunasPorLinha == contColunasPriLinha) {
                    contColunasPorLinha = 0;
                    continue;
                } else {
                    throw new Exception("Quantidade de colunas divergente. . .");
                }
            }
            contColunasPorLinha++;
        }

        // Redimenciona a matriz antiga e transforma as linhas do documento numa matriz.
        Character[][] novaMatriz = new Character[linhas][colunas];
        matrizLabirinto = novaMatriz;

        // Transforma a string em uma matriz.
        int linha = 0;
        int coluna = 0;
        for (int i = 0; i <= labirinto.length() - 1; i++) {
            char caracter;
            if ((caracter = labirinto.charAt(i)) == '\n') {
                linha++;
                coluna = 0;
                continue;
            }
            matrizLabirinto[linha][coluna] = caracter;
            coluna++;
        }

        validarBordas();

        validarCaracteres();
    }

    /**
     * Valida se o arquivo é consistente, ou seja: apresenta a quantidade de linhas, na sua primeira linha
     * @param nomeArquivo nome do arquivo 
     * @throws Exception
     */
    public void validarArquivo(String nomeArquivo) throws Exception {
        int contador = 0; // Aponta que linha está sendo lida (Linha começa por 1)

        // Recebe e abre arquivo com base no parâmetro recebido.
        BufferedReader in = null;
        in = new BufferedReader(new FileReader("C:\\" + nomeArquivo + ".txt"));

        // Este bloco valida se o labirinto apresenta o número de linhas e se a
        // quantidade de colunas é consistente.
        String str;
        while ((str = in.readLine()) != null) {
            contador++;
            if (contador == 1) { // Verifica e realiza a leitura da primeira linha.
                if (isPrimeiroValorInteiro(str)) {
                    linhas = Integer.parseInt(str);
                } else {
                    in.close();
                    throw new Exception("\n\nA primeira linha não contém um número inteiro.");
                }
            } else { // Verifica se todas as linhas após a primeira tem a mesma quantidade de
                     // colunas.
                if (!qntColunasLab(contador, str)) {
                    in.close();
                    throw new Exception("\n\nO número de colunas não bate.");
                }
            }
        }
        in.close();

        // Redimenciona a matriz antiga e transforma as linhas do documento numa matriz.
        Character[][] novaMatriz = new Character[linhas][colunas];
        matrizLabirinto = novaMatriz;
        arquivoParaMatriz(nomeArquivo);

        validarBordas();

        validarCaracteres();
    }

    private boolean isPrimeiroValorInteiro(String linha) {
        try {
            Integer.parseInt(linha);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean qntColunasLab(Integer contador, String linha) {
        if (contador == 2) {
            colunas = linha.length();
            return true;
        } else {
            if (colunas == linha.length()) {
                colunas = linha.length();
                return true;
            } else {
                return false;
            }
        }
    }

    private void arquivoParaMatriz(String nomeArquivo) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("C:\\" + nomeArquivo + ".txt"));
        String linha;

        in.readLine();

        for (int i = 0; i < linhas; i++) {
            linha = in.readLine();
            for (int j = 0; j < colunas; j++) {
                matrizLabirinto[i][j] = linha.charAt(j);
            }
        }
        in.close();
    }

    private void validarBordas() throws Exception {
        int contE = 0;
        int contS = 0;

        // Verifica as bordas laterias esquerda e direita da matrizLabirinto.
        for (int i = 0; i < linhas; i++) {
            switch (matrizLabirinto[i][0]) {
            case 'E':
                contE++;
                if (contE >= 2)
                    throw new Exception("Há mais de uma entrada.");
                break;
            case 'S':
                contS++;
                if (contS >= 2)
                    throw new Exception("Há mais de uma saída.");
                break;
            case '#':
                break;
            case ' ':
                throw new Exception("A parede está furada.");
            default:
                throw new Exception("Caracter inválido detectado na parede.");
            }
            switch (matrizLabirinto[i][colunas - 1]) {
            case 'E':
                contE++;
                if (contE >= 2)
                    throw new Exception("Há mais de uma entrada.");
                break;
            case 'S':
                contS++;
                if (contS >= 2)
                    throw new Exception("Há mais de uma saída.");
                break;
            case '#':
                break;
            case ' ':
                throw new Exception("A parede está furada.");
            default:
                throw new Exception("Caracter inválido detectado na parede.");
            }
        }

        // Verifica as linhas laterias cima e baixo da matrizLabirinto.
        for (int j = 0; j < colunas; j++) {
            switch (matrizLabirinto[0][j]) {
            case 'E':
                contE++;
                if (contE >= 2)
                    throw new Exception("Há mais de uma entrada.");
                break;
            case 'S':
                contS++;
                if (contS >= 2)
                    throw new Exception("Há mais de uma saída.");
                break;
            case '#':
                break;
            case ' ':
                throw new Exception("A parede está furada.");
            default:
                throw new Exception("Caracter inválido detectado na parede.");
            }
            switch (matrizLabirinto[linhas - 1][j]) {
            case 'E':
                contE++;
                if (contE >= 2)
                    throw new Exception("Há mais de uma entrada.");
                break;
            case 'S':
                contS++;
                if (contS >= 2)
                    throw new Exception("Há mais de uma saída.");
                break;
            case '#':
                break;
            case ' ':
                throw new Exception("A parede está furada.");
            default:
                throw new Exception("Caracter inválido detectado na parede.");
            }
        }

        if (contE < 1)
            throw new Exception("Não existe a entrada do labirinto");
        if (contS < 1)
            throw new Exception("Não existe a saída do labirinto");
    }

    private void validarCaracteres() throws Exception {
        int contSaida = 0;
        int contEntrada = 0;

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                switch (matrizLabirinto[i][j]) {
                case 'E':
                    contEntrada++;
                    break;
                case 'S':
                    contSaida++;
                    break;
                case '#':
                    break;
                case ' ':
                    break;
                default:
                    throw new Exception("Caracter inválido detectado na matrizLabrinto.");
                }
            }
        }

        if (contEntrada > 1 || contSaida > 1)
            throw new Exception("Existem entradas e/ou saídas no meio do labirinto!");
    }

    /**
     * Imprime a matriz de caracteres correspondente ao labirinto armazenada no
     * validador.
     */

    public void printMatriz() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.printf("%c ", matrizLabirinto[i][j]);
            }
            System.out.printf("\n");
        }
    }

    /**
     * Recebe uma matriz e copia todos os elementos da matrizLabririnto processada
     * durante a validação para ela.
     * 
     * @param matrizInput matriz externa que deve ter o mesmo tamanho que a
     *                    matrizLabirinto.
     */

    public void cloneMatriz(Character[][] matrizInput) {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matrizInput[i][j] = matrizLabirinto[i][j];
            }
        }
    }

    /**
     * Envia o número de linhas da matrizLabririnto.
     * 
     * @return retorna o número de linhas na matrizLabirinto.
     */

    public int getLinhas() {
        return linhas;
    }

    /**
     * Envia o número de colunas da matrizLabririnto.
     * 
     * @return retorna o número de colunas na matrizLabirinto.
     */

    public int getColunas() {
        return colunas;
    }
}