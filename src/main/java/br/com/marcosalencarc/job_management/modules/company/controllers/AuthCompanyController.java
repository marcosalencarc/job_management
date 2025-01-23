package br.com.marcosalencarc.job_management.modules.company.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcosalencarc.job_management.modules.company.dto.AuthCompanyDTO;
import br.com.marcosalencarc.job_management.modules.company.useCases.AuthCompanyUseCase;


@RestController
@RequestMapping("/company")
public class AuthCompanyController {
    
    private AuthCompanyUseCase authCompanyUseCase;

    public AuthCompanyController(AuthCompanyUseCase authCompanyUseCase){
        this.authCompanyUseCase = authCompanyUseCase;
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCompanyDTO authCompanyDTO){      
        try {
            var result = this.authCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    
}
