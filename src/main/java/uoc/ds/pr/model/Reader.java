package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;

import java.time.LocalDate;

import static uoc.ds.pr.Library.MAXIMUM_NUMBER_OF_BOOKS;

/*
* Clase que representa a un lector
* */
public class Reader {

    // Attributes
    public String id;               // Identificador
    public String name;             // Nombre
    public String surname;          // Apellido
    public String docId;            // DNI
    public LocalDate birthDate;     // Fecha de cumpleaños
    public String birthPlace;       // Lugar de nacimiento
    public String address;          // Dirección

    public LinkedList<Loan> loans;  // Lista de todos los préstamos que ha tenido el lector

    public Loan[] concurrentLoans;  // Vector que contiene los préstamos simultáneos que tiene un lector


    // Constructor
    public Reader(String id, String name, String surname, String docId, LocalDate birthDate, String birthPlace
            , String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.docId = docId;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.address = address;
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

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LinkedList<Loan> getLoans() {
        return loans;
    }

    public void setLoans(LinkedList<Loan> loans) {
        this.loans = loans;
    }

    public Loan[] getConcurrentLoans() {
        return concurrentLoans;
    }

    public void setConcurrentLoans(Loan[] concurrentLoans) {
        this.concurrentLoans = concurrentLoans;
    }
}
