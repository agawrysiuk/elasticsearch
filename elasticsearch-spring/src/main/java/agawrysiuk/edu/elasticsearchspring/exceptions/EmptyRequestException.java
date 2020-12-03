package agawrysiuk.edu.elasticsearchspring.exceptions;

public class EmptyRequestException extends RuntimeException {

    public EmptyRequestException(String msg) {
        super(msg);
    }
}
