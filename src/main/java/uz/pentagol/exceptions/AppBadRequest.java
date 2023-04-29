package uz.pentagol.exceptions;

public class AppBadRequest extends RuntimeException{
    public AppBadRequest(String message){
        super(message);
    }
}
