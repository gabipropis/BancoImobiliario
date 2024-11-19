import java.io.*;
import java.util.*;

public class Jogo {
    private List<Propriedade> propriedades;
    private List<Jogador> jogadores;
    private int rodadasTotais;

    public Jogo(String caminhoArquivo, List<String> comportamentosJogadores) throws IOException {
        this.propriedades = carregarPropriedades(caminhoArquivo);
        this.jogadores = inicializarJogadores(comportamentosJogadores);
        this.rodadasTotais = 0;
    }

    private List<Propriedade> carregarPropriedades(String caminhoArquivo) throws IOException {
        List<Propriedade> propriedades = new ArrayList<>();
        BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo));
        String linha;
        while ((linha = leitor.readLine()) != null) {
            String[] partes = linha.split(" ");
            int precoVenda = Integer.parseInt(partes[0]);
            int precoAluguel = Integer.parseInt(partes[1]);
            propriedades.add(new Propriedade(precoVenda, precoAluguel));
        }
        leitor.close();
        return propriedades;
    }

    private List<Jogador> inicializarJogadores(List<String> comportamentosJogadores) {
        List<Jogador> jogadores = new ArrayList<>();
        for (String comportamento : comportamentosJogadores) {
            jogadores.add(new Jogador(comportamento));
        }
        Collections.shuffle(jogadores); // Randomiza a ordem dos jogadores
        return jogadores;
    }

    public String simular() {
        while (getJogadoresAtivos().size() > 1 && rodadasTotais < 1000) {
            for (Jogador jogador : jogadores) {
                if (!jogador.isAtivo()) continue;

                int dado = rolarDado();
                jogador.setPosicao((jogador.getPosicao() + dado) % propriedades.size());

                if (jogador.getPosicao() < dado) {
                    jogador.setMoedas(jogador.getMoedas() + 100); // Passou pelo início
                }

                Propriedade propriedade = propriedades.get(jogador.getPosicao());

                if (propriedade.getProprietario() == null && jogador.getMoedas() >= propriedade.getPrecoVenda()) {
                    if (jogador.decidirCompra(propriedade)) {
                        jogador.setMoedas(jogador.getMoedas() - propriedade.getPrecoVenda());
                        propriedade.setProprietario(jogador);
                    }
                } else if (propriedade.getProprietario() != null && propriedade.getProprietario() != jogador) {
                    int aluguel = propriedade.getPrecoAluguel();
                    if (jogador.getMoedas() < aluguel) {
                        jogador.desativar();
                        for (Propriedade p : propriedades) {
                            if (p.getProprietario() == jogador) {
                                p.setProprietario(null);
                            }
                        }
                    } else {
                        jogador.setMoedas(jogador.getMoedas() - aluguel);
                        propriedade.getProprietario().setMoedas(propriedade.getProprietario().getMoedas() + aluguel);
                    }
                }
            }
            rodadasTotais++;
        }

        return determinarVencedor();
    }

    private int rolarDado() {
        return new Random().nextInt(6) + 1;
    }

    private List<Jogador> getJogadoresAtivos() {
        List<Jogador> jogadoresAtivos = new ArrayList<>();
        for (Jogador jogador : jogadores) {
            if (jogador.isAtivo()) jogadoresAtivos.add(jogador);
        }
        return jogadoresAtivos;
    }

    private String determinarVencedor() {
        if (rodadasTotais == 1000) {
            return "tempo_esgotado";
        }
        return getJogadoresAtivos().get(0).getComportamento();
    }

    public int getRodadasTotais() {
        return rodadasTotais;
    }
}
