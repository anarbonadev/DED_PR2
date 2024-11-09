package uoc.ds.pr.util;

import uoc.ds.pr.model.Book;
import uoc.ds.pr.model.StoredBook;

public class BookWareHouse {


    // Constructor
    public BookWareHouse() {
    }


    /***
     * Función que cuenta la cantidad de libros total que hay en la cola, sumando los libros de todas las pilas
     * @return Devuelve el número toal de libros
     */
    public int numBooks() {

        // TODO: tengo que ver dónde estarían guardados estos datos

        return 0;
    }

    /***
     * Función que cuenta la cantidad de pilas totales que hay en la cola.
     * @return Devuelve el número total de pilas
     */
    public int numStacks() {

        // TODO: tengo que ver dónde estarían guardados estos datos

        return 0;
    }


    /***
     * Función que guarda un libro con los datos que nos llegan
     * @param bookId Identificador del libro
     * @param title Título
     * @param publisher Editorial
     * @param edition Edición
     * @param publicationYear Año de publicación
     * @param isbn ISBN
     * @param author Autor
     * @param theme Temática
     */
    public void storeBook(String bookId, String title, String publisher, String edition, int publicationYear,
                          String isbn, String author, String theme) {
        // TODO: ¿dónde guardo el libro?
    }


    /***
     * Función que guarda un libro dado el objeto book
     * @param book Objeto que contiene los datos del libro que hay que almacenar
     */
    public void storeBook(Book book) {
        // TODO: ¿dónde guardo el libro?
    }


    /***
     * Función que extrae el primer libro pendiente de catalogar. Debe ser el libro de la cima de la primera pila
     * que hay en la cola
     * @return Devuelve un libro pendiente de catalogar
     */
    public Book getBookPendingCataloging() {
        Book book = null;

        // TODO: ¿dónde tengo la cola que hay que procesar?

        return book;
    }

    /***
     * Función que devuelve la posición que ocupa un libro dentro la cola, dado su identificador
     * @param bookId Identificador del libro
     * @return Devuelve la posición que ocupa el libro
     */
    public Position getPosition(String bookId) {
        Position position = null;

        // TODO: desarrollar esta parte

        return position;
    }


    /***
     * Clase anidada dentro de la clase BookWareHouse
     */
    public class Position {


        /***
         * Función que devuelve el número de pila dónde está el libro. La primera pila es la número 0, la siguiente
         * es la número 1, etc..
         * @return Devuelve el número de la cola dónde se encuentra el libro
         */
        public int getNumStack() {

            // TODO: desarrollar esta parte

            return 0;
        }

        /***
         * Función que devuelve la posición del libro dentro de la pila. El libro que está en la cima es el 0
         * y va incrementando el valor hacia abajo hasta la posición 9
         * @return Devuelve la posición del libro, dentro de la pila dónde está el libro.
         */
        public int getNum() {

            // TODO: desarrollar esta parte

            return 0;
        }
    }
}
