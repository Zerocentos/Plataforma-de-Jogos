import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KMeans {

    private Map<Jogador, Ponto> posicaoPorJogador;
    private List<Ponto> centroids = new ArrayList<>();
    private Random random = new Random();

    public KMeans(int k) {
        this(k, null);
    }
    
    public KMeans(int k, Map<Jogador, Ponto> posicaoPorJogador) {
        this.posicaoPorJogador = posicaoPorJogador;

        for(int i = 0; i < k; i++) {
            centroids.add(adicionarCentroid());
        }
    }

    private Ponto adicionarCentroid() {
        Ponto p = new Ponto(null);
        double value;

        for(NomeCaracteristica caracteristica :
            NomeCaracteristica.values()) {
                value = random.nextDouble();

                p.colocarValor(caracteristica, value);
            }

        return p;
    }

    private Ponto distanciaParaCentroid(Ponto p) {
        double valor;

        for(NomeCaracteristica caracteristica
            : NomeCaracteristica.values()) {
            valor = p.
        }
    }
}
