package exceptions;

public class DoctorCategoryOperationException extends RuntimeException {
    private static final long serialVersionUID = 1182563719599527969L;
    public DoctorCategoryOperationException(String msg){
        super(msg);
    }
}
