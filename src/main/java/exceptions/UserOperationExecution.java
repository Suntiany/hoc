package exceptions;

public class UserOperationExecution extends RuntimeException {
    private static final long serialVersionUID = 2361446884822298906L;
    public UserOperationExecution(String msg){
        super(msg);
    }
}
