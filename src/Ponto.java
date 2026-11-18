import java.util.Map;

public class Ponto {
    private Map<NomeCaracteristica, Double> coordenada;

    public Ponto(Map<NomeCaracteristica, Double> coordenada) {
        this.coordenada = coordenada;
    }

    public void colocarValor(NomeCaracteristica caracteristica, double valor) 
        throws IllegalArgumentException {
        
        if(caracteristica == null) 
            throw new IllegalArgumentException("Característica não pode ser vazio");

        if(valor < 0 || valor > 1 || Double.isNaN(valor))
            throw new IllegalArgumentException("Valor não pode ter valores" +
                "fora do limite entre 0 e 1 ou vazio!");

        coordenada.put(caracteristica, valor);
    }

    public boolean temCaracteristica(NomeCaracteristica caracteristica) {
        return coordenada.containsKey(caracteristica);
    }

    public void removerValor(NomeCaracteristica caracteristica) {
        coordenada.remove(caracteristica);
    }

    public double getValor(NomeCaracteristica caracteristica) {
        assert caracteristica != null 
            : "Característica não pode ser vazio.";
            
        return coordenada.get(caracteristica);
    }

    public Map<NomeCaracteristica, Double> getCoordenada() {
        return Map.copyOf(coordenada);
    }
}
