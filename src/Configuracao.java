public class Configuracao {
    public int numRodadas = 720;
    public double probChegada = 0.30;
    public int capFilaEntrada = 30;
    public int numEnfermeiros = 2;
    public int duracaoTriagem = 3;
    public double[] probCor = {0.05, 0.20, 0.45, 0.30};
    public int capFilaPrioridade = 100;
    public int numMedicos = 5;
    public int[] duracaoAtendimento = {20, 15, 10, 6};
    public long semente = 42;

    public void validar() {
        if (numRodadas < 100 || numRodadas > 10000) {
            throw new IllegalArgumentException("numRodadas deve ficar entre 100 e 10000");
        }
        if (probChegada < 0.05 || probChegada > 1.0) {
            throw new IllegalArgumentException("probChegada deve ficar entre 0,05 e 1,00");
        }
        if (capFilaEntrada < 5 || capFilaEntrada > 200) {
            throw new IllegalArgumentException("capFilaEntrada deve ficar entre 5 e 200");
        }
        if (numEnfermeiros < 1 || numEnfermeiros > 5) {
            throw new IllegalArgumentException("numEnfermeiros deve ficar entre 1 e 5");
        }
        if (duracaoTriagem < 1 || duracaoTriagem > 10) {
            throw new IllegalArgumentException("duracaoTriagem deve ficar entre 1 e 10");
        }
        if (capFilaPrioridade < 10 || capFilaPrioridade > 500) {
            throw new IllegalArgumentException("capFilaPrioridade deve ficar entre 10 e 500");
        }
        if (numMedicos < 1 || numMedicos > 10) {
            throw new IllegalArgumentException("numMedicos deve ficar entre 1 e 10");
        }
        if (probCor.length != 4 || duracaoAtendimento.length != 4) {
            throw new IllegalArgumentException("probCor e duracaoAtendimento devem ter 4 valores");
        }

        // As 4 probabilidades das cores precisam somar 1
        double soma = 0;
        for (int i = 0; i < 4; i++) {
            soma = soma + probCor[i];
            if (duracaoAtendimento[i] < 1 || duracaoAtendimento[i] > 90) {
                throw new IllegalArgumentException("duracaoAtendimento deve ficar entre 1 e 90");
            }
        }
        if (Math.abs(soma - 1.0) > 0.000001) {
            throw new IllegalArgumentException("A soma de probCor deve ser 1 (agora e " + soma + ")");
        }
    }
}