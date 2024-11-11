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
    private final QueueLinkedList<StackArrayImpl<StoredBook>> queueLinkedList = new QueueLinkedList<>();

    // Libros catalogados en general, por todos los trabajadores
    public static final LinkedList<CatalogedBook> catalogedBooks = new LinkedList<>();

    // Préstamos
    public final LinkedList<Loan> loans = new LinkedList<>();


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
     * Función que devuelve la cantidad total de libros catalogados, que corresponden con la suma del campo
     * totalCopies de todos los libros catalogados
     * @return Devuelve la cantidad total de libros catalogados
     */
    public int numCatalogBooks() {

        int totalCatalogBooks = 0;

        // Primero recuperamos el iterador de catalogedBooks
        Iterator<CatalogedBook> iterator = catalogedBooks.values();

        // Luego recorremos la lista de libros buscando por isbn
        while(iterator.hasNext()) {
            CatalogedBook catalogedBook = iterator.next();
            totalCatalogBooks = totalCatalogBooks + catalogedBook.numCopies();
        }
        return totalCatalogBooks;
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

        for(Worker worker : workers) {
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

        for(Worker worker : workers) {
            if(worker.getId().equals(workerId)) {
                int dato = worker.getTotalNumberOfProcessedBooks();
                return worker.getTotalNumberOfProcessedBooks();
            }
        }

        return 0;
    }


    /***
     * Función que añade un libro a la lista de libros procesados por un trabajador
     * @param workerId Identificador del trabajador
     * @param bookToCatalog El libro que ya procesado
     */
    public void addBookToProcessedByWorker(String workerId, Book bookToCatalog) {
        for(Worker worker : workers) {
            if(worker.getId().equals(workerId)) {
                worker.addBookToProcessedBook(bookToCatalog);
                return;
            }
        }
    }


    /***
     * Función que devuelve la cantidad total de copias DISPONIBLES que hay en la biblioteca de un libro concreto
     * @param bookId Identificador del libro del que queremos saber cuántas copias tenemos
     * @return Devuelve la cantidad de copias que hay del libro
     */
    public int numCopies(String bookId) {

        // Primero recuperamos el iterador de catalogedBooks
        Iterator<CatalogedBook> iterator = catalogedBooks.values();

        // Luego recorremos la lista de libros buscando por isbn
        while(iterator.hasNext()) {
            CatalogedBook catalogedBook = iterator.next();
            if(catalogedBook.getBookId().equals(bookId)) {
                return catalogedBook.numCopies();
            }
        }
        return 0;
    }


    /***
     * Función que devuelve la cantidad de libros que se han prestado en total
     * @return Devuelve la cantidad de libros prestados
     */
    public int numLoans() {
        return loans.size();
    }


    /***
     * Función que devuelve el número de préstamos ABIERTOS gestionados por un trabajador
     * @param workerId Identificador del trabajador
     * @return El número de préstamos que ha gestionado
     */
    public int numLoansByWorker(String workerId) {

        for(Worker worker : workers) {
            if(worker.getId().equals(workerId)) {
                return worker.getOpenLoans().size();
            }
        }
        return 0;
    }


    /***
     * Función que devuelve el número de copias prestadas de un libro
     * @param bookId Identificador del libro
     * @return El número de copias que hay prestadas
     */
    public int numLoansByBook(String bookId) {

        // Primero recuperamos el iterador de catalogedBooks
        Iterator<CatalogedBook> iterator = catalogedBooks.values();

        // Luego recorremos la lista de libros buscando por isbn
        while(iterator.hasNext()) {
            CatalogedBook catalogedBook = iterator.next();
            if(catalogedBook.getBookId().equals(bookId)) {
                return catalogedBook.getLoans().size();
            }
        }
        return 0;
    }


    /***
     * Función que devuelve el número de préstamos que tiene un lector
     * @param readerId Identificador del lector
     * @return El número de copias que tiene un lector en préstamo
     */
    public int numCurrentLoansByReader(String readerId) {

        int numCurrentLoans = 0;

        // Recorremos el array buscado al lector
        for(Reader reader : readers) {
            if(reader != null && reader.getId().equals(readerId)) {
                Loan[] readerLoans = reader.getConcurrentLoans();
                for(Loan loan : readerLoans) {
                    if(loan != null)
                    {
                        numCurrentLoans++;
                    }
                }
            }
        }
        return numCurrentLoans;
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


    /***
     * Función que busca en la colección de libros catalogados un libro por su ID
     * @param bookId Identificador del libro que estamos buscando
     * @return Devuelve la información del libro si lo encuentra
     */
    public CatalogedBook getBookById(String bookId) {
        // Primero recuperamos el iterador de catalogedBooks
        Iterator<CatalogedBook> iterator = catalogedBooks.values();

        // Luego recorremos la lista de libros buscando por isbn
        while(iterator.hasNext()) {
            CatalogedBook catalogedBook = iterator.next();
            if(catalogedBook.getBookId().equals(bookId)) {
                return catalogedBook;
            }
        }
        return null;
    }


    /***
     * Función que recupera la cantidad de libros que tiene prestados de forma simultánea un lector. Se saca del
     * atributo concurrentLoans del lector
     * @param readerId Identificador del lector
     * @return Devuelve la cantidad de préstamos simultáneos del lector
     */
    public int getConcurrentLoansByReader(String readerId) {

        // Recorremos el array buscado al lector
        for(Reader reader : readers) {
            if(reader != null && reader.getId().equals(readerId)) {

                // Recuperamos el array dónde guardamos los préstamos simultáneos
                Loan[] concurrentLoans = reader.getConcurrentLoans();

                int totalConcurrentLoans = 0;

                for(Loan loan : concurrentLoans) {
                    if(loan != null) {
                        totalConcurrentLoans++;
                    }
                }

                return totalConcurrentLoans;
            }
        }

        return 0;
    }


    /***
     * Función que se usa para incrementar el número de préstamos del lector
     * @param loan Es el nuevo préstamo que se lleva el lector
     */
    public void increaseLoanReaderCount(Loan loan) {

        // Recorremos el array buscando al lector
        for (int i = 0; i < readers.length; i++) {
            if(readers[i] != null && readers[i].getId().equals(loan.getReaderId())) {
                // Añadimos el préstamo al lector
                readers[i].addNewLoan(loan);
                return;
            }
        }
    }


    /***
     * Función que se usa para incrementar el número de préstamos abiertos por un trabajador
     * @param loan Es el nuevo préstamo abierto
     */
    public void increaseOpenLoanWorkerCount(Loan loan) {

        // Recorremos el array buscando al trabajador
        for (int i = 0; i < workers.length; i++) {
            if(workers[i] != null && workers[i].getId().equals(loan.getWorkerId())) {
                // Añadimos el préstamo al trabajador
                workers[i].addLoanToOpenLoans(loan);
                return;
            }
        }
    }


    /***
     * Función que se usa para incrementar el número global de préstamos de la biblioteca
     * @param loan Es el nuevo préstamo abierto
     */
    public void incrementTotalLoans(Loan loan) {
        loans.insertEnd(loan);
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
