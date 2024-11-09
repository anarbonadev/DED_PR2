package uoc.ds.pr.exceptions;

/*
 * Excepción que se lanza cuando el lector ya tiene 3 libros en préstamo
 * */
public class MaximumNumberOfBooksException extends DSException {
    public MaximumNumberOfBooksException() {
    }

    public MaximumNumberOfBooksException(String msg) {
        super(msg);
    }
}
