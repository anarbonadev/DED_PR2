package uoc.ds.pr.model;

/*
* Clase que representa a un libro almacenado.
* Hereda de la clase Book y no añade ningún atributo extra.
* */
public class StoredBook extends Book {
    // Constructor
    public StoredBook(String bookId, String title, String publisher, String edition, int publicationYear, String isbn, String author, String theme) {
        super(bookId, title, publisher, edition, publicationYear, isbn, author, theme);
    }

    public StoredBook(Book book) {
        super(book.bookId, book.title, book.publisher, book.edition, book.publicationYear, book.isbn, book.author, book.theme);
    }
}
