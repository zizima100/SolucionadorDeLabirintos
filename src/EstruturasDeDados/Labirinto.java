package EstruturasDeDados;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Labirinto {
    int idLabirinto;
    String dataDeCriacao;
    String dataDeEdicao;
    String conteudoLabirinto;

    public Labirinto(int idLabirinto, String conteudoLabirinto) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        this.idLabirinto = idLabirinto;
        this.dataDeCriacao = sdf.format(new Date());
        this.dataDeEdicao = sdf.format(new Date());
        this.conteudoLabirinto = conteudoLabirinto;
    }

    public void updateLabirinto(String dataDeEdicao, String conteudoLabirinto) {
        this.dataDeEdicao = dataDeEdicao;
        this.conteudoLabirinto = conteudoLabirinto;
    }

    public void setID(int novoIDLabirinto) {
        this.idLabirinto = novoIDLabirinto;
    }

    public int getID() {
        return this.idLabirinto;
    }

    public String getDataCriacao() {
        return this.dataDeCriacao;
    }

    public void setDataEdicao(int novaData) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        this.dataDeEdicao = sdf.format(new Date());
    }

    public String getDataEdicao() {
        return this.dataDeEdicao;
    }

    public void setConteudoLab(String novoLabirinto) {
        this.conteudoLabirinto = novoLabirinto;
    }

    public String getConteudoLabirinto() {
        return this.conteudoLabirinto;
    }
}
