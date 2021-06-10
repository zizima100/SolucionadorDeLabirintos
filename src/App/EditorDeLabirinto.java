package App;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import BancoDeDados.dbos.*;
import ServidorDeLabirintos.Compartilhado.*;

/**
 * UI do projeto.
 * @author Julio Faundes
 * @author Diego Barbosa
 * @author Vinicius Zacheu 
 * @since 2021
 */
public class EditorDeLabirinto {
    //Interface
    JFrame janela = new JFrame("Editor de Labirintos");
    JButton botao[] = new JButton[5]; // criamos 5 botoes
    JTextArea visorErros = new JTextArea(); // text area para visor de error
    JTextArea editorDeTexto = new JTextArea(); // proprio editor de texto
    Labirinto labirintoFinal = null;
    
    /**
     * metódo responsável em criar a janela, deixá-la centralizada, criar os botões, determinar suas caracteristicas estéticas 
     * e propriedades funcionais.
     */
    public EditorDeLabirinto(Labirinto labirintoImportado) {
        JPanel botoes = new JPanel(); // jpanel recebe varios componentes layoutManager
        botoes.setLayout(new GridLayout(1, 5)); // criamos os icones de botao 
        String textosBotoes[] = { "Novo", "Importar", "Validar", "Solucionar", "Salvar E Sair" }; 
        
        TratadorDeMouse tratadorDeMouse = new TratadorDeMouse();
        KeyboardListener keyboardListener = new KeyboardListener();

        for (int i = 0; i < this.botao.length; i++) {
            this.botao[i] = new JButton(textosBotoes[i]); // instanciamos os botoes com os determinados nomes 
            this.botao[i].setActionCommand(textosBotoes[i]); 
            this.botao[i].addActionListener(tratadorDeMouse); 
            botoes.add(this.botao[i]); // adicionamos o botao com determinado nome 
        }
        
        // Propriedades estéticas da janela.
        this.janela.setSize(750, 900);
        this.janela.getContentPane().setLayout(new BorderLayout());
        this.janela.add(botoes, BorderLayout.NORTH);
        this.janela.add(this.editorDeTexto, BorderLayout.CENTER);
        this.janela.add(this.visorErros, BorderLayout.SOUTH);
        this.janela.setVisible(true);
        editorDeTexto.setFont(new Font("Monospaced", Font.PLAIN, 16));
        visorErros.setFont(new Font("Monospaced", Font.PLAIN, 20));
        visorErros.setForeground(Color.RED);
        
        // Faz a janela iniciar centralizada.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - janela.getWidth()) / 2;
        int y = (screenSize.height - janela.getHeight()) / 2;
        this.janela.setLocation(x, y);
        
        // Propriedades funcionais da janela.
        this.janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        visorErros.setEditable(false); 
        
        //Faz botao salvar ficar disabled.
        editorDeTexto.addKeyListener(keyboardListener);
        botao[4].setEnabled(false);

        // Importa infos cliente.
        this.labirintoFinal = labirintoImportado;
        String conteudoImportado = labirintoImportado.getConteudo();
        editorDeTexto.setText(conteudoImportado.substring(conteudoImportado.indexOf('\n') + 1, conteudoImportado.length()));
    }

    public Labirinto getLabirinto() {
        return this.labirintoFinal;
    }

    class KeyboardListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            botao[4].setEnabled(false);
        }
        @Override
        public void keyPressed(KeyEvent e) {
        }
        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private class TratadorDeMouse implements ActionListener {

        private void botaoNovo() {
            // Reseta o editor e visor de erros.
            visorErros.setText("");
            visorErros.setForeground(Color.RED);
            editorDeTexto.setText("");
            botao[4].setEnabled(false);
        }

        private void botaoImportar() throws Exception {
            // Reseta o visor de erros.
            visorErros.setText("");
            visorErros.setForeground(Color.RED);
            botao[4].setEnabled(false);

            final JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                String path = selectedFile.getAbsolutePath();

                // Verifica se o arquivo é um txt
                if (!path.substring(path.length() - 3).equals("txt")) {
                    JOptionPane.showMessageDialog(null, "O arquivo não é um .txt");
                    return;
                }

                // Copiar o conteúdo do txt para o editor de texto.
                int i = 0;
                String linha;
                editorDeTexto.setText("");
                
                BufferedReader in = null;
                in = new BufferedReader(new FileReader(path)); // Path é o caminho do arquivo selecionado 
                while ((linha = in.readLine()) != null) {
                    i++;
                    if (i == 1)
                        continue;
                    String antes = editorDeTexto.getText();
                    editorDeTexto.setText(antes + linha + "\n"); // Comemca colocando a segunda linha, depois da primeira que tinha sido lida no "in" e depois coloca o \n
                }
                editorDeTexto.setText(editorDeTexto.getText().substring(0, editorDeTexto.getText().length() - 1)); // Remove último \n
                in.close();
            }
        }

        private void botaoValidar() {
            // Reseta visor e editor.
            visorErros.setText("");
            visorErros.setForeground(Color.RED);
            removerAsteriscos();

            String labirintoEditor = editorDeTexto.getText();
            int qntLinhas = contLinhas();

            try {
                ValidacaoLabirinto validador = new ValidacaoLabirinto();
                validador.validar(labirintoEditor, qntLinhas);
                botao[4].setEnabled(true); // Ativa botão salvar.
                visorErros.setForeground(Color.GREEN);
                visorErros.setText("Nenhum erro encontrado!");
                JOptionPane.showMessageDialog(null, "Validadado com Sucesso!"); // PopUp
            } catch (Exception e) {
                visorErros.setText(new ValidacaoInterface().validar(editorDeTexto.getText(), contLinhas())); // imprimimos no visor de erros o atributo erros, que é uma string que contem todos os erros.
                JOptionPane.showMessageDialog(null, "Erro ao validar labirinto. . .");
            }
        }

        private void botaoSolucionar() {
            // Reseta visor e editor.
            visorErros.setText("");
            visorErros.setForeground(Color.RED);
            removerAsteriscos();

            String labirintoEditor = editorDeTexto.getText();
            int qntLinhas = contLinhas();

            // Tenta validar e depois solucionar labirinto.
            try {
                // Validação.
                ValidacaoLabirinto validador = new ValidacaoLabirinto();
                validador.validar(labirintoEditor, qntLinhas);
                
                // Recebe a matriz de caracteres do labirinto.
                Character[][] matrizLabirinto = new Character[validador.getLinhas()][validador.getColunas()];
                validador.cloneMatriz(matrizLabirinto);

                // Soluciona labirinto a partir da matriz recebida.
                Solucionador solucionador = new Solucionador(matrizLabirinto);
                solucionador.solucionar();

                labirintoEditor = "";
                for (int i = 0; i < matrizLabirinto.length; i++) {
                    String linha = "";
                    for (int j = 0; j < matrizLabirinto[0].length; j++) {
                        linha += matrizLabirinto[i][j];
                    }
                    labirintoEditor += linha + "\n";
                }
                labirintoEditor = labirintoEditor.substring(0, labirintoEditor.length() - 1);
                editorDeTexto.setText(labirintoEditor);
                botao[4].setEnabled(true);
                visorErros.setForeground(Color.GREEN);
                visorErros.setText("Nenhum erro encontrado!");
                JOptionPane.showMessageDialog(null, "Labirinto Solucionado!");
            } catch (Exception e) {
                visorErros.setText(new ValidacaoInterface().validar(editorDeTexto.getText(), contLinhas()));
                JOptionPane.showMessageDialog(null, "Erro ao solucionar labirinto. . .");
            }
        }

        private void botaoSalvar() {
            visorErros.setText("");
            visorErros.setForeground(Color.RED);
            removerAsteriscos();

            int qntLinhas = contLinhas();
            String conteudoLabirinto = qntLinhas + "\n" + editorDeTexto.getText();
            
            try {
                labirintoFinal.setConteudo(conteudoLabirinto);
                labirintoFinal.setDataEdicao(new Date(System.currentTimeMillis()));
                janela.dispose();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        private int contLinhas() {
            String labirintoEditor = editorDeTexto.getText();
            int iLinhas = 1;
            for (int i = 0; i < labirintoEditor.length(); i++) {
                if (labirintoEditor.charAt(i) == '\n')
                    iLinhas++;
            }
            return iLinhas;
        }

        private void removerAsteriscos() {
            char[] labirinto = editorDeTexto.getText().toCharArray();
            for (int i = 0; i < labirinto.length; i++) {
                if (labirinto[i] == '*')
                    labirinto[i] = ' ';
            }
            editorDeTexto.setText(new String(labirinto));
        }

        /**
         * Responsável por tomar alguma decisão, caso algum dos botões seja clicado
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();
            try {
                switch (comando) {
                case "Novo": botaoNovo();
                    break;
                case "Importar": botaoImportar();
                    break;
                case "Validar": botaoValidar();
                    break;
                case "Solucionar": botaoSolucionar();
                    break;
                case "Salvar E Sair": botaoSalvar();
                    break;
                }
            } catch (Exception erro) {
                System.err.println(erro.getMessage());
            }
        }
    }
}
