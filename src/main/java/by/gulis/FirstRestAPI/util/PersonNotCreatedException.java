package by.gulis.FirstRestAPI.util;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException(String msg){
        super(msg);
    }
}
