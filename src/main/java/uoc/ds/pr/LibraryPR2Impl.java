package uoc.ds.pr;

import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;
import uoc.ds.pr.util.BookWareHouse;


public class LibraryPR2Impl implements Library {

    /***
     * Los mensajes de error indicados los recuperamos del fichero error_messages.properties
     */
    private static final ResourceBundle bundle = ResourceBundle.getBundle("error_messages");

    /***
     * Attributes
     */

    // BookWareHouse controla todo lo referente al sistema de recepción de libros, procesamiento, colas, pilas...
    private BookWareHouse bookWareHouse;

    /***
     * Constructor
     */
    public LibraryPR2Impl() {
        this.bookWareHouse = new BookWareHouse();
    }


    /***
     * Función que usamos para registrar un nuevo lector
     * @param id Identificador
     * @param name Nombre
     * @param surname Apellidos
     * @param docId DNI
     * @param birthDate Fecha de nacimiento
     * @param birthPlace Lugar de nacimiento
     * @param address Dirección
     */
    @Override
    public void addReader(String id, String name, String surname, String docId, LocalDate birthDate, String birthPlace, String address) {

        try {

            // Compruebo si hemos llegado a la capacidad máxima del array de lectores
            if(isReadersArrayFull()) {
                throw new MaxNumReachedException(bundle.getString("exception.MaxNumReaders"));
            }

            // Reader que tengo que insertar o actualizar
            Reader readerUpsert = new Reader(id, name, surname, docId, birthDate, birthPlace, address);

            // Compruebo si el lector ya está dando de alta
            Reader reader = getReader(id);

            if (reader != null) {
                // Añado una función que busque al reader dentro del array y actualice los datos
                updateReader(readerUpsert);
            }
            else {
                // Añado el nuevo reader al array
                insertReader(readerUpsert);
            }

        } catch (MaxNumReachedException e){
            throw new RuntimeException(e);
        }
    }

    /***
     * Función que usamos para registrar un nuevo lector
     * @param id Identificador
     * @param name Nombre
     * @param surname Apellido
     */
    @Override
    public void addWorker(String id, String name, String surname) {

        try {

            // Compruebo si hemos llegado a la capacidad máxima del array de lectores
            if(isWorkersArrayFull()) {
                throw new MaxNumReachedException(bundle.getString("exception.MaxNumWorkers"));
            }

            // Worker que tengo que insertar o actualizar
            Worker workerUpsert = new Worker(id, name, surname);

            // Compruebo si el lector ya está dando de alta
            Worker worker = getWorker(id);

            if (worker != null) {
                // Añado una función que busque al reader dentro del array y actualice los datos
                updateWorkerQuantityOfBooksCatalogued(workerUpsert);
            }
            else {
                // Añado el nuevo worker al array
                insertWorker(workerUpsert);
            }

        } catch (MaxNumReachedException e){
            throw new RuntimeException(e);
        }

    }

    /***
     * Función que usamos para almacenar un nuevo libro
     * @param bookId Identificador del libro
     * @param title Título
     * @param publisher Editorial
     * @param edition Edición
     * @param publicationYear Año de publicación
     * @param isbn ISBN
     * @param author Autor
     * @param theme Temática
     */
    @Override
    public void storeBook(String bookId, String title, String publisher, String edition, int publicationYear, String isbn, String author, String theme) {
        // Llamo a la función storeBook del bookWareHouse, que se encarga de las gestiones de la biblioteca
        bookWareHouse.storeBook(bookId, title, publisher, edition, publicationYear, isbn, author, theme);
    }


    /***
     * Función que se usa para que un trabajador catalogue un libro
     * @param workerId Es el identificador del trabajador
     * @return Devuelve un libro de tipo CatalogedBook
     * @throws NoBookException Excepción que salta si no hay ningún libro pendiente de catalogar
     * @throws WorkerNotFoundException Excepción que salta si no exíste el trabajador indicado
     */
    @Override
    public CatalogedBook catalogBook(String workerId) throws NoBookException, WorkerNotFoundException {

        // Si no exíste el trabajador se indicará un error
        if(getWorker(workerId) == null) {
            throw new WorkerNotFoundException(bundle.getString("exception.WorkerNotFoundException"));
        }

        // Si no hay ningún libro que catalogar se indicará un error
        if(this.bookWareHouse.isQueueEmpty()) {
            throw new NoBookException(bundle.getString("exception.NoBookException"));
        }

        // Extraemos el primero libro de la primera cola
        Book bookToCatalog = this.bookWareHouse.getBookPendingCataloging();

        // Comprobamos si el libro exíste o no en la Linked List de libros catalogados
        // Si existe el libro, actualizamos sus valores totalCopies + 1 y availableCopies + 1
        // Si no existe, lo agregamos al final
        CatalogedBookResult catalogedBookResult = this.findAndUpdateBookByIsbn(bookToCatalog, workerId);

        // Si el libro ya existía en la colección de libros catalogados, sea el trabajador actual que catalogó
        // el libro la primera vez o no, no se incrementa la cantidad de libros catalogados por el trabajador

        // Si no se encontró en el catálogo, es la primera vez que se añade y se suma al total de libros
        // catalogados por el trabajador
        if(!catalogedBookResult.getFound()) {

            // Añadimos el libro a la lista de libros catalogados por el trabajador
            addToWorkerCatalog(workerId, catalogedBookResult.getCatalogedBook());
        }

        // En ambos casos, añadimos el libro a los procesados por el trabajador, aunque no le compute como procesado
        addBookToProcessedByWorker(workerId, bookToCatalog);

        return catalogedBookResult.getCatalogedBook();
    }


    /***
     * Función que se usa para prestar un libro a un lector
     * @param loanId Identificador del préstamo
     * @param readerId Identificador del léctor
     * @param bookId Identificador del libro que se está prestando
     * @param workerId Identificador del trabajador que gestiona el préstamo
     * @param date La fecha en la que se realiza el préstamo
     * @param expirationDate La fecha final de devolución
     * @throws ReaderNotFoundException Excepción que salta si no existe el lector
     * @throws BookNotFoundException Excepción que salta si no existe el libro
     * @throws WorkerNotFoundException Excepción que salta si no exíste el trabajador indicado
     * @throws NoBookException Excepción que salta si no hay ningún libro pendiente de catalogar
     * @throws MaximumNumberOfBooksException Excepción que salta si el lector tiene ya 3 libros en préstamo
     */
    @Override
    public void lendBook(String loanId, String readerId, String bookId, String workerId, LocalDate date, LocalDate expirationDate) throws ReaderNotFoundException, BookNotFoundException, WorkerNotFoundException, NoBookException, MaximumNumberOfBooksException {

        // Si el lector no existe se indicará un error
        if(getWorker(readerId) == null) {
            throw new ReaderNotFoundException(bundle.getString("exception.ReaderNotFoundException"));
        }

        // Si el libro no existe se indicará un error

        // Si no exíste el trabajador se indicará un error
        if(getWorker(workerId) == null) {
            throw new WorkerNotFoundException(bundle.getString("exception.WorkerNotFoundException"));
        }

        // Si no hay ningún libro que catalogar se indicará un error
        if(this.bookWareHouse.isQueueEmpty()) {
            throw new NoBookException(bundle.getString("exception.NoBookException"));
        }

        // Si el lector ya tiene tres libros en préstamo se indicará un error



    }

    @Override
    public Loan giveBackBook(String loanId, LocalDate date) throws LoanNotFoundException {
        return null;
    }

    @Override
    public int timeToBeCataloged(String bookId, int lotPreparationTime, int bookCatalogTime) throws BookNotFoundException, InvalidLotPreparationTimeException, InvalidCatalogTimeException {
        return 0;
    }

    @Override
    public Iterator<Loan> getAllLoansByReader(String readerId) throws NoLoansException {
        return null;
    }

    @Override
    public Iterator<Loan> getAllLoansByState(String readerId, LoanState state) throws NoLoansException {
        return null;
    }

    @Override
    public Iterator<Loan> getAllLoansByBook(String bookId) throws NoLoansException {
        return null;
    }

    @Override
    public Reader getReaderTheMost() throws NoReaderException {
        return null;
    }

    @Override
    public Book getMostReadBook() throws NoBookException {
        return null;
    }


    /***********************************************************************************/
    /******************** AUX OPERATIONS  **********************************************/
    /***********************************************************************************/

    /***
     * Función que busca en el array de lectores si existe un lector dado un valor de id
     * @param id El identificador del lector que estamos buscando
     * @return Si encuentra un lector con dicho identificador, devuelve el lector,
     * en caso contrario, devuelve un null
     */
    @Override
    public Reader getReader(String id) {
        // Recorremos el array buscado si existe un reader con el id dado
        for(Reader reader : BookWareHouse.readers) {
            if(reader != null && reader.getId().equals(id)) {
                // Si se encuentra, devolvemos ese reader
                return reader;
            }
        }

        // Si no se encuentra, devolvemos un null
        return null;
    }

    /***
     * Función que devuelve el número de lectores registrados en la biblioteca. Recorremos el array de lectores
     * y las posiciones que no sean nulas suman al total de lectores
     * @return Devuelve el número de lectores registrados en la biblioteca
     */
    @Override
    public int numReaders() {
        int totalReaders = 0;

        for(Reader reader : BookWareHouse.readers) {
            if(reader != null) {
                totalReaders++;
            }
        }

        return totalReaders;
    }

    /***
     * Función que busca en el array de trabajadores si existe un trabajador dado un valor de id
     * @param id El identificador del trabajador que estamos buscando
     * @return Si encuentra un trabajador con dicho identificador, devuelve el trabajador,
     * en caso contrario, devuelve un null
     */
    @Override
    public Worker getWorker(String id) {
        // Recorremos el array buscado si existe un reader con el id dado
        for(Worker worker : BookWareHouse.workers) {
            if(worker != null && worker.getId().equals(id)) {
                // Si se encuentra, devolvemos ese worker
                return worker;
            }
        }

        // Si no se encuentra, devolvemos un null
        return null;
    }

    /***
     * Función que devuelve el número de trabajadores de la biblioteca. Recorremos el array de trabajadores
     * y las posiciones que no sean nulas suman al total de trabajadores
     * @return Devuelve el número de trabajadores de la biblioteca
     */
    @Override
    public int numWorkers() {
        int totalWorkers = 0;

        for(Worker worker : BookWareHouse.workers) {
            if(worker != null) {
                totalWorkers++;
            }
        }

        return totalWorkers;
    }

    //
    // Books warehouse
    //

    /***
     * Función que cuenta la cantidad de libros total que hay en la cola, sumando los libros de todas las pilas
     * @return Devuelve el número toal de libros
     */
    @Override
    public int numBooks() {
        return this.bookWareHouse.numBooks();
    }

    /***
     * Función que cuenta la cantidad de pilas totales que hay en la cola.
     * @return Devuelve el número total de pilas
     */
    @Override
    public int numStacks() {
        return this.bookWareHouse.numStacks();
    }

    /***
     * Función que devuelve la cantidad total de libros catalogados
     * @return Devuelve la cantidad total de libros catalogados
     */
    @Override
    public int numCatalogBooks() {
        return this.bookWareHouse.numCatalogBooks();
    }

    /***
     * Función que devuelve la cantidad de libros catalogados por un trabajador. Se consideran catalogados por un
     * trabajador cuando es la primera vez que se cataloga un libro.
     *      Si un segundo trabajador cataloga un libro que ya fue catalogado por otro trabajador, no cuenta
     * @param workerId Identificador del trabajador
     * @return Devuelve la cantidad total de libros catalogados por el trabajador
     */
    @Override
    public int numCatalogBooksInWorker(String workerId) {
        return this.bookWareHouse.numCatalogBooksInWorker(workerId);
    }

    /***
     * Función que devuelve la cantidad TOTAL de libros procesados por un trabajador, le cuente o no como libro
     * catalogado
     * @param workerId Identificador del trabajador
     * @return Devuelve la cantidad total de libros procesados por el trabajador
     */
    @Override
    public int totalCatalogBooksByWorker(String workerId) {
        return this.bookWareHouse.totalCatalogBooksByWorker(workerId);
    }

    /***
     * Función que devuelve la cantidad total de copias que hay en la biblioteca de un libro concreto
     * @param bookId Identificador del libro del que queremos saber cuántas copias tenemos
     * @return Devuelve la cantidad de copias que hay del libro
     */
    @Override
    public int numCopies(String bookId) {
        return this.bookWareHouse.numCopies(bookId);
    }


    /***
     * Función que devuelve la cantidad de libros que se han prestado en total
     * @return Devuelve la cantidad de libros prestados
     */
    @Override
    public int numLoans() {
        return this.bookWareHouse.numLoans();
    }


    /***
     * Función que devuelve el número de préstamos gestionados por un trabajador
     * @param workerId Identificador del trabajador
     * @return El número de préstamos que ha gestionado
     */
    @Override
    public int numLoansByWorker(String workerId) {
        return this.bookWareHouse.numLoansByWorker(workerId);
    }


    /***
     * Función que devuelve el número de copias prestadas de un libro
     * @param bookId Identificador del libro
     * @return El número de copias que hay prestadas
     */
    @Override
    public int numLoansByBook(String bookId) {
        return this.bookWareHouse.numLoansByBook(bookId);
    }


    /***
     * Función que devuelve el número de préstamos que tiene un lector
     * @param readerId Identificador del lector
     * @return El número de copias que tiene un lector en préstamo
     */
    @Override
    public int numCurrentLoansByReader(String readerId) {
        return this.bookWareHouse.numCurrentLoansByReader(readerId);
    }


    /***
     * Función que devuelve la cantidad de préstamos que han sido cerrados por un trabajador
     * @param workerId Identificador del trabajador
     * @return
     */
    @Override
    public int numClosedLoansByWorker(String workerId) {
        return this.bookWareHouse.numClosedLoansByWorker(workerId);
    }


    /***
     * Función que devuelve la cantidad de préstamos que han sido cerrados por un lector
     * @param readerId
     * @return
     */
    @Override
    public int numClosedLoansByReader(String readerId) {
        return this.bookWareHouse.numClosedLoansByReader(readerId);
    }

    /***********************************************************************************/
    /******************** PRIVATE OPERATIONS *******************************************/
    /***********************************************************************************/

    /***
     * Función que usamos para comprobar si hemos llegado al máximo valor de lectores que podemos dar de alta en
     * la biblioteca
     * @return Devuelve false si no hemos llegado al máximo, en caso contrario devuelve true
     */
    private boolean isReadersArrayFull(){
        int size = 0;

        for (Reader reader : BookWareHouse.readers) {
            if(reader != null){
                size++;
            }
        }

        if(size < MAX_NUM_READERS){
            return false;
        }
        return true;
    }


    /***
     * Función que busca un lector dentro del array readers y una vez encontrado actualiza sus valores con los
     * que recibe en el parámetro readerUpsert
     * @param readerUpsert Objeto que contiene los datos del lector que estamos actualizando
     * @return Devuelve el propio lector con los datos actualizados
     */
    private void updateReader(Reader readerUpsert) {
        // Recorremos el array buscando el reader a actualizar
        for(int i = 0 ; i < BookWareHouse.readers.length; i++) {
            if(BookWareHouse.readers[i] != null && BookWareHouse.readers[i].getId().equals(readerUpsert.getId())) {
                BookWareHouse.readers[i].setName(readerUpsert.getName());
                BookWareHouse.readers[i].setSurname(readerUpsert.getSurname());
                BookWareHouse.readers[i].setDocId(readerUpsert.getDocId());
                BookWareHouse.readers[i].setBirthDate(readerUpsert.getBirthDate());
                BookWareHouse.readers[i].setBirthPlace(readerUpsert.getBirthPlace());
                BookWareHouse.readers[i].setAddress(readerUpsert.getAddress());

            }
        }
    }


    /***
     * Función que da de alta un nuevo reader en la colección de readers de la biblioteca
     * @param readerUpsert Es el reader que tenemos que dar de alta
     */
    private void insertReader(Reader readerUpsert) {

        // Como es un lector nuevo, inicializamos ciertos valores
        LinkedList<Loan> loans = new LinkedList<>();
        readerUpsert.setLoans(loans);

        Loan[] concurrentLoans = new Loan[MAXIMUM_NUMBER_OF_BOOKS];
        readerUpsert.setConcurrentLoans(concurrentLoans);

        // Recorremos el array buscando el primer hueco dónde insertar el reader
        for(int i = 0 ; i < BookWareHouse.readers.length; i++) {
            if(BookWareHouse.readers[i] == null){
                BookWareHouse.readers[i] = readerUpsert;
                break;
            }
        }
    }


    /***
     * Función que usamos para comprobar si hemos llegado al máximo valor de workers que podemos dar de alta en
     * la biblioteca
     * @return Devuelve false si no hemos llegado al máximo, en caso contrario devuelve true
     */
    private boolean isWorkersArrayFull(){
        int size = 0;

        for (Worker worker : BookWareHouse.workers) {
            if(worker != null){
                size++;
            }
        }

        if(size < MAX_NUM_WORKERS){
            return false;
        }
        return true;
    }


    /***
     * Función que busca un trabajador dentro del array workers y una vez encontrado actualiza sus valores con los
     * que recibe en el parámetro workerUpsert
     * @param workerUpsert Objeto que contiene los datos del trabajador que estamos actualizando
     * @return Devuelve el propio trabajador con los datos actualizados
     */
    private void updateWorkerQuantityOfBooksCatalogued(Worker workerUpsert) {
        // Recorremos el array buscando el worker a actualizar
        for(int i = 0 ; i < BookWareHouse.workers.length; i++) {
            if(BookWareHouse.workers[i] != null && BookWareHouse.workers[i].getId().equals(workerUpsert.getId())) {
                BookWareHouse.workers[i].setName(workerUpsert.getName());
                BookWareHouse.workers[i].setSurname(workerUpsert.getSurname());
            }
        }
    }


    /***
     * Función que da de alta un nuevo worker en la colección de workers de la biblioteca
     * @param workerUpsert Es el worker que tenemos que dar de alta
     */
    private void insertWorker(Worker workerUpsert) {
        // Recorremos el array buscando el primer hueco dónde insertar el reader
        for(int i = 0 ; i < BookWareHouse.workers.length; i++) {
            if(BookWareHouse.workers[i] == null){
                BookWareHouse.workers[i] = workerUpsert;
                break;
            }
        }
    }


    /***
     * Función que añade un nuevo libro a la lista de libros catalogados por el trabajador
      * @param workerId Identificador del trabajador
     * @param catalogedBook El libro recién catalogado
     */
    private void addToWorkerCatalog(String workerId, CatalogedBook catalogedBook)
    {
        // Buscamos el trabajador dentro del array
        for (int i = 0; i < BookWareHouse.workers.length; i++) {
            if(BookWareHouse.workers[i] != null && BookWareHouse.workers[i].getId().equals(workerId)) {
                BookWareHouse.workers[i].addToWorkerCatalog(catalogedBook);
                break;
            }
        }
    }



    /***
     * Función que busca dentro de la colección de libros catalogados, catalogedBook, el libro pendiente de catalogar.
     * Si el libro existe, incrementa en +1 los valores de totalCopies y availableCopies.
     * Si no existe, lo añade a la colección, con valores de totalCopies y availableCopies a 1
     * @param bookToCatalog Es el libro pendiente de ser catalogado
     * @return Devuelve el libro catalogado
     */
    private CatalogedBookResult findAndUpdateBookByIsbn(Book bookToCatalog, String workerId) {

        CatalogedBookResult catalogedBookResult = null;

        // Primero recuperamos el iterador de catalogedBooks
        Iterator<CatalogedBook> iterator = BookWareHouse.catalogedBooks.values();

        // Luego recorremos la lista de libros buscando por isbn
        while(iterator.hasNext()) {
            CatalogedBook catalogedBook = iterator.next();
            if(catalogedBook.getIsbn().equals(bookToCatalog.getIsbn())) {
                // Si encuentro el libro actualizamos los valores totalCopies y availableCopies
                catalogedBook.setTotalCopies(catalogedBook.numCopies() + 1);
                catalogedBook.setAvailableCopies(catalogedBook.getAvailableCopies() + 1);

                catalogedBookResult = new CatalogedBookResult(true, catalogedBook);

                return catalogedBookResult;
            }
        }

        // Si no se encuentra el libro, añadimos el libro a catalogedBooks
        CatalogedBook catalogedBook = new CatalogedBook(bookToCatalog.getBookId(), bookToCatalog.getTitle()
                , bookToCatalog.getPublisher(), bookToCatalog.getEdition(), bookToCatalog.getPublicationYear()
                , bookToCatalog.getIsbn(), bookToCatalog.getAuthor(), bookToCatalog.getTheme()
                , 1, 1, workerId);
        BookWareHouse.catalogedBooks.insertEnd(catalogedBook);

        catalogedBookResult = new CatalogedBookResult(false, catalogedBook);

        return catalogedBookResult;
    }


    /***
     * Función que añade un libro a la lista de libros procesados por un trabajador
     * @param workerId Identificador del trabajador
     * @param bookToCatalog El libro que ya procesado
     */
    private void addBookToProcessedByWorker(String workerId, Book bookToCatalog) {
        this.bookWareHouse.addBookToProcessedByWorker(workerId, bookToCatalog);
    }


    /***********************************************************************************/
    /******************** NESTED CLASSES ***********************************************/
    /***********************************************************************************/


    /***
     * Clase auxiliar que uso en la respuesta del método findAndUpdateBookByIsbn
     */
    private class CatalogedBookResult {

        // Atributos
        private Boolean found;
        private CatalogedBook catalogedBook;

        // Constructor
        public CatalogedBookResult(Boolean found, CatalogedBook catalogedBook) {
            this.found = found;
            this.catalogedBook = catalogedBook;
        }

        // Getters y Setters

        public Boolean getFound() {
            return found;
        }

        public void setFound(Boolean found) {
            this.found = found;
        }

        public CatalogedBook getCatalogedBook() {
            return catalogedBook;
        }

        public void setCatalogedBook(CatalogedBook catalogedBook) {
            this.catalogedBook = catalogedBook;
        }
    }

}
