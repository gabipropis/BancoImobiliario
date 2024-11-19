public class Propriedade {
    private int precoVenda;
    private int precoAluguel;
    private Jogador proprietario;

    public Propriedade(int precoVenda, int precoAluguel) {
        this.precoVenda = precoVenda;
        this.precoAluguel = precoAluguel;
        this.proprietario = null;
    }

    public int getPrecoVenda() {
        return precoVenda;
    }

    public int getPrecoAluguel() {
        return precoAluguel;
    }

    public Jogador getProprietario() {
        return proprietario;
    }

    public void setProprietario(Jogador proprietario) {
        this.proprietario = proprietario;
    }
}
