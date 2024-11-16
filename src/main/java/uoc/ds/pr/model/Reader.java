package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;

import java.time.LocalDate;
import java.util.Comparator;

import static uoc.ds.pr.Library.MAXIMUM_NUMBER_OF_BOOKS;

/*
* Clase que representa a un lector
* */
public class Reader implements Comparable<Reader> {

    // Attributes
    private String id;               // Identificador
    private String name;             // Nombre
    private String surname;          // Apellido
    private String docId;            // DNI
    private LocalDate birthDate;     // Fecha de cumpleaños
    private String birthPlace;       // Lugar de nacimiento
    private String address;          // Dirección

    private LinkedList<Loan> loans;  // Lista de todos los préstamos que ha tenido el lector

    private Loan[] concurrentLoans;  // Vector que contiene los préstamos simultáneos que tiene un lector
    private int nextIndex;           // Me indica el siguiente índice libre del vector concurrentLoans

    // Comparadores
    public static final Comparator<Reader> CMP_LOANS = (r1, r2) -> r1.getName().compareTo(r2.getName());

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
        this.nextIndex = 0; // Lo inicializo a 0, para que el primer préstamo vaya en esta posición
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

    public int getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }

    /***
     * Función que inserta un nuevo préstamo en la lista de préstamos del lector
     * @param loan Es el nuevo préstamo del lector
     */
    public void addNewLoan(Loan loan){
        // Asignamos el préstamo en la posición que toque
        this.concurrentLoans[nextIndex] = loan;

        // Incrementamos el índice
        nextIndex++;
    }

    @Override
    public int compareTo(Reader o) {
        return this.id.compareTo(o.id);
    }
}
