package uz.pentagol.exceptions;

public class AppForbiddenException extends RuntimeException{
    public AppForbiddenException(String message){
        super(message);
    }
}
