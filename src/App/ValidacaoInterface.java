package App;


/**
 * Classe que valida o labirinto aberto no editor de texto    
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class ValidacaoInterface {
    private Integer linhas;
    private Integer colunas;
    private Character[][] matrizLabirinto;
    String erros;

    /**
     * Transforma string em labirinto e valida a consistencia de colunas, nas linhas, quantidade de entradas, 
     * saidas e se há caracteres estranhos dentro do labirinto.
     * @param labirinto corresponde a uma string que contem o labirinto
     * @param qntLinhas corresponde a quantidade de linhas do labirinto
     * @return
     */
    public String validar(String labirinto, int qntLinhas) {
        erros = "";
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
                    erros += "Quantidade de colunas divergente. . .\n";
                    return erros += "O ERRO ACIMA IMPEDE A VALIDAÇÃO DO RESTO DO LABIRINTO";
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

        return this.erros.substring(0, erros.length() - 1);
    }

    void validarCaracteres() {
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
                    this.erros += "Caracter inválido detectado na matrizLabrinto.\n";
                }
            }
        }

        if (contEntrada > 1 || contSaida > 1)
            this.erros += "Existem entradas e/ou saídas no meio do labirinto!\n";
    }

    void validarBordas() {
        int contE = 0;
        int contS = 0;

        // Verifica as bordas laterias esquerda e direita da matrizLabirinto.
        for (int i = 0; i < linhas; i++) {
            switch (matrizLabirinto[i][0]) {
            case 'E':
                contE++;
                if (contE >= 2)
                    this.erros += "Há mais de uma entrada.\n";
                break;
            case 'S':
                contS++;
                if (contS >= 2)
                    this.erros += "Há mais de uma saída.\n";
                break;
            case '#':
                break;
            case ' ':
                this.erros += "A parede está furada.\n";
                break;
            default:
                this.erros += "Caracter inválido detectado na parede.\n";
                break;
            }
            switch (matrizLabirinto[i][colunas - 1]) {
            case 'E':
                contE++;
                if (contE >= 2)
                    this.erros += "Há mais de uma entrada.\n";
                break;
            case 'S':
                contS++;
                if (contS >= 2)
                    this.erros += "Há mais de uma saída.\n";
                break;
            case '#':
                break;
            case ' ':
                this.erros += "A parede está furada.\n";
                break;
            default:
                this.erros += "Caracter inválido detectado na parede.\n";
                break;
            }
        }

        // Verifica as linhas laterias cima e baixo da matrizLabirinto.
        for (int j = 0; j < colunas; j++) {
            switch (matrizLabirinto[0][j]) {
            case 'E':
                contE++;
                if (contE >= 2)
                    this.erros += "Há mais de uma entrada.\n";
                break;
            case 'S':
                contS++;
                if (contS >= 2)
                    this.erros += "Há mais de uma saída.\n";
                break;
            case '#':
                break;
            case ' ':
                this.erros += "A parede está furada.\n";
            default:
                this.erros += "Caracter inválido detectado na parede.\n";
            }
            switch (matrizLabirinto[linhas - 1][j]) {
            case 'E':
                contE++;
                if (contE >= 2)
                    this.erros += "Há mais de uma entrada.\n";
                break;
            case 'S':
                contS++;
                if (contS >= 2)
                    this.erros += "Há mais de uma saída.\n";
                break;
            case '#':
                break;
            case ' ':
                this.erros += "A parede está furada.\n";
            default:
                this.erros += "Caracter inválido detectado na parede.\n";
            }
        }

        if (contE < 1)
            this.erros += "Não existe a entrada do labirinto\n";
        if (contS < 1)
            this.erros += "Não existe a saída do labirinto\n";
    }
}
