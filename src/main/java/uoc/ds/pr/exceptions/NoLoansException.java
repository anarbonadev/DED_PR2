package uoc.ds.pr.exceptions;

/*
* Excepción que se lanza cuando no hay préstamos
* */
public class NoLoansException extends DSException {
    public NoLoansException() {
    }

    public NoLoansException(String msg) {
        super(msg);
    }
}
