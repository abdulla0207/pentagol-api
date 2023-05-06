package uz.pentagol.exceptions;

public class EntityNotUpdateException extends RuntimeException{
    public EntityNotUpdateException(String message){
        super(message);
    }
}
