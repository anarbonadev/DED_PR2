package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.StackArrayImpl;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.model.*;

import static uoc.ds.pr.Library.*;

public class BookWareHouse {

    /***
     * Aquí declaro todas las estructuras de datos que vamos a usar. Se indican en la PEC1, página 15
     */

    // Lectores
    public static final Reader[] readers = new Reader[MAX_NUM_READERS];

    // Trabajadores
    public static final Worker[] workers = new Worker[MAX_NUM_WORKERS];

    // Declaro la cola de pilas
    private QueueLinkedList<StackArrayImpl<StoredBook>> queueLinkedList = new QueueLinkedList<>();

    // Libros catalogados en general, por todos los trabajadores
    public static final LinkedList<CatalogedBook> catalogedBooks = new LinkedList<>();

    // Préstamos
    public static final LinkedList<Loan> books = new LinkedList<>();


    // Préstamos de un lector








    // Constructor
    public BookWareHouse() {
    }

    /***
     * Función que cuenta la cantidad de libros total que hay en la cola, sumando los libros de todas las pilas
     * @return Devuelve el número total de libros
     */
    public int numBooks() {

        int totalBooks = 0;

        // Creamos un iterador para recorrer la cola
        Iterator<StackArrayImpl<StoredBook>> iterator = queueLinkedList.values();

        while (iterator.hasNext()) {
            StackArrayImpl<StoredBook> stackArray = iterator.next();
            totalBooks += stackArray.size();
        }

        return totalBooks;
    }

    /***
     * Función que cuenta la cantidad de pilas totales que hay en la cola.
     * @return Devuelve el número total de pilas
     */
    public int numStacks() {

        int totalStacks = 0;

        // Creamos un iterador para recorrer la cola
        Iterator<StackArrayImpl<StoredBook>> iterator = queueLinkedList.values();

        while (iterator.hasNext()) {
            iterator.next();
            totalStacks++;
        }

        return totalStacks;
    }


    /***
     * Función que devuelve la cantidad total de libros catalogados
     * @return Devuelve la cantidad total de libros catalogados
     */
    public int numCatalogBooks() {

        // TODO: tengo que ver que debe devolver. Parece que tiene que devolver la cantidad total de libros catalogados

        return 0;
    }

    /***
     * Función que devuelve la cantidad de libros catalogados por un trabajador. Se consideran catalogados por un
     * trabajador cuando es la primera vez que se cataloga un libro.
     *      Si un segundo trabajador cataloga un libro que ya fue catalogado por otro trabajador, no cuenta
     * @param workerId Identificador del trabajador
     * @return Devuelve la cantidad total de libros catalogados por el trabajador
     */
    public int numCatalogBooksInWorker(String workerId) {

        int numberBooksCatalogued = 0;

        for(Worker worker : BookWareHouse.workers) {
            if(worker.getId().equals(workerId)) {
                return worker.getTotalNumberOfCatalogedBooks();
            }

        }
        return 0;
    }


    /***
     * Función que devuelve la cantidad TOTAL de libros procesados por un trabajador, le cuente o no como libro
     * catalogado
     * @param workerId Identificador del trabajador
     * @return Devuelve la cantidad total de libros procesados por el trabajador
     */
    public int totalCatalogBooksByWorker(String workerId) {

        // TODO: tengo que ver de dónde saco el total de libros procesados por un trabajador

        return 0;
    }


    /***
     * Función que devuelve la cantidad total de copias que hay en la biblioteca de un libro concreto
     * @param bookId Identificador del libro del que queremos saber cuántas copias tenemos
     * @return Devuelve la cantidad de copias que hay del libro
     */
    public int numCopies(String bookId) {

        // TODO: sacar de la colección de libros catalogados la cantidad de copias

        return 0;
    }


    /***
     * Función que devuelve la cantidad de libros que se han prestado en total
     * @return Devuelve la cantidad de libros prestados
     */
    public int numLoans() {

        // TODO: PEC1 página 15 -> los libros prestados son una lista encadenada (Linked List)

        return 0;
    }


    /***
     * Función que devuelve el número de préstamos gestionados por un trabajador
     * @param workerId Identificador del trabajador
     * @return El número de préstamos que ha gestionado
     */
    public int numLoansByWorker(String workerId) {

        // TODO: ver de dónde saco este dato

        return 0;
    }


    /***
     * Función que devuelve el número de copias prestadas de un libro
     * @param bookId Identificador del libro
     * @return El número de copias que hay prestadas
     */
    public int numLoansByBook(String bookId) {

        // TODO: ver de dónde saco este dato

        return 0;
    }


    /***
     * Función que devuelve el número de préstamos que tiene un lector
     * @param readerId Identificador del lector
     * @return El número de copias que tiene un lector en préstamo
     */
    public int numCurrentLoansByReader(String readerId) {

        // TODO: ver de dónde saco este dato

        return 0;
    }


    /***
     * Función que devuelve la cantidad de préstamos que han sido cerrados por un trabajador
     * @param workerId Identificador del trabajador
     * @return
     */
    public int numClosedLoansByWorker(String workerId) {

        // TODO: ver de dónde saco este dato

        return 0;
    }


    /***
     * Función que devuelve la cantidad de préstamos que han sido cerrados por un lector
     * @param readerId
     * @return
     */
    public int numClosedLoansByReader(String readerId) {

        // TODO: ver de dónde saco este dato

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

        // Extraemos el libro que está en la cima de la primera STACK
        StoredBook storedBook = queueLinkedList.peek().pop();

        // Si el tamaño de la primera pila es 0, la eliminamos
        if(queueLinkedList.peek().size() == 0)
        {
            queueLinkedList.deleteFirst();
        }

        // Mapeo el objeto StoredBook a un Book
        if(storedBook != null)
        {
            return storedBook;
        }

        return null;
    }

    /***
     * Función que devuelve la posición que ocupa un libro dentro la cola, dado su identificador
     * @param bookId Identificador del libro
     * @return Devuelve la posición que ocupa el libro
     */
    public Position getPosition(String bookId) {
        Position position = new Position();
        boolean found = false;

        // Creamos un iterador para recorrer la cola
        Iterator<StackArrayImpl<StoredBook>> iterator = queueLinkedList.values();

        while (iterator.hasNext() && !found) {

            // Incrementamos el número de STACK
            position.incNumStack();

            // Reseteamos el valor de numPosition
            position.setNumPosition(-1);

            // Sacamos la cola que ocupa esta posición del iterador
            StackArrayImpl<StoredBook> stackArray = iterator.next();

            // Creamos un nuevo iterador, pero esta vez de la cola
            Iterator<StoredBook> storedBookIterator = stackArray.values();

            while (storedBookIterator.hasNext() && !found) {

                // Sacamos el libro que ocupa esta posición del iterador
                StoredBook storedBook = storedBookIterator.next();

                if(bookId.equals(storedBook.getBookId())) {
                    found = true;
                }

                position.incNumPosition();
            }
        }

        return position;
    }


    /***
     * Clase anidada dentro de la clase BookWareHouse
     */
    public class Position {

        private int numStack;
        private int numPosition;

        // Constructor
        public Position() {
            this.numStack = -1;
            this.numPosition = -1;
        }

        /***
         * Función que devuelve el número de pila dónde está el libro. La primera pila es la número 0, la siguiente
         * es la número 1, etc..
         * @return Devuelve el número de la cola dónde se encuentra el libro
         */
        public int getNumStack() {
            return numStack;
        }

        /***
         * Función que devuelve la posición del libro dentro de la pila. El libro que está en la cima es el 0
         * y va incrementando el valor hacia abajo hasta la posición 9
         * @return Devuelve la posición del libro, dentro de la pila dónde está el libro.
         */
        public int getNum() {
            return numPosition;
        }

        /***
         * Función que incrementa en +1 el valor de numStack
         */
        public void incNumStack() {
            this.numStack++;
        }

        /***
         * Función que incrementa en +1 el valor de numPosition
         */
        public void incNumPosition() {
            this.numPosition++;
        }

        public void setNumPosition(int numPosition) {
            this.numPosition = numPosition;
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

            // Comprobamos si la cola está vacía o si la última cola está llena
            if(queueLinkedList.isEmpty() || queueLinkedList.getLast().isFull()) {

                // Instanciamos una nueva pila
                StackArrayImpl<StoredBook> newStackArray = new StackArrayImpl<StoredBook>(MAX_BOOK_STACK);

                // Añadimos el libro a la pila
                newStackArray.push(storedBook);

                // Insertamos la pila en la cola
                queueLinkedList.add(newStackArray);

            } else {
                // Añadimos el libro a la última pila
                queueLinkedList.getLast().push(storedBook);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
