package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.StackArrayImpl;
import uoc.ds.pr.model.Book;
import uoc.ds.pr.model.CatalogedBook;
import uoc.ds.pr.model.StoredBook;

import static uoc.ds.pr.Library.MAX_BOOK_STACK;

public class BookWareHouse {

    // Declaro la cola de pilas
    private QueueLinkedList<StackArrayImpl<StoredBook>> queueLinkedList = new QueueLinkedList<>();

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

        // Las funciones para almacenar los libros reciben objetos de clase Book. Pero los objetos que metemos en
        // las pilas y, a su vez, en la cola, son objetos de tipo StoredBook, ya que una vez están en las pilas
        // son libros almacenados

        // Creo una instancia de un StoredBook con los datos que recibo
        StoredBook storedBook = new StoredBook(bookId, title, publisher, edition, publicationYear, isbn, author, theme);

        // Llamo a una función privada y común, que almacena el libro
        addBookToQueue(storedBook);
    }


    /***
     * Función que guarda un libro dado el objeto book
     * @param book Objeto que contiene los datos del libro que hay que almacenar
     */
    public void storeBook(Book book) {
        // Recibimos un objeto de tipo Book, pero necesitamos un StoredBook
        StoredBook storedBook = new StoredBook(book);

        // Llamo a una función privada y común, que almacena el libro
        addBookToQueue(storedBook);
    }


    /***
     * Función que extrae el primer libro pendiente de catalogar. Debe ser el libro de la cima de la primera pila
     * que hay en la cola
     * @return Devuelve un libro pendiente de catalogar
     */
    public Book getBookPendingCataloging() {
        Book book = null;

        // Extraemos, sin eliminarla, la primera pila de la QUEUE
        StackArrayImpl<StoredBook> firstStack = this.queueLinkedList.peek();

        // Extraemos el primer libro de la STACK
        StoredBook storedBook = firstStack.pop();

        // Si el tamaño de la primera pila es 0, la eliminamos
        if(firstStack.size() == 0)
        {
            //this.queueLinkedList.poll();

            // TODO: comprobar que realmente elimina el primero elemento de la cola
            this.queueLinkedList.deleteFirst();
        }

        // Mapeo el objeto StoredBook a un Book
        if(storedBook != null)
        {
            // String bookId, String title, String publisher, String edition, int publicationYear, String isbn, String author, String theme
            book = new Book(storedBook.getBookId(), storedBook.getTitle(), storedBook.getPublisher(), storedBook.getEdition()
                    ,storedBook.getPublicationYear(), storedBook.getIsbn(), storedBook.getAuthor(), storedBook.getTheme());
        }

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

    /***
     * Función que nos indica si la cola está vacía o no
     * @return Devuelve true si la cola está vacía, y false en caso contrario
     */
    public boolean isQueueEmpty() {
        return queueLinkedList.isEmpty();
    }


    /***********************************************************************************/
    /******************** PRIVATE OPERATIONS *******************************************/
    /***********************************************************************************/


    /***
     * Función que recibe un libro de tipo StoredBook y en función de si la última pila de la cola está llena o no
     * hace una de 2 cosas:
     *      - Si la última pila tiene menos de 10 libros, añade este StoredBook a esa cola
     *      - Si la última pila tiene 10 libros, crea una nueva pila, añade el StoredBook y luego añade la pila a la cola
     * @param storedBook
     */
    private void addBookToQueue(StoredBook storedBook) {
        try {

            // Comprobamos si la cola está vacía o no
            if(this.queueLinkedList.isEmpty()) {
                // Si está vacía, se añade una nueva pila con el libro
                addNewStack(storedBook);
            } else {

                // Tengo que recuperar el último montón de libros y ver cuántos libros tiene
                StackArrayImpl<StoredBook> lastStack = this.queueLinkedList.getLastNode();

                // Si tenemos una pila y no está llena
                if(lastStack != null && !lastStack.isFull()) {
                    lastStack.push(storedBook);
                } else {
                    // Hay que crear una nueva pila, añadir el libro, y la pila a la cola
                    addNewStack(storedBook);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /***
     * Función que crea una nueva pila, añade el libro que recibe por parámetros a dicha pila y luego añade la pila
     * a la cola
     * @param storedBook Es el libro que vamos a añadir
     */
    private void addNewStack(StoredBook storedBook) {
        // Instanciamos una nueva pila
        StackArrayImpl<StoredBook> newStackArray = new StackArrayImpl<StoredBook>(MAX_BOOK_STACK);

        // Añadimos el libro a la pila
        newStackArray.push(storedBook);

        // Insertamos la pila en la cola
        this.queueLinkedList.add(newStackArray);
    }

}
