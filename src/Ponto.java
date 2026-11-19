import java.util.Map;

public class Ponto {
    private Map<NomeCaracteristica, Double> coordenada;

    public Ponto(Map<NomeCaracteristica, Double> coordenada) {
        assert coordenada != null : "As coordenadas não podem ser nulos";

        this.coordenada = coordenada;
    }

    public void colocarValor(NomeCaracteristica caracteristica, double valor) 
        throws IllegalArgumentException {
        
        if(caracteristica == null) 
            throw new IllegalArgumentException("Característica não pode ser vazio");

        if(!Double.isFinite(valor))
            throw new IllegalArgumentException("A coordenada deve ser um número finito.");

        coordenada.put(caracteristica, valor);
    }

    public boolean temCaracteristica(NomeCaracteristica caracteristica) {
        return coordenada.containsKey(caracteristica);
    }

    public void removerValor(NomeCaracteristica caracteristica) {
        coordenada.remove(caracteristica);
    }

    public double getValor(NomeCaracteristica caracteristica) {
        if(caracteristica == null || !coordenada.containsKey(caracteristica)) 
            throw new IllegalArgumentException("Característica não pode ser vazio.\n" +
                "Ou característica já deve ter valor na coordenada.");

        return coordenada.get(caracteristica);
    }

    public Map<NomeCaracteristica, Double> getCoordenada() {
        return Map.copyOf(coordenada);
    }
}
