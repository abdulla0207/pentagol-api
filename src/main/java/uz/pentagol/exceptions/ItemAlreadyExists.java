package uz.pentagol.exceptions;

public class ItemAlreadyExists extends RuntimeException{
    public ItemAlreadyExists(String message){
        super(message);
    }
}
