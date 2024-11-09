package uoc.ds.pr.model;

import java.time.LocalDate;

/*
* Clase que representa a un lector
* */
public class Reader {

    // Attributes
    public String id;           // Identificador
    public String name;         // Nombre
    public String surname;      // Apellido
    public String docId;        // DNI
    public LocalDate birthDate; // Fecha de cumpleñaos
    public String birthPlace;   // Luegar de nacimiento
    public String address;      // Dirección

    // Constructor
    public Reader(String id, String name, String surname, String docId, LocalDate birthDate, String birthPlace, String address) {
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
}
