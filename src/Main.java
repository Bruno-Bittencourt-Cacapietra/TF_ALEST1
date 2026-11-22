/*public class App {
    public static void main(String[] args){
        Configuracao config = new Configuracao();
        config.numRodadas = 30;
        Simulacao simul = new Simulacao(config, true);
        simul.executar();
        System.out.println("|Chegadas: " + simul.getChegadas());
        System.out.println("|Atendidos: " + simul.getAtendidos());
        System.out.println("|Recusados: " + simul.getRecusados());

    }
}*/
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.File;


public class Main {
    static final long[] SEMENTES = {42, 43, 44, 45, 46};
    static PrintWriter csv;

    public static void main(String[] args) throws IOException {
            
        if (args.length > 0 && args[0].equals("demo")) {
            rodarDemonstracao();
            return;
        }
        new File("resultados").mkdirs();
        csv = new PrintWriter("resultados/resultados.csv", "UTF-8");
        csv.println(Metricas.cabecalhoCsv());

        // Numero de medicos
        for (int medicos : new int[]{3, 5, 7}) {
            Configuracao c = new Configuracao();
            c.numMedicos = medicos;
            rodarComSementes("numMedicos", String.valueOf(medicos), c);
        }

        // Probabilidade de chegada
        for (double prob : new double[]{0.10, 0.20, 0.30, 0.40, 0.50, 0.60, 0.80, 1.00}) {
            Configuracao c = new Configuracao();
            c.probChegada = prob;
            rodarComSementes("probChegada", Metricas.num(prob, 2), c);
        }

        
        for (int cap : new int[]{5, 10, 30, 60}) {
            Configuracao c = new Configuracao();
            c.capFilaEntrada = cap;
            rodarComSementes("capFilaEntrada", String.valueOf(cap), c);
        }

       
        for (int enf : new int[]{1, 2, 3}) {
            Configuracao c = new Configuracao();
            c.numEnfermeiros = enf;
            rodarComSementes("numEnfermeiros", String.valueOf(enf), c);
        }

       
        for (int dur : new int[]{1, 3, 5, 7}) {
            Configuracao c = new Configuracao();
            c.duracaoTriagem = dur;
            rodarComSementes("duracaoTriagem", String.valueOf(dur), c);
        }

        csv.close();
        salvarSerieDeReferencia();
        System.out.println("Pronto! Arquivos gerados na pasta resultados.");
    }

  
    static void rodarComSementes(String parametro, String valor, Configuracao c) {
        c.validar();
        for (long semente : SEMENTES) {
            c.semente = semente;
            Simulacao sim = rodar(c);
            Metricas m = Metricas.calcular(sim, c);
            csv.println(m.toCsvRow(parametro + "=" + valor, parametro, valor, c));
        }
    }

    
    static Simulacao rodar(Configuracao c) {
        PrintStream telaNormal = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
        Simulacao sim = new Simulacao(c, false);
        sim.executar();
        System.setOut(telaNormal);
        return sim;
    }

   
    static void salvarSerieDeReferencia() throws IOException {
        Configuracao c = new Configuracao();
        Simulacao sim = rodar(c);
        int[][] filas = sim.getTamanhoFilas();
        int[] enf = sim.getEnfermeirosOcupados();
        int[] med = sim.getMedicosOcupados();
        int[] rec = sim.getRecusadosAcum();

        PrintWriter out = new PrintWriter("resultados/serie_rodadas.csv", "UTF-8");
        out.println("rodada;filaEntrada;filaVermelha;filaAmarela;filaVerde;filaAzul;enfermeirosOcupados;medicosOcupados;recusadosAcum");
        for (int r = 0; r < c.numRodadas; r++) {
            out.println((r + 1) + ";" + filas[r][0] + ";" + filas[r][1] + ";" + filas[r][2] + ";"
                      + filas[r][3] + ";" + filas[r][4] + ";" + enf[r] + ";" + med[r] + ";" + rec[r]);
        }
        out.close();
    }
        
    static void rodarDemonstracao() {
        Configuracao config = new Configuracao();
        config.numRodadas = 100;     
        config.numMedicos = 1;        
        config.probChegada = 0.5;
        config.semente = 7;
        config.validar();

        Simulacao sim = new Simulacao(config, true); 
        sim.executar();

        System.out.println("|Chegadas: " + sim.getChegadas());
        System.out.println("|Atendidos: " + sim.getAtendidos());
        System.out.println("|Recusados: " + sim.getRecusados());
    }
}