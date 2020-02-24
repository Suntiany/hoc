package exceptions;

public class HospitalOperationException extends RuntimeException{
    private static final long serialVersionUID = 2361446884822298905L;
    public HospitalOperationException(String msg){
        super(msg);
    }
}
