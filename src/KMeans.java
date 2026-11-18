import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KMeans {

    private Map<Jogador, Ponto> posicaoPorJogador;
    private List<Ponto> centroids = new ArrayList<>();
    private Random random = new Random();

    public KMeans(int k) {
        this(k, new HashMap<>());
    }
    
    public KMeans(int k, Map<Jogador, Ponto> posicaoPorJogador) {
        this.posicaoPorJogador = posicaoPorJogador;

        for(int i = 0; i < k; i++) {
            centroids.add(adicionarCentroid());
        }
    }

    private Ponto adicionarCentroid() {
        Ponto p = new Ponto(new HashMap<>());
        double value;

        for(NomeCaracteristica caracteristica :
            NomeCaracteristica.values()) {
                value = random.nextDouble();

                p.colocarValor(caracteristica, value);
            }

        return p;
    }

    private double distanciaParaCentroid(Ponto p, Ponto c) {
        NomeCaracteristica[] nomes = NomeCaracteristica.values();
        double valor;
        double distancia = Math.abs(p.getValor(nomes[0])
                           - c.getValor(nomes[0]));

        for(int i=1; i < nomes.length; i++) {
                valor = Math.abs(p.getValor(nomes[i]) 
                        - c.getValor(nomes[i]));

                distancia = Math.sqrt(distancia + valor);
        }

        return distancia;
    }

    private Ponto centroidMaisProximo(Ponto p) {
        Ponto centroidProximo = null;
        double distanciaCentroid = 2; // A distância não pode ser maior que 2;

        for(Ponto c : centroids) {
            if(centroidProximo == null || 
                distanciaCentroid <= distanciaParaCentroid(p, c)) continue;

            distanciaCentroid = distanciaParaCentroid(p, c);
            centroidProximo = c;
        }

        return centroidProximo;
    }

    private Map<Ponto, Ponto> agrupamentoPorCentroid() {
        Map<Ponto, Ponto> pontoPorCentroid = new HashMap<>();
        Ponto centroid;

        for(Ponto p : posicaoPorJogador.values()) {
            centroid = centroidMaisProximo(p);

            pontoPorCentroid.put(p, centroid);
        }

        return pontoPorCentroid;
    }
}
