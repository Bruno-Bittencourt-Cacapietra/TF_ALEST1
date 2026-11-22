import java.util.ArrayList;
import java.util.Locale;

/**
 * Calcula as metricas finais da simulacao a partir dos dados que a Simulacao guardou.
 * Prioridades: indice 0 = Vermelha, 1 = Amarela, 2 = Verde, 3 = Azul.
 * Filas: indice 0 = Entrada, 1 a 4 = filas Vermelha, Amarela, Verde, Azul.
 */
public class Metricas {
    
    public int chegadas;
    public int atendidos;
    public int recusados;
    public int restantes;          
    public double taxaRecusa;      
    public double vazao;           
    public double utilizacaoEnf;
    public double utilizacaoMed;
    public double[] esperaMediaPorPrioridade = new double[4];
    public int[] esperaMaxPorPrioridade = new int[4];
    public double esperaMediaGeral;
    public int esperaMaxGeral;
    public double[] tamanhoMedioFilas = new double[5];
    public int[] tamanhoMaxFilas = new int[5];

    
    public static Metricas calcular(Simulacao sim, Configuracao conf) {
        Metricas m = new Metricas();
        m.calcularTotais(sim, conf);
        m.calcularUtilizacao(sim, conf);
        m.calcularFilas(sim, conf);
        m.calcularEsperas(sim);
        return m;
    }

    private void calcularTotais(Simulacao sim, Configuracao conf) {
        chegadas = sim.getChegadas();
        atendidos = sim.getAtendidos();
        recusados = sim.getRecusados();
        restantes = chegadas - recusados - atendidos;

        if (chegadas > 0) {
            taxaRecusa = (double) recusados / chegadas;
        }

        double horas = conf.numRodadas / 60.0;   
        vazao = atendidos / horas;
    }

    
    private void calcularUtilizacao(Simulacao sim, Configuracao conf) {
        int[] enfOcupados = sim.getEnfermeirosOcupados();
        int[] medOcupados = sim.getMedicosOcupados();

        double somaEnf = 0;
        double somaMed = 0;
        for (int r = 0; r < conf.numRodadas; r++) {
            somaEnf = somaEnf + enfOcupados[r];
            somaMed = somaMed + medOcupados[r];
        }
        utilizacaoEnf = somaEnf / (conf.numRodadas * conf.numEnfermeiros);
        utilizacaoMed = somaMed / (conf.numRodadas * conf.numMedicos);
    }

    
    private void calcularFilas(Simulacao sim, Configuracao conf) {
        int[][] tamanhos = sim.getTamanhoFilas();   

        for (int f = 0; f < 5; f++) {
            double soma = 0;
            int maximo = 0;
            for (int r = 0; r < conf.numRodadas; r++) {
                soma = soma + tamanhos[r][f];
                if (tamanhos[r][f] > maximo) {
                    maximo = tamanhos[r][f];
                }
            }
            tamanhoMedioFilas[f] = soma / conf.numRodadas;
            tamanhoMaxFilas[f] = maximo;
        }
    }

    
    private void calcularEsperas(Simulacao sim) {
        ArrayList<Paciente> todos = sim.getTodos();

        double[] somaPorPrio = new double[4];
        int[] contPorPrio = new int[4];
        double somaGeral = 0;
        int contGeral = 0;

        for (Paciente p : todos) {
            int espera = p.getEspera();
            int prio = p.getPrioridade();   // 1 a 4

            if (espera >= 0 && prio >= 1 && prio <= 4) {
                int i = prio - 1;
                somaPorPrio[i] = somaPorPrio[i] + espera;
                contPorPrio[i]++;
                if (espera > esperaMaxPorPrioridade[i]) {
                    esperaMaxPorPrioridade[i] = espera;
                }

                somaGeral = somaGeral + espera;
                contGeral++;
                if (espera > esperaMaxGeral) {
                    esperaMaxGeral = espera;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            if (contPorPrio[i] > 0) {
                esperaMediaPorPrioridade[i] = somaPorPrio[i] / contPorPrio[i];
            }
        }
        if (contGeral > 0) {
            esperaMediaGeral = somaGeral / contGeral;
        }
    }

    

      public static String cabecalhoCsv() {
        return "cenario;parametro;valor;semente;chegadas;atendidos;recusados;restantes;"
             + "taxaRecusa;vazao;utilizacaoEnf;utilizacaoMed;esperaMediaGeral;esperaMaxGeral;"
             + "esperaMediaVermelha;esperaMediaAmarela;esperaMediaVerde;esperaMediaAzul;"
             + "esperaMaxVermelha;esperaMaxAmarela;esperaMaxVerde;esperaMaxAzul";
    }
    
    
    static String num(double valor, int casas) {
        String texto = String.format("%." + casas + "f", valor);   
        return texto.replace('.', ',');                          
    }

    public String toCsvRow(String cenario, String parametro, String valor, Configuracao cfg) {
        return cenario + ";" + parametro + ";" + valor + ";" + cfg.semente + ";"
            + chegadas + ";" + atendidos + ";" + recusados + ";" + restantes + ";"
            + num(taxaRecusa, 4) + ";" + num(vazao, 2) + ";"
            + num(utilizacaoEnf, 4) + ";" + num(utilizacaoMed, 4) + ";"
            + num(esperaMediaGeral, 2) + ";" + esperaMaxGeral + ";"
            + num(esperaMediaPorPrioridade[0], 2) + ";" + num(esperaMediaPorPrioridade[1], 2) + ";"
            + num(esperaMediaPorPrioridade[2], 2) + ";" + num(esperaMediaPorPrioridade[3], 2) + ";"
            + esperaMaxPorPrioridade[0] + ";" + esperaMaxPorPrioridade[1] + ";"
            + esperaMaxPorPrioridade[2] + ";" + esperaMaxPorPrioridade[3];
}
}