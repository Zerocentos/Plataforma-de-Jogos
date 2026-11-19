import java.util.HashMap;
import java.util.List;
import java.util.Map;
public final class RelatorioPtC {

    public RelatorioPtC() { }

    public static void imprimir(List<Jogador> jogadores) {
        System.out.println();
        System.out.println("Parte C - K-means");
        System.out.println("-".repeat(20));

        System.out.println("Normalizando dados...");
        converterValores(jogadores, normalizarJogadores(jogadores));
        System.out.println("Dados normalizados!");

        
    }

    private static List<double[]> normalizarJogadores(List<Jogador> jogadores) {
        Normalizador norma = Normalizador.ajustado(jogadores);
        
        return norma.transformarTodos(jogadores);
    }

    private static Map<Jogador, Ponto> converterValores(List<Jogador> jogadores, 
        List<double[]> valoresCaracteristicas) {

        Map<Jogador, Ponto> pontoPorJogador = new HashMap<>();

        for(int i = 0; i < jogadores.size(); i++) {
            Ponto coordenada = new Ponto(new HashMap<>());

            for(int j = 0; j < NomeCaracteristica.values().length; j++) {
                coordenada.colocarValor(NomeCaracteristica.values()[j], 
                    valoresCaracteristicas.get(i)[j]);
            }

            pontoPorJogador.put(jogadores.get(i), coordenada);
        }

        return pontoPorJogador;
    }
}
