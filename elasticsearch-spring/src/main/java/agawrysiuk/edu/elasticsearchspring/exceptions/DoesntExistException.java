package agawrysiuk.edu.elasticsearchspring.exceptions;

public class DoesntExistException extends RuntimeException {

    public DoesntExistException(String msg) {
        super(msg);
    }
}
