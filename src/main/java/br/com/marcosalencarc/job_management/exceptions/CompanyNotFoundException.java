package br.com.marcosalencarc.job_management.exceptions;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException(){
        super("Company not found");
    }
    
}
