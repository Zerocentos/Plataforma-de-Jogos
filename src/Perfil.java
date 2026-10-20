/**
 * Perfil de um jogador na plataforma.
 *
 * É o "rótulo" (a resposta correta) que o KNN aprende a prever e que usamos
 * para conferir se os grupos encontrados pelo K-Means fazem sentido.
 */
public enum Perfil {
  CASUAL("Joga de vez em quando, por diversão"),
  REGULAR("Joga com frequência, mas sem grande dedicação"),
  RANQUEADO("Joga partidas ranqueadas e treina quase todo dia");

  private final String descricao;

  Perfil(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }
}
