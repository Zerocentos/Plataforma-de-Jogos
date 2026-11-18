import java.util.Map;

public class Ponto {
    private Map<NomeCaracteristica, Double> coordenada;

    public Ponto(Map<NomeCaracteristica, Double> coordenada) {
        this.coordenada = coordenada;
    }

    public double colocarValor(NomeCaracteristica caracteristica, double valor) 
        throws IllegalArgumentException {
        if(valor < 0 || valor > 1)
            throw new IllegalArgumentException();

        return coordenada.put(caracteristica, valor);
    }

    public boolean temCaracteristica(NomeCaracteristica caracteristica) {
        return coordenada.containsKey(caracteristica);
    }

    public double removerValor(NomeCaracteristica caracteristica) {
        return coordenada.remove(caracteristica);
    }

    public double getValor(NomeCaracteristica caracteristica) {
        return coordenada.get(caracteristica);
    }

    public Map<NomeCaracteristica, Double> getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Map<NomeCaracteristica, Double> coordenada) {
        this.coordenada = coordenada;
    }
}
