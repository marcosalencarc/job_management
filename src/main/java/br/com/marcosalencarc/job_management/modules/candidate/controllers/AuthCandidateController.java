package br.com.marcosalencarc.job_management.modules.candidate.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcosalencarc.job_management.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.marcosalencarc.job_management.modules.candidate.useCases.AuthCandidateUseCase;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {
    private AuthCandidateUseCase authCandidateUseCase;

    public AuthCandidateController(AuthCandidateUseCase authCandidateUseCase){
        this.authCandidateUseCase = authCandidateUseCase;
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateDTO){      
        try {
            var result = this.authCandidateUseCase.execute(authCandidateDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
