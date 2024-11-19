import java.util.Random;

public class Jogador {
    private int moedas;
    private int posicao;
    private String comportamento;
    private boolean ativo;

    public Jogador(String comportamento) {
        this.moedas = 300;
        this.posicao = 0;
        this.comportamento = comportamento;
        this.ativo = true;
    }

    public int getMoedas() {
        return moedas;
    }

    public void setMoedas(int moedas) {
        this.moedas = moedas;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public String getComportamento() {
        return comportamento;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void desativar() {
        this.ativo = false;
    }

    public boolean decidirCompra(Propriedade propriedade) {
        switch (comportamento) {
            case "impulsivo":
                return true;
            case "exigente":
                return propriedade.getPrecoAluguel() > 50;
            case "cauteloso":
                return moedas - propriedade.getPrecoVenda() >= 80;
            case "aleatorio":
                return new Random().nextBoolean();
            default:
                return false;
        }
    }
}
