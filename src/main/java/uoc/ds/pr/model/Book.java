package uoc.ds.pr.model;

/*
* Clase que representa a un libro, con los atributos básicos
* */
public abstract class Book {

    // Protected attributes
    protected int bookId;           // Identificador
    protected String isbn;          // ISBN
    protected String title;         // Título
    protected String editorial;     // Editorial
    protected int publicationYear;  // Año de publicación
    protected int edition;          // Número de edición
    protected String author;        // Autor
    protected String theme;         // Temática

    // Constructor
    public Book(int bookId, String isbn, String title, String editorial, int publicationYear, int edition, String author, String theme) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.editorial = editorial;
        this.publicationYear = publicationYear;
        this.edition = edition;
        this.author = author;
        this.theme = theme;
    }

    // Getters & Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
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