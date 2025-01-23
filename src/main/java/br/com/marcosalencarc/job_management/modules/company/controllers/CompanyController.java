package br.com.marcosalencarc.job_management.modules.company.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcosalencarc.job_management.modules.company.entities.CompanyEntity;
import br.com.marcosalencarc.job_management.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/company")
public class CompanyController {

    private CreateCompanyUseCase createCompanyUseCase;

    public CompanyController(CreateCompanyUseCase createCompanyUseCase){
        this.createCompanyUseCase = createCompanyUseCase;
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
        try{
            var result = this.createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
}
