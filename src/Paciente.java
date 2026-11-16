public class Paciente {
    private String id;
    private int prioridade;
    private int rodadaChegada;
    private int rodadaAtendimento;
    private int tempoRestante;

    public Paciente(String id, String prioridade, int rodadaChegada, int rodadaAtendimento, int tempoRestante){
        this.id = id;
        this.prioridade = 0;
        this.rodadaChegada = rodadaChegada;
        this.rodadaAtendimento = -1;
        this.tempoRestante = tempoRestante;
    }

    public String getId(){
        return id;
    }

    public int getPrioridade(){
        return prioridade;
    }

    public int getRodadaChegada(){
        return rodadaChegada;
    }

    public int getRodadaAtendimento(){
        return rodadaAtendimento;
    }

    public int getTempoRestante(){
        return tempoRestante;
    }

    public void setID(String id){
        this.id = id;
    }

    public void setPrioridade(int prioridade){
        this.prioridade = prioridade;
    }

    public void setRodadaChegada(int rodadaChegada){
        this.rodadaChegada = rodadaChegada;
    }

    public void setRodadaAtendimento(int rodadaAtendimento){
        this.rodadaAtendimento = rodadaAtendimento;
    }

    public void setTempoRestante(int tempoRestante){
        this.tempoRestante = tempoRestante;
    }

    public int getEspera(){
        if(rodadaAtendimento != -1){
            return rodadaAtendimento - rodadaChegada;
        }
        return -1;
    }


}
