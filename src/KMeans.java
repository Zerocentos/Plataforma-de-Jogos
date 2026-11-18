import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KMeans {

    private List<Ponto> pontos = new ArrayList<>();
    private List<Ponto> centroids = new ArrayList<>();
    private Random random = new Random();
    
    public KMeans(int k) {
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

    private class Ponto {
        private Map<NomeCaracteristica, Double> coordenada;

        public Ponto(Map<NomeCaracteristica, Double> coordenada) {
            this.coordenada = coordenada;
        }

        public void colocarValor(NomeCaracteristica caracteristica, double valor) 
            throws IllegalArgumentException {
            if(valor < 0 || valor > 1)
                throw new IllegalArgumentException();

            coordenada.put(caracteristica, valor);
        } 
    }
}
