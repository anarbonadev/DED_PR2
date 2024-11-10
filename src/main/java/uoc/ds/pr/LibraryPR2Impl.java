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
    private Reader[] readers = new Reader[MAX_NUM_READERS];
    private Worker[] workers = new Worker[MAX_NUM_WORKERS];

    // Lista encadenada de libros catalogados
    LinkedList<CatalogedBook> catalogedBooks = new LinkedList<>();


    /***
     * BookWareHouse controla todo lo referente al sistema de recepción de libros, procesamiento, colas, pilas...
     */
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
            if(this.readers.length == MAX_NUM_READERS) {
                throw new MaxNumReachedException(bundle.getString("exception.MaxNumReaders"));
            }

            // Reader que tengo que insertar o actualizar
            Reader readerUpsert = new Reader(id, name, surname, docId, birthDate, birthPlace, address);

            // Compruebo si el lector ya está dando de alta
            Reader reader = getReader(id);

            if (reader != null) {
                // Añado una función que busque al reader dentro del array y actualice los datos
                reader = updateReader(readerUpsert);
            }
            else {
                // Añado el nuevo reader al array
                this.readers[this.readers.length - 1] = readerUpsert;
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
            if(this.workers.length == MAX_NUM_WORKERS) {
                throw new MaxNumReachedException(bundle.getString("exception.MaxNumWorkers"));
            }

            // Reader que tengo que insertar o actualizar
            Worker workerUpsert = new Worker(id, name, surname);

            // Compruebo si el lector ya está dando de alta
            Worker worker = getWorker(id);

            if (worker != null) {
                // Añado una función que busque al reader dentro del array y actualice los datos
                worker = incrementBooksCataloguedByWorkerId(workerUpsert);
            }
            else {
                // Añado el nuevo worker al array
                this.workers[this.workers.length - 1] = workerUpsert;
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

        // Si no hay ningún libro que catalogar se indicará un error
        if(this.bookWareHouse.isQueueEmpty()) {
            throw new NoBookException(bundle.getString("exception.NoBookException"));
        }

        // Si no exíste el trabajador se indicará un error
        if(getWorker(workerId) == null) {
            throw new WorkerNotFoundException(bundle.getString("exception.WorkerNotFoundException"));
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
            incrementBooksCataloguedByWorkerId(workerId);
        }

        return catalogedBookResult.getCatalogedBook();
    }

    @Override
    public void lendBook(String loanId, String readerId, String bookId, String workerId, LocalDate date, LocalDate expirationDate) throws ReaderNotFoundException, BookNotFoundException, WorkerNotFoundException, NoBookException, MaximumNumberOfBooksException {

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
        for(Reader reader : this.readers) {
            if(reader != null && reader.getId().equals(id)) {
                // Si se encuentra, devolvemos ese reader
                return reader;
            }
        }

        // Si no se encuentra, devolvemos un null
        return null;
    }

    /***
     * Función que devuelve el número de lectores registrados en la biblioteca
     * @return
     */
    @Override
    public int numReaders() {
        return this.readers.length;
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
        for(Worker worker : this.workers) {
            if(worker != null && worker.getId().equals(id)) {
                // Si se encuentra, devolvemos ese worker
                return worker;
            }
        }

        // Si no se encuentra, devolvemos un null
        return null;
    }

    @Override
    public int numWorkers() {
        return this.workers.length;
    }

    //
    // Books warehouse
    //
    @Override
    public int numBooks() {
        return 0;
    }

    @Override
    public int numStacks() {
        return 0;
    }

    @Override
    public int numCatalogBooks() {
        return 0;
    }

    @Override
    public int numCatalogBooksInWorker(String workerId) {
        return 0;
    }

    @Override
    public int totalCatalogBooksByWorker(String workerId) {
        return 0;
    }

    @Override
    public int numCopies(String bookId) {
        return 0;
    }

    @Override
    public int numLoans() {
        return 0;
    }

    @Override
    public int numLoansByWorker(String workerId) {
        return 0;
    }

    @Override
    public int numLoansByBook(String bookId) {
        return 0;
    }

    @Override
    public int numCurrentLoansByReader(String readerId) {
        return 0;
    }

    @Override
    public int numClosedLoansByWorker(String workerId) {
        return 0;
    }

    @Override
    public int numClosedLoansByReader(String readerId) {
        return 0;
    }

    /***********************************************************************************/
    /******************** PRIVATE OPERATIONS *******************************************/
    /***********************************************************************************/

    /***
     * Función que busca un lector dentro del array readers y una vez encontrado actualiza sus valores con los
     * que recibe en el parámetro readerUpsert
     * @param readerUpsert Objeto que contiene los datos del lector que estamos actualizando
     * @return Devuelve el propio lector con los datos actualizados
     */
    private Reader updateReader(Reader readerUpsert) {
        // Recorremos el array buscando el reader a actualizar
        for(int i = 0 ; i < MAX_NUM_READERS; i++) {
            if(this.readers[i] != null && this.readers[i].getId().equals(readerUpsert.getId())) {
                this.readers[i].setName(readerUpsert.getName());
                this.readers[i].setSurname(readerUpsert.getSurname());
                this.readers[i].setDocId(readerUpsert.getDocId());
                this.readers[i].setBirthDate(readerUpsert.getBirthDate());
                this.readers[i].setBirthPlace(readerUpsert.getBirthPlace());
                this.readers[i].setAddress(readerUpsert.getAddress());

                return readerUpsert;
            }
        }
        return readerUpsert;
    }

    /***
     * Función que busca un trabajadnor dentro del array workers y una vez encontrado actualiza sus valores con los
     * que recibe en el parámetro workerUpsert
     * @param workerUpsert Objeto que contiene los datos del trabajador que estamos actualizando
     * @return Devuelve el propio trabajador con los datos actualizados
     */
    private Worker incrementBooksCataloguedByWorkerId(Worker workerUpsert) {
        // Recorremos el array buscando el worker a actualizar
        for(int i = 0 ; i < MAX_NUM_WORKERS; i++) {
            if(this.workers[i] != null && this.workers[i].getId().equals(workerUpsert.getId())) {
                this.workers[i].setName(workerUpsert.getName());
                this.workers[i].setSurname(workerUpsert.getSurname());

                return workerUpsert;
            }
        }
        return workerUpsert;
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
        Iterator<CatalogedBook> iterator = this.catalogedBooks.values();

        // Luego recorremos la lista de libros buscando por isbn
        while(iterator.hasNext()) {
            CatalogedBook catalogedBook = iterator.next();
            if(catalogedBook.getIsbn().equals(bookToCatalog.getIsbn())) {
                // Si encuentro el libro actualizamos los valores totalCopies y availableCopies
                catalogedBook.setTotalCopies(catalogedBook.getTotalCopies() + 1);
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
        this.catalogedBooks.insertEnd(catalogedBook);

        catalogedBookResult = new CatalogedBookResult(false, catalogedBook);

        return catalogedBookResult;
    }


    /***
     * Función que incrementa el atributo quantityOfBooksCatalogued del trabajador que ha catalogado por
     * primera vez un libro
     * @param workerId Es el indentificador único del trabajador
     */
    private void incrementBooksCataloguedByWorkerId(String workerId){
        for (int i = 0; i < workers.length; i++) {
            Worker worker = this.workers[i];
            if(worker.getId().equals(workerId)) {
                this.workers[i].incrementQuantityOfBooksCatalogued();
            }
        }
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
