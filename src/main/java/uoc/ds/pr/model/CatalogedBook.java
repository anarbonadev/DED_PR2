package uoc.ds.pr.model;

/*
* Clase que representa a un libro catalogado.
* Hereda de la clase Book y añade los atributos específicos para gestionar el préstamo.
* */
public class CatalogedBook extends Book {

    // Protected attributes
    protected int totalCopies;      // Cantidad de ejemplares totales
    protected int availableCopies;  // Cantidad de ejemplares disponibles

    // Este atributo no está en la PEC1, pero creo que es necesario. Nos sirve para saber qué trabajador fue
    // el primero en catalogar un libro
    protected String  idWorker;

    // Constructor
    public CatalogedBook(String bookId, String title, String publisher, String edition, int publicationYear
            , String isbn, String author, String theme, int totalCopies, int availableCopies, String idWorker) {
        super(bookId, title, publisher, edition, publicationYear, isbn, author, theme);
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.idWorker = idWorker;
    }

    // Getters & Setters
    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(String idWorker) {
        this.idWorker = idWorker;
    }
}
