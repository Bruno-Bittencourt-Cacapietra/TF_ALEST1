/**
 * Classe que implementa uma lista linear usando arranjo.
 * @author Isabel H. Manssour, Gabriel Fonseca Silva
 */

public class ListArrayOfPaciente {

    // Atributos
    private static final int INITIAL_SIZE = 10;
    private Paciente[] data;
    private int count;

    /**
     * Construtor da lista.
     */
    public ListArrayOfPaciente() {
        this(INITIAL_SIZE);
    }

    /**
     * Construtor da lista.
     * @param tam tamanho inicial a ser alocado para data[]
     */
    public ListArrayOfPaciente(int tam) {
        if (tam <= 0) {
            tam = INITIAL_SIZE;
        }
        data = new Paciente[tam];
        count = 0;
    }

    /**
     * Esvazia a lista.
     */
    public void clear() { // O(1)
        data = new Paciente[INITIAL_SIZE];
        count = 0;
    }

    /**
     * Retorna true se a lista nao contem elementos.
     * @return true se a lista nao contem elementos
     */
    public boolean isEmpty() {// O(1)
        return (count == 0);
    }

    /**
     * Retorna o numero de elementos armazenados na lista.
     * @return o numero de elementos da lista
     */
    public int size() { // O(1)
        return count;
    }

    /**
     * Adiciona um elemento ao final da lista.
     * @param element elemento a ser adicionado ao final da lista
     */
    public void add(Paciente element) { // O(1) 
        if(count == data.length) {
            setCapacity(data.length * 2);
        }
        data[count] = element;
        count++;
    }

    /**
     * Retorna o elemento de uma determinada posicao da lista.
     * @param index posicao da lista
     * @return o elemento da posicao especificada
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    public Paciente get(int index) { // O(1)
        if(index < 0 || index >= size()){
            throw new IndexOutOfBoundsException("Deu ruim");
        }
        return data[index];
    }
    
    @Override
    public String toString() { // O(n)
        StringBuilder s = new StringBuilder();
        s.append("[");
        for (int i = 0; i < count; i++) {
            s.append(data[i]);
            if (i != (count - 1)) {
                s.append(",");
            }
        }
        s.append("]");
        return s.toString();
    }

    /**
     * Altera a capacidade do arranjo para um novo tamanho especificado.
     * Se o novo tamanho for menor que o tamanho atual, os elementos que
     * excederem o novo tamanho serão descartados.
     * @param newCapacity o novo tamanho do arranjo
     */
    private void setCapacity(int newCapacity) {// O(n)
        if(newCapacity == data.length) {
            return;
        }
        Paciente[] newData = new Paciente[newCapacity];
        int min = 0;
        if(data.length < newCapacity){
            min = data.length;
        } else {
            min = newCapacity;
        }
        for(int i = 0; i < min; i++){
            newData[i] = data[i];
        }
        data = newData;
    }

    /**
     * Remove a primeira ocorrencia do elemento na lista, se estiver presente
     *
     * @param element o elemento a ser removido
     * @return true se a lista contem o elemento especificado
     */
    public boolean remove(Paciente element) { // O(n)
        int index = indexOf(element);
        if (index == -1) {
            return false;
        }
        removeByIndex(index);
        return true;
    }

    /**
     * Substitui o elemento armazenado em uma determinada posicao da lista pelo
     * elemento passado por parametro, retornando o elemento que foi substituido.
     * @param index a posicao da lista
     * @param element o elemento a ser armazenado na lista
     * @return o elemento armazenado anteriormente na posicao da lista
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    public Paciente set(int index, Paciente element) {// O(1)
        if(index < 0 || index >= size()){
            throw new IndexOutOfBoundsException("Deu ruim");
        }
        Paciente aux = data[index];
        data[index] = element;
        return aux;
    }

    /**
     * Procura pelo elemento passado por parametro na lista e retorna true se a 
     * lista contem este elemento.
     * @param element o elemento a ser procurado
     * @return true se a lista contem o elemento passado por parametro
     */
    public boolean contains(Paciente element) { // O(n)
        return indexOf(element) != -1;
    }
    
    /**
     * Insere um elemento em uma determinada posicao da lista
     *
     * @param index a posicao da lista onde o elemento sera inserido
     * @param element elemento a ser inserido
     * @throws IndexOutOfBoundsException se (index < 0 || index > size())
     */
    public void addByIndex(int index, Paciente element) { // O(n)
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Deu ruim");
        }
        if (count == data.length) {
            setCapacity(data.length * 2);
        }
        for (int i = count; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = element;
        count++;
    }

    /**
     * Retorna o indice da primeira ocorrencia do elemento na lista, ou -1 se a
     * lista nao contem o elemento
     *
     * @param element o elemento a ser buscado
     * @return o indice da primeira ocorrencia do elemento na lista, ou -1 se a
     * lista nao contem o elemento
     */
    public int indexOf(Paciente element) { // O(n)
        for (int i = 0; i < count; i++) {
            if (data[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }
	
    /**
     * Remove o elemento de uma determinada posicao da lista
     *
     * @param index a posição da lista
     * @return o elemento que foi removido da lista
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    public Paciente removeByIndex(int index) { // O(n)
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Deu ruim");
        }
        Paciente remove = data[index];
        for (int i = index; i < count - 1; i++) {
            data[i] = data[i + 1];
        }
        data[count - 1] = null;
        count--;
        return remove;
    }
}


