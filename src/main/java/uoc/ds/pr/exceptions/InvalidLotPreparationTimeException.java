package uoc.ds.pr.exceptions;

/*
* Excepción que se lanza cuando el tiempo medio de preparación de un montón no es un número o es menor que cero
* */
public class InvalidLotPreparationTimeException extends DSException {
    public InvalidLotPreparationTimeException() {
    }

    public InvalidLotPreparationTimeException(String msg) {
        super(msg);
    }
}
