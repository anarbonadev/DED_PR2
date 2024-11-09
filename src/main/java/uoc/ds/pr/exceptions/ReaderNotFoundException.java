package uoc.ds.pr.exceptions;

/*
 * Excepción que se lanza cuando no exíste el lector
 * */
public class ReaderNotFoundException extends DSException {
    public ReaderNotFoundException() {
    }

    public ReaderNotFoundException(String msg) {
        super(msg);
    }
}
