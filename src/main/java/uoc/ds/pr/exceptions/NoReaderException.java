package uoc.ds.pr.exceptions;

/*
* Excepción que se lanza cuando no yay un lector que más lea
* */
public class NoReaderException extends DSException {
    public NoReaderException() {
    }

    public NoReaderException(String msg) {
        super(msg);
    }
}
