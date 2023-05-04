package uz.pentagol.exceptions;

public class TokenNotValidException extends RuntimeException{
    public TokenNotValidException(String message){
        super(message);
    }
}
