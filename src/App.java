public class App {
    public static void main(String[] args){
        Configuracao config = new Configuracao();
        config.numRodadas = 30;
        Simulacao simul = new Simulacao(config, true);
        simul.executar();
        System.out.println("|Chegadas: " + simul.getChegadas());
        System.out.println("|Atendidos: " + simul.getAtendidos());
        System.out.println("|Recusados: " + simul.getRecusados());

    }
}
