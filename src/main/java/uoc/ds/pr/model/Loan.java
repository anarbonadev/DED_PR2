package uoc.ds.pr.model;

import uoc.ds.pr.Library;

import java.time.LocalDate;

/*
* Clase que representa a un préstamo
* */
public class Loan {
    // Attributes
    private String loanId;              // Identificador del préstamo
    private String readerId;            // Identificador del lector
    private String bookId;              // Identificador del libro
    private String workerId;            // Identificador del trabajador que lo presta
    private LocalDate date;             // La fecha del préstamo
    private LocalDate expirationDate;   // La fecha final de devolución
    private Library.LoanState state;    // Estado en el que puede estar un préstamo
    private String title;               // Título del libro prestado



    // Conctructor
    public Loan(String loanId, String readerId, String bookId, String workerId, LocalDate date
            , LocalDate expirationDate, Library.LoanState state, String title) {
        this.loanId = loanId;
        this.readerId = readerId;
        this.bookId = bookId;
        this.workerId = workerId;
        this.date = date;
        this.expirationDate = expirationDate;
        this.state = state;
        this.title = title;
    }

    // Getters & Setters
    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Library.LoanState getState() {
        return state;
    }

    public void setState(Library.LoanState state) {
        this.state = state;
    }

    /***
     * Función que devuelve el título del libro prestado
     * @return
     */
    public String getTitle(){
        return this.title;
    }
}
