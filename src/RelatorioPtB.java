// Exibe o resumo da divisão e da normalização da Parte B.
public final class RelatorioPtB {

  private static final String[] NOMES_CARACTERISTICAS = {
    "Horas por semana",
    "Partidas por semana",
    "Gasto mensal",
    "Taxa de vitória"
  };

  private RelatorioPtB() {}

  public static void imprimir(DadosPreparadosPtB dados) {
    assert dados != null;

    System.out.println();
    System.out.println("Parte B - preparação dos dados");
    System.out.println("Semente do embaralhamento: "
        + DivisaoTreinoTeste.SEMENTE_PADRAO);
    System.out.println("Jogadores no treino: "
        + dados.getDivisao().getTreino().size());
    System.out.println("Jogadores no teste: "
        + dados.getDivisao().getTeste().size());
    System.out.println("Limites usados pela normalização min-max (calculados no treino):");

    double[] minimos = dados.getNormalizador().minimos();
    double[] maximos = dados.getNormalizador().maximos();
    for (int i = 0; i < NOMES_CARACTERISTICAS.length; i++) {
      System.out.printf("  %-22s min = %.2f, max = %.2f%n",
          NOMES_CARACTERISTICAS[i], minimos[i], maximos[i]);
    }
  }
}
