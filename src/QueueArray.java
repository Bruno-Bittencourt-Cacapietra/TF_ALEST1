public class QueueArray{

    // Atributos
    private ListArrayOfPaciente fila;
    private int capacidade;

    public QueueArray(int capacidade) {
        fila = new ListArrayOfPaciente();
        this.capacidade = capacidade;
    }

    // insere o elemento no final da fila
    public boolean enqueue(Paciente element) {
        if(isFull()){
            return false;
        }
        fila.add(element);
        return true;
    }

    // remove e retorna o elemento e do início da fila, 
    // e dá erro se a fila estiver vazia
    public Paciente dequeue() {
        if(fila.isEmpty()){
            throw new IndexOutOfBoundsException ("Fila vazia");
        }
        return fila.removeByIndex(0);
    }

    // retorna, mas não remove, o primeiro elemento da fila, 
    // e dá erro se a fila estiver vazia
    public Paciente head() {
        if(fila.isEmpty()){
            throw new IndexOutOfBoundsException ("Fila vazia");
        }
        return fila.get(0);
    }

    // retorna o número de elementos da fila
    public int size() {
        return fila.size();
    }

    //retorna true se a fila estiver vazia, e false caso contrário
    public boolean isEmpty() {
        return fila.isEmpty();
    }

    public boolean isFull(){
        return fila.size() == capacidade;
    }

    //esvazia a fila
    public void clear() {
        fila.clear();
    }
}