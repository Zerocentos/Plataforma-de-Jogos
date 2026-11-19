public enum NomeCaracteristica {
    HORAS_POR_SEMANA("Horas por semana"),
    PARTIDAS_POR_SEMANA("Partidas por semana"),
    GASTO_MENSAL("Gasto mensal"),
    TAXA_DE_VITORIA("Taxa de vitória");

    private final String frase;

    private NomeCaracteristica(String frase) {
        this.frase = frase;
    }

    public String getDescricao() {
        return this.frase;
    }
}
