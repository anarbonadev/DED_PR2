package uoc.ds.pr.exceptions;

/*
* Excepción que se lanza cuando el tiempo medio de catalogación de un libro no es un número o es menor que cero
* */
public class InvalidCatalogTimeException extends DSException {
    public InvalidCatalogTimeException() {
    }

    public InvalidCatalogTimeException(String msg) {
        super(msg);
    }
}
