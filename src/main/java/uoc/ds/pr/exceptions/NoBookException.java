package uoc.ds.pr.exceptions;

/*
* Excepción que se lanza cuando no hay ningún libro pendiente de catalogar
* */
public class NoBookException extends DSException {
    public NoBookException() {
    }

    public NoBookException(String msg) {
        super(msg);
    }
}
