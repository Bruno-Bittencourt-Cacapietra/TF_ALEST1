public class QueueArray{

    // Atributos
    private ListArrayOfInteger fila;

    public QueueArray() {
        fila = new ListArrayOfInteger();
    }

    // insere o elemento no final da fila
    public void enqueue(Integer element) {
        fila.add(element);
    }

    // remove e retorna o elemento e do início da fila, 
    // e dá erro se a fila estiver vazia
    public Integer dequeue() {
        if(fila.isEmpty()){
            throw new IndexOutOfBoundsException ("Fila vazia");
        }
        return fila.removeByIndex(0);
    }

    // retorna, mas não remove, o primeiro elemento da fila, 
    // e dá erro se a fila estiver vazia
    public Integer head() {
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

    //esvazia a fila
    public void clear() {
        fila.clear();
    }

    public QueueArray clone(){
        QueueArray queue = new QueueArray();
        for(int i = 0; i < fila.size(); i++ ){
            queue.enqueue(fila.get(i));
        }
        return queue;
    }

    public void reverse(){
        fila.reverse();
    }

    public StackArray toStack(){
        StackArray stack = new StackArray();
        for(int i = 0; i < fila.size(); i++){
            stack.push(fila.get(i));
        }
        return stack;
    }

}