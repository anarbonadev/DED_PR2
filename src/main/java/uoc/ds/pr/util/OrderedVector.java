package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;

import java.util.Comparator;

public class OrderedVector<E> implements FiniteContainer<E> {

    // Atributos
    private final Comparator<E> comparator;

    private int len;

    private final E[] data;

    // Constructor
    public OrderedVector(int max, Comparator<E> comparator) {
        this.comparator = comparator;
        data = (E[]) new Object[max];
        len = 0;
    }

    /***
     *
     * @param vIn
     */
    public void update(E vIn) {
        int i = 0;

        // If the element exists, we delete the element to reinsert it in its position
        if (isFull()) {
            E pOut = last();
            if (comparator.compare(pOut, vIn) < 0) {
                delete(pOut);
                update(vIn);
                return;
            }
            else {
                return;
            }
        }

        // traversal to determine the position in which to insert
        while (i < len && data[i] != null && comparator.compare(data[i], vIn) >= 0) {
            i++;
        }

        // shift to the right of all elements
        rshift(i);

        // element is inserted at position
        data[i] = vIn;
        len++;

    }


    /***
     *
     * @return
     */
    public E last() {
        return data[len-1];
    }


    /***
     * Función que usamos para elimiar un elemento concreto del OrderedVector
     * @param elem Es el elemento que vamos a borrar
     */
    public void delete (E elem) {
        int i = 0;
        boolean found = false;

        while (!found && i < len)
            found = compare(elem, data[i++]);

        if (found) {
            if (i<len) {
                lshift(i-1);
            }
            else {
                lshift(i);
            }
            len--;
        }
    }

    /***
     * Función que compara 2 elementos para ver si son iguales
     * @param elem1 Elemento 1
     * @param elem2 Elemento 2
     * @return
     */
    private boolean compare(E elem1, E elem2) {
        return ((Comparable<E>)elem1).compareTo(elem2) == 0;
    }


    /***
     *
     * @param i
     */
    public void rshift(int i) {
        // shift all elements one position (RIGHT)
        int p = len-1;
        while (p >= i) {
            data[p+1] = data[p];
            p--;
        }
    }


    /***
     *
     * @param i
     */
    public void lshift(int i) {
        // shift all elements one position (LEFT)
        int p = i;
        while (p < len-1) {
            data[p] = data[p+1];
            p++;
        }
    }


    @Override
    public boolean isFull() {
        return len == data.length;
    }

    @Override
    public boolean isEmpty() {
        return len == 0;
    }

    @Override
    public int size() {
        return len;
    }

    @Override
    public Iterator<E> values() {
        return new IteratorArrayImpl<>(data, len,0);
    }
}
