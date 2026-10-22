import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int opcao = lerOpcao(scanner);
    if (opcao == 4) {
      System.out.println("Encerrando."); return;
    }

    try {
      executarOpcao(opcao);
    } catch(Exception e) {
      System.out.println("Não foi possível rodar o programa: " + e.getMessage());
    }

    scanner.close();
  }

  private static int lerOpcao(Scanner scanner) {
    System.out.println("Qual parte você deseja rodar? [Digite o número correspondente]");
    System.out.println("1 - Pt A");
    System.out.println("2 - Pt B");
    System.out.println("3 - Rodar todos em sequência");
    System.out.println("4 - Nenhum (sair)");

    while (true) {
      System.out.print("> ");

      String resposta = scanner.nextLine().trim();
      try {
        int opcao = Integer.parseInt(resposta);
        if (opcao >= 1 && opcao <= 4) {
          return opcao;
        }
      } catch (Exception ignorada) { }

      System.out.println("Opção inválida. Digite um número de 1 a 4.");
      System.out.println();
    }
  }

  private static void executarOpcao(int opcao) throws Exception {
    try (Connection conexao = Conexao.abrir()) {
      JogadorDAO jogadorDAO = new JogadorDAOJdbc(conexao);
      List<Jogador> jogadores = jogadorDAO.listarTodos();

      switch (opcao) {
        case 1:
          executarParteA(conexao, jogadores);
          break;
        case 2:
          executarParteB(jogadores);
          break;
        case 3:
          executarParteA(conexao, jogadores);
          executarParteB(jogadores);
          break;
        default:
          assert false : "Opção inválida para executar";
      }
    }
  }

  private static void executarParteA(Connection conexao, List<Jogador> jogadores)
      throws Exception {
    RelatorioPtA.imprimir(conexao, jogadores);
  }

  private static void executarParteB(List<Jogador> jogadores) {
    DadosPreparadosPtB dadosPtB = new DadosPreparadosPtB(jogadores);
    RelatorioPtB.imprimir(dadosPtB);
  }
}
