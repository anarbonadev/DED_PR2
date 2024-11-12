package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;

/*
* Clase que representa a un libro catalogado.
* Hereda de la clase Book y añade los atributos específicos para gestionar el préstamo.
* */
public class CatalogedBook extends Book {

    // Protected attributes
    private int totalCopies;      // Cantidad de ejemplares totales
    private int availableCopies;  // Cantidad de ejemplares disponibles

    // Este atributo no está en la PEC1, pero creo que es necesario. Nos sirve para saber qué trabajador fue
    // el primero en catalogar un libro
    private String  idWorker;

    public LinkedList<Loan> loans;  // Lista de todos los préstamos de un libro

    // Constructor
    public CatalogedBook(String bookId, String title, String publisher, String edition, int publicationYear
            , String isbn, String author, String theme, int totalCopies, int availableCopies, String idWorker) {
        super(bookId, title, publisher, edition, publicationYear, isbn, author, theme);
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.idWorker = idWorker;
        this.loans = new LinkedList();
    }

    public CatalogedBook(String bookId, String title, String publisher, String edition, int publicationYear
            , String isbn, String author, String theme, int totalCopies, int availableCopies, String idWorker
            , LinkedList<Loan> loans) {
        super(bookId, title, publisher, edition, publicationYear, isbn, author, theme);
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.idWorker = idWorker;
        this.loans = loans;
    }

    // Getters & Setters
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

    public int getTotalCopies() {
        return totalCopies;
    }

    public LinkedList<Loan> getLoans() {
        return loans;
    }

    public void setLoans(LinkedList<Loan> loans) {
        this.loans = loans;
    }

    /***
     * Función que devuelve el total de copias DISPONIBLES que hay de un libro catalogado. Como tengo esta función no añado
     * un getter para este atributo
     * @return
     */
    public int numCopies() {
        return availableCopies;
    }

    /***
     * Función que añade un nuevo préstamo a la lista de préstamos del libro
     * @param loan
     */
    public void addnewLoanToLoans(Loan loan) {
        this.loans.insertEnd(loan);
    }
}
