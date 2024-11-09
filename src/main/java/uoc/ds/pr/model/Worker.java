package uoc.ds.pr.model;

/*
* Clase que representa a un trabajador de la biblioteca
* */
public class Worker {

    // Attributes
    public String id;       // Identificador del trabajador
    public String name;     // Nombre del trabajador
    public String surname;  // Apellido del trabajador

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
}
