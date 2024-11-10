package uoc.ds.pr.model;

import java.time.LocalDate;

/*
* Clase que representa a un préstamo
* */
public class Loan {
    // Attributes
    public String loanId;               // Identificador del préstamo
    public String readerId;             // Identificador del lector
    public String bookId;               // Identificador del libro
    public String workerId;             // Identificador del trabajador que lo presta
    public LocalDate date;              // La fecha del préstamo
    public LocalDate expirationDate;    // La fecha final de devolución
    public String state;               // Estado en el que puede estar un préstamo


    // Conctructor
    public Loan(String loanId, String readerId, String bookId, String workerId, LocalDate date, LocalDate expirationDate, String state) {
        this.loanId = loanId;
        this.readerId = readerId;
        this.bookId = bookId;
        this.workerId = workerId;
        this.date = date;
        this.expirationDate = expirationDate;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /***
     *
     * @return
     */
    public String getTitle(){
        //TODO: revisar este método, porque se usa en la clase LibraryPR2Test, pero no tiene sentido porque un préstamo sólo guarda el bookId
        return "";
    }
}
