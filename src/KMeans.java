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
        double distancia = 0;
        double diferenca;

        for(int i = 0; i < nomes.length; i++) {
            diferenca = Math.abs(p.getValor(nomes[i])
                           - c.getValor(nomes[i]));

            distancia += Math.pow(diferenca, 2);
        }

        return Math.sqrt(distancia);
    }

    private Ponto centroidMaisProximo(Ponto p) {
        Ponto centroidProximo = null;
        double distancia = -1; // A distância não pode ser maior que 2;

        for(Ponto c : centroids) {
            if(centroidProximo != null &&
                distancia != -1 &&
                distancia >= distanciaParaCentroid(p, c))
                continue;

            distancia = distanciaParaCentroid(p, c);
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
