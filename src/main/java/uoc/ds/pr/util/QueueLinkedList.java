package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.Queue;

public class QueueLinkedList<E> extends LinkedList<E> implements Queue<E> {

    // Constructor
    public QueueLinkedList() {
        super();
    }

    @Override
    public void add(E e) {
        super.insertEnd(e);
    }

    @Override
    public E poll() {
        return super.deleteFirst();
    }

    @Override
    public E peek() {
        LinkedNode<E> primer = this.last.getNext();
        return primer.getElem();
    }

    /***
     * Devuelve el último elemento de la lista enlazada, que en nuestro caso es una pila
     * @return Devuelve la última pila encolada
     */
    public E getLast() {
        return this.last.getElem();
    }

}
