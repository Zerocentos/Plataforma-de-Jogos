import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/** Agrupa jogadores por proximidade das suas características normalizadas. */
public class KMeans {

    private static final int MAX_ITERACOES = 300;
    private static final double TOLERANCIA = 1e-6;

    private final int k;
    private final Map<Jogador, Ponto> posicaoPorJogador;
    private final List<Ponto> centroides = new ArrayList<>();
    private final Random random = new Random();
    private Map<Jogador, Integer> grupoPorJogador = new HashMap<>();

    public KMeans(int k, Map<Jogador, Ponto> posicaoPorJogador) {
        if (k <= 0) 
            throw new IllegalArgumentException("O número de grupos (k) deve ser positivo.");

        Objects.requireNonNull(posicaoPorJogador, "O mapa de jogadores não pode ser nulo.");

        if (posicaoPorJogador.isEmpty())
            throw new IllegalArgumentException("É necessário fornecer ao menos um jogador.");

        if (k > posicaoPorJogador.size())
            throw new IllegalArgumentException("k não pode ser maior que a quantidade de jogadores.");

        this.k = k;
        this.posicaoPorJogador = new HashMap<>(posicaoPorJogador);

        validarPontos();
        inicializarCentroides();
    }

    public Map<Jogador, Integer> agrupar() {
        for (int iteracao = 0; iteracao < MAX_ITERACOES; iteracao++) {

            Map<Jogador, Integer> novasAtribuicoes = atribuirPontos();
            List<Ponto> novosCentroides = recalcularCentroides(novasAtribuicoes);
            double maiorDeslocamento = maiorDeslocamento(centroides, novosCentroides);

            grupoPorJogador = novasAtribuicoes;
            centroides.clear();
            centroides.addAll(novosCentroides);

            if (maiorDeslocamento <= TOLERANCIA)
                break;
        }

        return Map.copyOf(grupoPorJogador);
    }

    public double calcularInercia() {
        if (grupoPorJogador.isEmpty()) {
            agrupar();
        }

        double inercia = 0.0;

        for (Map.Entry<Jogador, Ponto> bucket : posicaoPorJogador.entrySet()) {
            int indice = grupoPorJogador.get(bucket.getKey());
            double distancia = distanciaParaCentroide(bucket.getValue(), centroides.get(indice));

            inercia += distancia * distancia;
        }

        return inercia;
    }

    public List<Ponto> getCentroides() {
        return List.copyOf(centroides);
    }

    private void validarPontos() {
        NomeCaracteristica[] caracteristicas = NomeCaracteristica.values();

        for (Map.Entry<Jogador, Ponto> entrada : posicaoPorJogador.entrySet()) {
            Objects.requireNonNull(entrada.getKey(), "O jogador não pode ser nulo.");
            Ponto ponto = Objects.requireNonNull(entrada.getValue(), "O ponto não pode ser nulo.");
            for (NomeCaracteristica caracteristica : caracteristicas) {
                ponto.getValor(caracteristica); // valida presença da coordenada
            }
        }
    }

    private void inicializarCentroides() {
        List<Ponto> pontos = new ArrayList<>(posicaoPorJogador.values());
        Ponto pontoAleatorio = copiarPonto(pontos.get(random.nextInt(pontos.size())));
        
        for (int i = 0; i < k; i++) {
            centroides.add(pontoAleatorio);
        }
    }

    private Map<Jogador, Integer> atribuirPontos() {
        Map<Jogador, Integer> atribuicoes = new HashMap<>();
        
        for (Map.Entry<Jogador, Ponto> entrada : posicaoPorJogador.entrySet()) {
            int melhorIndice = 0;
            double menorDistancia = distanciaParaCentroide(entrada.getValue(), centroides.get(0));

            for (int i = 1; i < centroides.size(); i++) {
                double distancia = distanciaParaCentroide(entrada.getValue(), centroides.get(i));

                if (distancia >= menorDistancia)
                    continue;

                menorDistancia = distancia;
                melhorIndice = i;
            }

            atribuicoes.put(entrada.getKey(), melhorIndice);
        }
        return atribuicoes;
    }

    private List<Ponto> recalcularCentroides(Map<Jogador, Integer> atribuicoes) {
        NomeCaracteristica[] caracteristicas = NomeCaracteristica.values();
        double[][] somas = new double[k][caracteristicas.length];
        int[] quantidades = new int[k];

        for (Map.Entry<Jogador, Ponto> entrada : posicaoPorJogador.entrySet()) {
            int grupo = atribuicoes.get(entrada.getKey());
            quantidades[grupo]++;
            for (int j = 0; j < caracteristicas.length; j++) {
                somas[grupo][j] += entrada.getValue().getValor(caracteristicas[j]);
            }
        }

        List<Ponto> resultado = new ArrayList<>();
        for (int grupo = 0; grupo < k; grupo++) {
            if (quantidades[grupo] == 0) {
                // Grupo vazio: mantém o centro anterior, evitando divisão por zero.
                resultado.add(copiarPonto(centroides.get(grupo)));
                continue;
            }
            Map<NomeCaracteristica, Double> coordenadas = new HashMap<>();
            for (int j = 0; j < caracteristicas.length; j++) {
                coordenadas.put(caracteristicas[j], somas[grupo][j] / quantidades[grupo]);
            }
            resultado.add(new Ponto(coordenadas));
        }
        return resultado;
    }

    private double maiorDeslocamento(List<Ponto> antigos, List<Ponto> novos) {
        double maior = 0.0;
        for (int i = 0; i < antigos.size(); i++) {
            maior = Math.max(maior, distanciaParaCentroide(antigos.get(i), novos.get(i)));
        }
        return maior;
    }

    private double distanciaParaCentroide(Ponto ponto, Ponto centroide) {
        double somaQuadrados = 0.0;
        for (NomeCaracteristica caracteristica : NomeCaracteristica.values()) {
            double diferenca = ponto.getValor(caracteristica) - centroide.getValor(caracteristica);
            somaQuadrados += diferenca * diferenca;
        }
        return Math.sqrt(somaQuadrados);
    }

    private Ponto copiarPonto(Ponto original) {
        return new Ponto(new HashMap<>(original.getCoordenada()));
    }
}
