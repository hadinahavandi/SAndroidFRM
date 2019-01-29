package common.Exceptions;

public class InvalidInputException extends SweetException {
    public InvalidInputException(String ErrorMessage,String FieldID)
    {
        super(ErrorMessage);
    }

}
