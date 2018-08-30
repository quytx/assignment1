package assignment;

public class EntityException extends RuntimeException {

    public enum EntityExceptionError {

        INVALID_LINK("assignment.Link is null or invalid"),
        INVALID_ENTITY("assignment.Entity is null");

        private final String errMsg;

        EntityExceptionError(String errMsg) {
            this.errMsg = errMsg;
        }

    }

    public EntityException(EntityExceptionError error) {
        super(error.toString());
    }

}
