package uoc.ds.pr.model;

/*
* Clase que representa a un libro, con los atributos básicos
* */
public class Book {

    // Protected attributes
    private String bookId;        // Identificador
    private String title;         // Título
    private String publisher;     // Editorial
    private String edition;          // Número de edición
    private int publicationYear;  // Año de publicación
    private String isbn;          // ISBN
    private String author;        // Autor
    private String theme;         // Temática

    // Constructor
    public Book(String bookId, String title, String publisher, String edition, int publicationYear, String isbn, String author, String theme) {
        this.bookId = bookId;
        this.title = title;
        this.publisher = publisher;
        this.edition = edition;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.author = author;
        this.theme = theme;
    }

    // Getters & Setters

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}