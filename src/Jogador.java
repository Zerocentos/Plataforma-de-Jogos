import java.util.Objects;

/**
 * Classe imutável que representa um jogador da plataforma.
 *
 * As quatro medidas numéricas (horasSemana, partidasSemana, gastoMensal e
 * taxaVitoria) são as "características" usadas pelos algoritmos de
 * aprendizado de máquina. O perfil é o rótulo conhecido.
 */
public final class Jogador {

  private final int id;
  private final String apelido;
  private final double horasSemana;
  private final int partidasSemana;
  private final double gastoMensal;
  private final double taxaVitoria;
  private final Perfil perfil;

  public Jogador(int id, String apelido, double horasSemana, int partidasSemana,
                 double gastoMensal, double taxaVitoria, Perfil perfil) {
    if (apelido == null || apelido.isBlank()) {
      throw new IllegalArgumentException("O apelido do jogador não pode ser vazio.");
    }
    if (perfil == null) {
      throw new IllegalArgumentException("O perfil do jogador é obrigatório.");
    }
    this.id = id;
    this.apelido = apelido;
    this.horasSemana = horasSemana;
    this.partidasSemana = partidasSemana;
    this.gastoMensal = gastoMensal;
    this.taxaVitoria = taxaVitoria;
    this.perfil = perfil;
  }

  public int getId() {
    return id;
  }

  public String getApelido() {
    return apelido;
  }

  public double getHorasSemana() {
    return horasSemana;
  }

  public int getPartidasSemana() {
    return partidasSemana;
  }

  public double getGastoMensal() {
    return gastoMensal;
  }

  public double getTaxaVitoria() {
    return taxaVitoria;
  }

  public Perfil getPerfil() {
    return perfil;
  }

  /**
    * Devolve as características numéricas do jogador, na ordem:
    * horas por semana, partidas por semana, gasto mensal, taxa de vitória.
    *
    * Cada chamada devolve um array NOVO, para que quem receber o vetor não
    * consiga alterar o jogador (a classe continua imutável).
    */
  public double[] getCaracteristicas() {
    return new double[] {horasSemana, partidasSemana, gastoMensal, taxaVitoria};
  }

  @Override
  public String toString() {
    return "Jogador{id=" + id + ", apelido='" + apelido + "', horas=" + horasSemana
            + ", partidas=" + partidasSemana + ", gasto=" + gastoMensal
            + ", vitoria=" + taxaVitoria + ", perfil=" + perfil + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Jogador)) return false;
    return id == ((Jogador) o).id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
