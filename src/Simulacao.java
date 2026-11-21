import java.util.ArrayList;
import java.util.Random;

public class Simulacao {
    private static final String[] nomesCor = {"VERMELHA", "AMARELA", "VERDE", "AZUL"};
    private Configuracao config;
    private Random random;
    private QueueArray filaEntrada;
    private QueueArray[] filaPrioridade;
    private Paciente[] enfermeiros;
    private Paciente[] medicos;
    private int rodadaAtual;
    private boolean imprimirEventos;
    
    private int chegadas;
    private int recusados;
    private int atendidos;
    private ArrayList<Paciente> todos;
    private int[][] tamanhoFilas;
    private int[] enfermeirosOcupados;
    private int[] medicosOcupados;
    private int[] recusadosAcum;

    public Simulacao(Configuracao config, boolean imprimirEventos){
        this.config = config;
        this.imprimirEventos = imprimirEventos;
        random = new Random(config.semente);
        filaEntrada = new QueueArray(config.capFilaEntrada);
        filaPrioridade = new QueueArray[4];
        filaPrioridade[0] = new QueueArray(config.capFilaPrioridade);
        filaPrioridade[1] = new QueueArray(config.capFilaPrioridade);
        filaPrioridade[2] = new QueueArray(config.capFilaPrioridade);
        filaPrioridade[3] = new QueueArray(config.capFilaPrioridade);
        enfermeiros = new Paciente[config.numEnfermeiros];
        medicos = new Paciente[config.numMedicos];
        todos = new ArrayList<>();
        tamanhoFilas = new int[config.numRodadas][5];
        enfermeirosOcupados = new int[config.numRodadas];
        medicosOcupados = new int[config.numRodadas];
        recusadosAcum = new int[config.numRodadas];
        chegadas = 0;
        recusados = 0;
        atendidos = 0;
        rodadaAtual = 0;
    }

    public int getChegadas(){
        return chegadas;
    }

    public int getRecusados(){
        return recusados;
    }

    public int getAtendidos(){
        return atendidos;
    }

    public ArrayList<Paciente> getTodos(){
        return todos;
    }

    public int[][] getTamanhoFilas(){
        return tamanhoFilas;
    }
    
    public int[] getEnfermeirosOcupados(){
        return enfermeirosOcupados;
    }

    public int[] getMedicosOcupados(){
        return medicosOcupados;
    }

    public int[] getRecusadosAcum(){
        return recusadosAcum;
    }

    public void executar(){
        for(rodadaAtual = 1; rodadaAtual <= config.numRodadas; rodadaAtual++ ){
            processarMedicos();
            processarTriagem();
            processarEntrada();
            registrarRodada();
        }
        imprimirEstadoFinal();
    }

    public void processarMedicos(){
        for(int i = 0; i < medicos.length; i++){
            Paciente p = medicos[i];
            if(p != null){
                p.setTempoRestante(p.getTempoRestante()-1);
                if(p.getTempoRestante() == 0){
                    atendidos++;
                    medicos[i]= null;
                    log(p.getId() + " terminou o atendimento (médico N° " + i + ")");
                }
            }
            if(medicos[i] == null){
                for(int j = 0; j < 4; j++){
                    if(!filaPrioridade[j].isEmpty()){
                        Paciente novo = filaPrioridade[j].dequeue();
                        novo.setRodadaAtendimento(rodadaAtual);
                        novo.setTempoRestante(config.duracaoAtendimento[j]);
                        medicos[i]= novo;
                        log(novo.getId() + "(" + nomesCor[j] + ") começou o atendimento (médico " + i + ", " + config.duracaoAtendimento[j]+ " rodadas)");
                        break;
                    }
                }
            }

        }
    }

    public void processarTriagem(){
        for(int i = 0; i < enfermeiros.length; i++){
            Paciente p = enfermeiros[i];
            if(p != null){
                if(p.getTempoRestante() != 0){
                    p.setTempoRestante(p.getTempoRestante()-1);
                }
                if(p.getTempoRestante() == 0){
                    if (p.getPrioridade() == 0) {
                        p.setPrioridade(sortearCor());
                        log(p.getId() + " triagem concluída |" + " Prioridade atribuida ao paciente: " + p.getPrioridade() + " - (enfermeiro/a N° " + i  + ")");
                    }
                    if(filaPrioridade[p.getPrioridade()-1].enqueue(p)){
                        enfermeiros[i] = null;
                    }
                }
            }

            if(enfermeiros[i] == null && !filaEntrada.isEmpty()){
                Paciente novo = filaEntrada.dequeue();
                novo.setTempoRestante(config.duracaoTriagem);
                enfermeiros[i] = novo;
                log(novo.getId() + " entrou na triagem - (enfermeiro/a N° " + i + ")");
            }
        }
    }

    public int sortearCor(){
        double x = random.nextDouble();
        double acumulado = 0;
        for(int i = 0; i < 4; i++){
            acumulado = acumulado + config.probCor[i];
            if(x < acumulado){
                return i + 1;
            }
        }
        return 4;
    }

    public void processarEntrada(){
        double x = random.nextDouble();
        if(x >= config.probChegada){
            return;
        }
        chegadas++;
        String id = String.format("PACIENTE-%03d",chegadas);
        Paciente p = new Paciente(id, rodadaAtual);
        if(filaEntrada.enqueue(p)){
            todos.add(p);
            log(p.getId() + " esperando atendimento na fila de chegada.");
        }else{
            recusados++;
            log(p.getId() + " recusado por fila cheia.");
        }
    }

    public void registrarRodada(){
        int r = 0;
        if(rodadaAtual != 0){
            r = rodadaAtual-1;
        }

        tamanhoFilas[r][0] = filaEntrada.size();
        for(int i = 0; i < 4; i ++){
            tamanhoFilas[r][i + 1] = filaPrioridade[i].size();
        }
        int soma = 0;
        for(int i = 0; i < enfermeiros.length; i++){
            if(enfermeiros[i] != null){
                soma ++;
            }
        }
        enfermeirosOcupados[r] = soma;
        soma = 0;
        for(int i = 0; i < medicos.length; i++){
            if(medicos[i] != null){
                soma ++;
            }
        }
        medicosOcupados[r] = soma;
        recusadosAcum[r] = recusados;
    }

    public void imprimirEstadoFinal(){
        System.out.println("=== Estado final apos " + config.numRodadas + " rodadas ===");
        System.out.println("=== Fila de entrada ===\n" + filaEntrada.toString());
        for(int i = 0; i < nomesCor.length; i++){
            System.out.println(nomesCor[i] + "\nTamanho: " + filaPrioridade[i].size()+ "\n" + filaPrioridade[i]);
        }
        System.out.println("=== Enfermeiros em triagem ===");
        int enf = 0;
        for(int i = 0; i < enfermeiros.length; i++){
            if(enfermeiros[i] != null){
                System.out.println("Enfermeiro " + i + " em triagem do paciente: " + enfermeiros[i].getId());
                enf ++;
            }
        }
        if(enf == 0){
            System.out.println("Nenhum enfermeiro na triagem");
        }
        System.out.println("=== Médicos em atendimento ===");
        int med = 0;
        for(int i = 0; i < medicos.length; i++){
            if(medicos[i] != null){
                System.out.println("Médico " + i + " atendendo paciente: " + medicos[i].getId()+ " (Faltam " + medicos[i].getTempoRestante() + " rodadas)");
                med++;
            }
        }
        if(med == 0){
            System.out.println("Nenhum médico em atendimento");
        }
    }

    public void log(String msg){
        if(imprimirEventos){
            System.out.println("Rodada " + rodadaAtual + ": " + msg);
        }
    }


}
