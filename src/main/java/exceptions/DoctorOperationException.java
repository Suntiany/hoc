package exceptions;

public class DoctorOperationException extends RuntimeException {
    private static final long serialVersionUID = 5076172298827469013L;

    public DoctorOperationException(String msg) {
        super(msg);
    }
}
