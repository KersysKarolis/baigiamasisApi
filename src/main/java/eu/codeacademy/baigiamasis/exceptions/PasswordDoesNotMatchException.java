package eu.codeacademy.baigiamasis.exceptions;

public class PasswordDoesNotMatchException extends Exception{
    public PasswordDoesNotMatchException (String message){
        super(message);
    }
}
