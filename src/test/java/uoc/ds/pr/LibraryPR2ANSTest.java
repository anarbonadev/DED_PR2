package uoc.ds.pr;

import edu.uoc.ds.traversal.Iterator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.Book;
import uoc.ds.pr.model.CatalogedBook;
import uoc.ds.pr.model.Loan;
import uoc.ds.pr.model.Reader;
import uoc.ds.pr.util.BookWareHouseTest;
import uoc.ds.pr.util.BooksData;
import uoc.ds.pr.util.DateUtils;

import static uoc.ds.pr.Library.MAX_BOOK_STACK;
import static uoc.ds.pr.util.BooksDataHelper.addBooksData;

public class LibraryPR2ANSTest {

    protected Library theLibrary;

    @Before
    public void setUp() throws Exception {
        this.theLibrary = FactoryLibrary.getLibrary();
        Assert.assertEquals(17, this.theLibrary.numReaders());
    }

    @After
    public void tearDown() {
        this.theLibrary = null;
    }

    /***
     * Esta prueba valida que el sistema permite encolar libros de manera masiva
     */
    @Test
    public void massStorageTest(){

        // El enunciado dice que el número total de libros es desconocido y relativamente pequeño, de unas centenas.
        // Llegamos hasta 1000, así son 10 centenas.
        int numBooks = 1000;
        for (int i = 0; i < numBooks; i++) {
            theLibrary.storeBook("JV7d", "The Steam House", "Forgotten Books", "First Edition"
                    , 1880, "978-1605062234", "Jules Verne", "Adventures");
        }

        Assert.assertEquals(numBooks/MAX_BOOK_STACK, theLibrary.numStacks());
        Assert.assertEquals(numBooks, theLibrary.numBooks());

    }


    /***
     * Esta prueba valida que cuando se hayan catalogado todos los libros que hay en la cola, si se trata de catalogar
     * uno más, salte la excepción NoBookException
     */
    @Test
    public void noBooksRemainingTest() throws DSException {

        storeBookTest();

        Assert.assertEquals(14, theLibrary.numStacks());
        Assert.assertEquals(131, theLibrary.numBooks());

        for (int i = 0; i < 131; i++) {
            theLibrary.catalogBook("workerId1");
        }

        Assert.assertEquals(0, theLibrary.numStacks());
        Assert.assertEquals(0, theLibrary.numBooks());

        Assert.assertThrows(NoBookException.class, () ->
                theLibrary.catalogBook("workerId1"));

    }


    /***
     * Esta prueba valida que cuando se hayan catalogado todos los libros que hay en la cola, si se trata de catalogar
     */
    @Test
    public void bookLoanAvailabilityTest() throws DSException {

        catalogBookTest();

        Assert.assertEquals(2, theLibrary.numCopies("JV2c"));

        theLibrary.lendBook("LOAN1", "readerId1", "JV2c", "workerId1",
                DateUtils.createLocalDate("01-11-2024"), DateUtils.createLocalDate("15-11-2024"));
        Assert.assertEquals(1, theLibrary.numCopies("JV2c"));

        theLibrary.lendBook("LOAN2", "readerId1", "JV2c", "workerId1",
                DateUtils.createLocalDate("01-11-2024"), DateUtils.createLocalDate("15-11-2024"));
        Assert.assertEquals(0, theLibrary.numCopies("JV2c"));

        Assert.assertThrows(NoBookException.class, () ->
                theLibrary.lendBook("LOAN3", "readerId1", "JV2c", "workerId1",
                DateUtils.createLocalDate("01-11-2024"), DateUtils.createLocalDate("15-11-2024")));

    }


    /***
     * Esta prueba valida que el flujo completo de préstamo y devolución de los libros
     */
    @Test
    public void completeBookLoanCycleTest() throws DSException {

        catalogBookTest();

        Assert.assertEquals(2, theLibrary.numCopies("JV2c"));

        // Hay 2 copias diponibles del libro JV2c, las voy a prestar
        for (int i = 1; i < 3; i++) {
            theLibrary.lendBook("LOAN" + i, "readerId" + i, "JV2c", "workerId" + i,
                DateUtils.createLocalDate("01-11-2024"), DateUtils.createLocalDate("15-11-2024"));
        }

        // Luego comprobaré que no hayan disponibles y si se trata de prestar otra dará error
        Assert.assertEquals(0, theLibrary.numCopies("JV2c"));

        // Luego devolveremos 1 de las copias y comprobaremos que hay 1 copia disponible
        theLibrary.giveBackBook("LOAN2", DateUtils.createLocalDate("10-11-2024"));
        Assert.assertEquals(1, theLibrary.numCopies("JV2c"));

        // Luego devolveremos la otra copia y comprobaremos que hay 2 copias disponibles
        theLibrary.giveBackBook("LOAN1", DateUtils.createLocalDate("10-11-2024"));
        Assert.assertEquals(2, theLibrary.numCopies("JV2c"));

        // Pruebo a devolver 2 veces el mismo préstamo
        Assert.assertThrows(LoanNotFoundException.class, () ->
                theLibrary.giveBackBook("LOAN1",  DateUtils.createLocalDate("10-11-2024")));

    }

    /******************************************************************************************************************/
    /******************************************* AUX METHODS **********************************************************/
    /******************************************************************************************************************/

    public void catalogBookTest() throws DSException {

        storeBookTest();

        CatalogedBook b = null;
        b = theLibrary.catalogBook("workerId1");

        b = theLibrary.catalogBook("workerId2");

        b = theLibrary.catalogBook("workerId1");

        b = theLibrary.catalogBook("workerId2");

        b = theLibrary.catalogBook("workerId2");

        b = theLibrary.catalogBook("workerId2");

        b = theLibrary.catalogBook("workerId2");

        b = theLibrary.catalogBook("workerId1");

        b = theLibrary.catalogBook("workerId3");

        b = theLibrary.catalogBook("workerId3");

    }

    public void storeBookTest() {

        addBooksData(theLibrary, BooksData.booksData1);
        addBooksData(theLibrary, BooksData.booksData2);
        addBooksData(theLibrary, BooksData.booksData3);
        addBooksData(theLibrary, BooksData.booksData4);
        addBooksData(theLibrary, BooksData.booksData5);
        addBooksData(theLibrary, BooksData.booksData6);
        addBooksData(theLibrary, BooksData.booksData7);

        theLibrary.storeBook("FK1", "Die Verwandlung", "Die Weißen Blätter ",
                "First Edition", 1915, "978-84-8256-840-9", "Frank Kafka", "existentialism");
    }
}