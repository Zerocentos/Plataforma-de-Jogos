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

        Ponto coordenada = new Ponto(new HashMap<>()); 
        Map<Jogador, Ponto> jogadorPorPonto = new HashMap<>();

        for(Jogador jogador : jogadores) {
            for(double[] valores : valoresCaracteristicas) {
                coordenada.colocarValor(NomeCaracteristica.values()[0], valores[0]);
                coordenada.colocarValor(NomeCaracteristica.values()[1], valores[1]);
                coordenada.colocarValor(NomeCaracteristica.values()[2], valores[2]);
                coordenada.colocarValor(NomeCaracteristica.values()[3], valores[3]);

                jogadorPorPonto.put(jogador, coordenada);
            }
        }

        return jogadorPorPonto;
    }
}
