import java.util.List;
public final class RelatorioPtC {

    private static List<double[]> caracteristicasNormalizadas;

    public RelatorioPtC() { }

    public static void imprimir(List<Jogador> jogadores) {
        System.out.println();
        System.out.println("Parte C - K-means");
        System.out.println("-".repeat(20));

        System.out.println("Normalizando dados...");
        caracteristicasNormalizadas = normalizarJogadores(jogadores);
        System.out.println("Dados normalizados!");
    }

    private static List<double[]> normalizarJogadores(List<Jogador> jogadores) {
        Normalizador norma = Normalizador.ajustado(jogadores);
        
        return norma.transformarTodos(jogadores);
    }

    public static List<double[]> getCaracteristicasNormalizadas() {
        return caracteristicasNormalizadas;
    }
}
