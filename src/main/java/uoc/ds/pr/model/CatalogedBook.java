package uoc.ds.pr.model;

/*
* Clase que representa a un libro catalogado.
* Hereda de la clase Book y añade los atributos específicos para gestionar el préstamo.
* */
public class CatalogedBook extends Book {

    /*
    * Clase Libro catalogado: subclase de Libro: campos extra: #cantidad de ejemplares totales y #cantidad
    * de ejemplares disponibles.
    * */

    // Protected attributes
    protected int totalCopies;      // Cantidad de ejemplares totales
    protected int availableCopies;  // Cantidad de ejemplares disponibles

    // Constructor
    public CatalogedBook(int bookId, String isbn, String title, String editorial, int publicationYear, int edition, String author, String theme) {
        super(bookId, isbn, title, editorial, publicationYear, edition, author, theme);
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
}
