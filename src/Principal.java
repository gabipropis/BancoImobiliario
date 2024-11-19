import java.util.*;

public class Principal {
    public static void main(String[] args) {
        String caminhoArquivo = "src/gameConfig.txt";
        List<String> comportamentosJogadores = Arrays.asList("impulsivo", "exigente", "cauteloso", "aleatorio");
        int numPartidas = 300;

        Map<String, Integer> vitorias = new HashMap<>();
        int rodadasTotais = 0;
        int partidasTempoEsgotado = 0;

        for (String comportamento : comportamentosJogadores) {
            vitorias.put(comportamento, 0);
        }

        try {
            for (int i = 0; i < numPartidas; i++) {
                Jogo jogo = new Jogo(caminhoArquivo, comportamentosJogadores);
                String vencedor = jogo.simular();
                rodadasTotais += jogo.getRodadasTotais();

                if (vencedor.equals("tempo_esgotado")) {
                    partidasTempoEsgotado++;
                } else {
                    vitorias.put(vencedor, vitorias.get(vencedor) + 1);
                }
            }

            System.out.println("Partidas terminadas por tempo esgotado: " + partidasTempoEsgotado);
            System.out.println("Média de rodadas por partida: " + (rodadasTotais / (double) numPartidas));
            System.out.println("Porcentagem de vitórias por comportamento:");
            for (String comportamento : comportamentosJogadores) {
                System.out.printf("%s: %.2f%%\n", comportamento, (vitorias.get(comportamento) / (double) numPartidas) * 100);
            }
            String melhorComportamento = Collections.max(vitorias.entrySet(), Map.Entry.comparingByValue()).getKey();
            System.out.println("Comportamento que mais vence: " + melhorComportamento);

        } catch (Exception e) {
            System.err.println("Erro ao executar o jogo: " + e.getMessage());
        }
    }
}
