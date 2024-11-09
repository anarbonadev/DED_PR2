package uoc.ds.pr.exceptions;

/*
 * Excepción que se lanza cuando no existe el trabajador
 * */
public class WorkerNotFoundException extends DSException {
    public WorkerNotFoundException() {
    }

    public WorkerNotFoundException(String msg) {
        super(msg);
    }
}
