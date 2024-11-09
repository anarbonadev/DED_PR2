package uoc.ds.pr.model;

/*
* Clase que representa a un libro almacenado.
* Hereda de la clase Book y no añade ningún atributo extra.
* */
public class StoredBook extends Book {
    // Constructor
    public StoredBook(int bookId, String isbn, String title, String editorial, int publicationYear, int edition, String author, String theme) {
        super(bookId, isbn, title, editorial, publicationYear, edition, author, theme);
    }
}
