import java.util.Map;

public class Ponto {
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
