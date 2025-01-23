package br.com.marcosalencarc.job_management.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(){
        super("User not found!");
    }
    
}
