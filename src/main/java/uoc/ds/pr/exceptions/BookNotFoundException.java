package uoc.ds.pr.exceptions;

/*
 * Excepción que se lanza cuando no exíste el libro
 * */
public class BookNotFoundException extends DSException {
    public BookNotFoundException() {
    }

    public BookNotFoundException(String msg) {
        super(msg);
    }
}
