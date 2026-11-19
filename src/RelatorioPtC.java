import java.util.HashMap;
import java.util.List;
import java.util.Map;
public final class RelatorioPtC {

    public RelatorioPtC() { }

    public static void imprimir(List<Jogador> jogadores) {
        System.out.println();
        System.out.println("Parte C - K-means");
        System.out.println("-".repeat(20));

        List<double[]> caracteristicas = normalizarJogadores(jogadores);

        System.out.println("\nDados normalizados!");
        System.out.println("Os 5 primeiros jogadores: ");
        
        for(int i = 0; i < 5; i++) {
            System.out.println("Jogador " + i + ": ");

            for(int j = 0; j < NomeCaracteristica.values().length; j++) {
                System.out.println(
                    NomeCaracteristica.values()[j].getDescricao() + ": " 
                    + caracteristicas.get(i)[j]);
            }

            System.out.println();
        }
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
