package uoc.ds.pr.exceptions;

/*
* Excepción que se lanza cuando no exíste el préstamo
* */
public class LoanNotFoundException extends DSException {
    public LoanNotFoundException() {
    }

    public LoanNotFoundException(String msg) {
        super(msg);
    }
}
