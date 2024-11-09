package uoc.ds.pr.exceptions;

/*
 * Excepción que se lanza cuando no caben más lectores en el array
 */
public class MaxNumReachedException extends DSException {
    public MaxNumReachedException() {
    }

    public MaxNumReachedException(String msg) {
        super(msg);
    }
}
