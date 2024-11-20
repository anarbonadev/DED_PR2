package uoc.ds.pr.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.model.Book;

import static uoc.ds.pr.Library.MAX_BOOK_STACK;

public class BookWareHouseANSTest {


    private BookWareHouse bookWareHouse;

    @Before
    public void setUp() {
        bookWareHouse = new BookWareHouse();
    }

    /***
     * Esta prueba valida que no se permita catalogar un libro si la cola está vacía. Si la cola está vacía,
     * el método getBookPendingCataloging() devuelve NULL
     */
    @Test
    public void catalogingEmptyWareHouseTest()
    {
        Assert.assertNull("Devuelve null", bookWareHouse.getBookPendingCataloging());
    }


    /***
     * Esta prueba valida que se permitan añadir libros repetidos a la cola
     */
    @Test
    public void duplicateBookStoreTest(){
        bookWareHouse.storeBook("JV7d", "The Steam House", "Forgotten Books", "First Edition"
                , 1880, "978-1605062234", "Jules Verne", "Adventures");
        Assert.assertEquals(1, bookWareHouse.numBooks());

        for (int i = 0; i < 10; i++) {
            bookWareHouse.storeBook("JV7d", "The Steam House", "Forgotten Books", "First Edition"
                    , 1880, "978-1605062234", "Jules Verne", "Adventures");
        }
        Assert.assertEquals(11, bookWareHouse.numBooks());
    }

    /***
     * Esta prueba valida que salte una excepción si se trata de recuperar la posición de un libro que no exíste en la cola
     */
    @Test
    public void getPositionNonExistingBookTest(){
        Assert.assertEquals(-1, bookWareHouse.getPosition("NonExistingBook").getNumStack());
    }

    /***
     * Esta prueba valida que se siga el orden de catalogacion esperado:
     *   - FIFO para sacar pilas de la cola.
     *   - LIFO para sacar libros de la pila.
     */
    @Test
    public void catalogingOrderTest(){
        prepareData();

        Book book1 = bookWareHouse.getBookPendingCataloging();
        Assert.assertEquals("JV2c", book1.getBookId());

        Book book2 = bookWareHouse.getBookPendingCataloging();
        Assert.assertEquals("JV2b", book2.getBookId());

        for (int i = 0; i < 20; i++) {
            bookWareHouse.getBookPendingCataloging();
        }

        Book book3 = bookWareHouse.getBookPendingCataloging();
        Assert.assertEquals("HP6a", book3.getBookId());

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
            bookWareHouse.storeBook("JV7d", "The Steam House", "Forgotten Books", "First Edition"
                    , 1880, "978-1605062234", "Jules Verne", "Adventures");
        }

        Assert.assertEquals(numBooks/MAX_BOOK_STACK, bookWareHouse.numStacks());
        Assert.assertEquals(numBooks, bookWareHouse.numBooks());
    }


    /***
     * Esta prueba valida que cuando se extrae el último libro de una pila, la pila se saca de la cola
     */
    @Test
    public void deleteOfStackIfEmpty(){
        prepareData();

        Assert.assertEquals(14, bookWareHouse.numStacks());

        // Catalogamos los 10 primeros libros, la primera pila debe desaparecer
        for (int i = 0; i < 10; i++) {
            bookWareHouse.getBookPendingCataloging();
        }

        Assert.assertEquals(13, bookWareHouse.numStacks());

    }


    /******************************************************************************************************************/
    /******************************************* AUX METHODS **********************************************************/
    /******************************************************************************************************************/

    public void prepareData() {
        Assert.assertEquals(0, bookWareHouse.numBooks());
        addBooksData(BooksData.booksData1);
        addBooksData(BooksData.booksData2);
        addBooksData(BooksData.booksData3);
        addBooksData(BooksData.booksData4);
        addBooksData(BooksData.booksData5);
        addBooksData(BooksData.booksData6);
        addBooksData(BooksData.booksData7);

        bookWareHouse.storeBook("FK1", "Die Verwandlung", "Die Weißen Blätter ",
                "First Edition", 1915, "978-84-8256-840-9", "Frank Kafka", "existentialism");
    }

    private void addBooksData(String[][] booksData) {
        Book book = null;
        for (String[] bookData: booksData) {
            book = new Book(bookData[0], bookData[1], bookData[2], bookData[3], Integer.parseInt(bookData[4]),
                    bookData[5], bookData[6], bookData[7]);
            bookWareHouse.storeBook(book);
        }
    }

}
