package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.adt.sequential.StackArrayImpl;
import edu.uoc.ds.exceptions.EmptyContainerException;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.model.StoredBook;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class QueueLinkedList<E> implements Queue<E> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("error_messages");

    // Lista encadenada dónde vamos a guardar los elementos de la cola
    private final LinkedList<E> list;

    // Constructor
    public QueueLinkedList() {
        this.list = new LinkedList<>();
    }

    public QueueLinkedList(LinkedList<E> list) {
        this.list = list;
    }

    /***
     * Añadimos una nueva pila al final de la lista
     * @param e Es la pila que vamos a añadir al final
     */
    @Override
    public void add(E e) {
        list.insertEnd(e);
    }

    /***
     * Eliminamos y devolvemos la primera pila de la lista
     * @return Es la pila que estamos sacando de la cola
     */
    @Override
    public E poll() {
        if(list.isEmpty()){
            throw new EmptyContainerException(bundle.getString("exception.queueLinkedList.emptyQueue"));
        }
        return list.deleteFirst();
    }

    /***
     * Devolvemos la primera pila de la cola sin eliminarla
     * @return Es la pila que estamos sacando de la cola
     */
    @Override
    public E peek() {
        if(list.isEmpty()){
            throw new EmptyContainerException(bundle.getString("exception.queueLinkedList.emptyQueue"));
        }
        return list.values().next();
    }

    /***
     * Verificamos si la cola está vacía
     * @return Devuelve true si está vacía, y false en caso contrario
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /***
     * Devuelve el tamaño de la cola
     * @return El tamaño de la cola
     */
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<E> values() {
        return null;
    }

    /***
     * Devuelve el tamaño de la última pila que hay en la cola
     * @return El tamaño de la última pila
     */
    /*
    public int getLastStackSize(){
        if(list.isEmpty()){
            throw new EmptyContainerException(bundle.getString("exception.queueLinkedList.emptyQueue"));
        }

        // Obtenemos el primer objeto de la Linked List
        E lastPos =  list.values().next();

        // Vamos avanzando hasta llegar al último nodo
        while (lastPos != null && list.values().hasNext()){
            lastPos = list.values().next();
        }

        if(lastPos != null){
            try {
                // Usamos reflexión para llamar a size() de manera genérica
                Method sizeMethod = lastPos.getClass().getMethod("size");
                return (int) sizeMethod.invoke(lastPos);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

        }
    }
    */


    /***
     * Función que devuelve el último nodo de la cola. En nuestro caso, nos devolverá la última pila
     * @return Si la cola no está vacía, devuelve la última cola, en caso contrario, null
     */
    public E getLastNode(){
        if(list.isEmpty()){
            //throw new EmptyContainerException(bundle.getString("exception.queueLinkedList.emptyQueue"));
            return null;
        }

        // Obtenemos el primer objeto de la Linked List
        E lastPos =  list.values().next();

        // Vamos avanzando hasta llegar al último nodo
        while (lastPos != null && list.values().hasNext()){
            lastPos = list.values().next();
        }

        if(lastPos != null){
            return lastPos;
        }

        return null;
    }
}
