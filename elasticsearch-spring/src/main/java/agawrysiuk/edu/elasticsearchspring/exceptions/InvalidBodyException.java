package agawrysiuk.edu.elasticsearchspring.exceptions;

public class InvalidBodyException extends RuntimeException {

    public InvalidBodyException(String msg) {
        super(msg);
    }
}
