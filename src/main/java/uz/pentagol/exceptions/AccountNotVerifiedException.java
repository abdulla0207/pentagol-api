package uz.pentagol.exceptions;

public class AccountNotVerifiedException extends RuntimeException{
    public AccountNotVerifiedException(String message){
        super(message);
    }
}
