public enum NomeCaracteristica {
    HORAS_POR_SEMANA("Horas por semana"),
    PARTIDAS_POR_SEMANA("Partidas por semana"),
    GASTO_MENSAL("Gasto mensal"),
    TAXA_DE_VITORIA("Taxa de vitória");

    String frase;

    NomeCaracteristica(String frase) {
        this.frase = frase;
    }

    @Override
    public String toString() {
        return this.frase;
    }
}
