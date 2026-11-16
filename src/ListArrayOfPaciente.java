/**
 * Classe que implementa uma lista linear usando arranjo.
 * @author Isabel H. Manssour, Gabriel Fonseca Silva
 */

public class ListArrayOfInteger {

    // Atributos
    private static final int INITIAL_SIZE = 10;
    private Integer[] data;
    private int count;

    /**
     * Construtor da lista.
     */
    public ListArrayOfInteger() {
        this(INITIAL_SIZE);
    }

    /**
     * Construtor da lista.
     * @param tam tamanho inicial a ser alocado para data[]
     */
    public ListArrayOfInteger(int tam) {
        if (tam <= 0) {
            tam = INITIAL_SIZE;
        }
        data = new Integer[tam];
        count = 0;
    }

    /**
     * Esvazia a lista.
     */
    public void clear() { // O(1)
        data = new Integer[INITIAL_SIZE];
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
    public void add(Integer element) { // O(1) 
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
    public int get(int index) { // O(1)
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
        Integer[] newData = new Integer[newCapacity];
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
    public boolean remove(Integer element) { // O(n)
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
    public int set(int index, Integer element) {// O(1)
        if(index < 0 || index >= size()){
            throw new IndexOutOfBoundsException("Deu ruim");
        }
        Integer aux = data[index];
        data[index] = element;
        return aux;
    }

    /**
     * Procura pelo elemento passado por parametro na lista e retorna true se a 
     * lista contem este elemento.
     * @param element o elemento a ser procurado
     * @return true se a lista contem o elemento passado por parametro
     */
    public boolean contains(Integer element) { // O(n)
        return indexOf(element) != -1;
    }
    
    /**
     * Insere um elemento em uma determinada posicao da lista
     *
     * @param index a posicao da lista onde o elemento sera inserido
     * @param element elemento a ser inserido
     * @throws IndexOutOfBoundsException se (index < 0 || index > size())
     */
    public void addByIndex(int index, Integer element) { // O(n)
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
    public int indexOf(Integer element) { // O(n)
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
    public Integer removeByIndex(int index) { // O(n)
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Deu ruim");
        }
        Integer remove = data[index];
        for (int i = index; i < count - 1; i++) {
            data[i] = data[i + 1];
        }
        data[count - 1] = null;
        count--;
        return remove;
    }

    public void sort(){// O(n²)
        for(int i = 0; i < count; i ++){ 
            for(int j = 0; j < count - 1 -i; j++){
                if(data[j] > data[j+1]){
                    Integer aux = data[j];
                    data[j] = data[j+1];
                    data [j+1] = aux;
                }
            }
        }
    }
    public void addIncreasingOrder(Integer element){// O(n²)
        add(element);
        sort();
    }

    public void reverse(){// O(n)
        Integer[] newData = new Integer[data.length];
        int index = 0;
        for(int i = count -1; i > -1; i--){
            newData[index] = data[i];
            index++;
        }
        data = newData;
    }

    public void unique(){// O(n²)
        if(data.length == 0){
            return;

        }
        int index = 0;
        Integer[] newData = new Integer [data.length];
        for(int i = 0; i < count; i++){ 
            boolean ehUnique = true;
            for(int j = 0; j < i; j++){
                if(data[i].equals(data[j])){
                ehUnique = false;
                break;
                }
            }
            if(ehUnique){
                newData[index] = data[i];
                index++;
            }
        }
        data = newData;
        count = index;
    }

    public int countOccurrences(Integer element){//O(n)
        int countIndex = 0;
        for(int i = 0; i < count; i++){
            if(data[i].equals(element)){
                countIndex++;
            }
        }
        return countIndex;
    }
}


