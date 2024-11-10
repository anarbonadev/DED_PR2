package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;

/*
* Clase que representa a un trabajador de la biblioteca
* */
public class Worker {

    // Attributes
    public String id;                                   // Identificador del trabajador
    public String name;                                 // Nombre del trabajador
    public String surname;                              // Apellido del trabajador

    public LinkedList<Loan> openLoans;                  // Préstamos abiertos por un trabajador
    public LinkedList<Loan> closedLoans;                // Préstamos cerrados por un trabajador
    public LinkedList<CatalogedBook> catalogedBooks;    // Libros catalogados por un trabajador


    // Constructor
    public Worker(String id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LinkedList<Loan> getOpenLoans() {
        return openLoans;
    }

    public void setOpenLoans(LinkedList<Loan> openLoans) {
        this.openLoans = openLoans;
    }

    public LinkedList<Loan> getClosedLoans() {
        return closedLoans;
    }

    public void setClosedLoans(LinkedList<Loan> closedLoans) {
        this.closedLoans = closedLoans;
    }

    public LinkedList<CatalogedBook> getCatalogedBooks() {
        return catalogedBooks;
    }

    public void setCatalogedBooks(LinkedList<CatalogedBook> catalogedBooks) {
        this.catalogedBooks = catalogedBooks;
    }

    /***
     * Función que inserta un nuevo libro a la lista de libros catalogados por el trabajador
     * @param catalogedBook Es el libro recién catalogado que insertamos
     */
    public void addToWorkerCatalog(CatalogedBook catalogedBook) {
        this.catalogedBooks.insertEnd(catalogedBook);
    }
}
