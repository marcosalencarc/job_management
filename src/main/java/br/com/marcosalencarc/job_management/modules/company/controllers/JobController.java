package br.com.marcosalencarc.job_management.modules.company.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcosalencarc.job_management.modules.company.dto.CreateJobDTO;
import br.com.marcosalencarc.job_management.modules.company.entities.JobEntity;
import br.com.marcosalencarc.job_management.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/company/job")
public class JobController {
    
    private CreateJobUseCase createJobUseCase;

    public JobController(CreateJobUseCase createJobUseCase){
        this.createJobUseCase = createJobUseCase;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name = "Jobs", description = "Jobs informations")
    @Operation(
        summary = "Jobs registration", 
        description = "This function is responsible for registering job vacancies in a company")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = JobEntity.class))
        })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        try{
            var companyId = request.getAttribute("company_id");
            
            var entity = JobEntity.builder()
            .level(createJobDTO.getLevel())
            .companyId(UUID.fromString(companyId.toString()))
            .benefits(createJobDTO.getBenefits())
            .description(createJobDTO.getDescription())
            .build();

            var result = this.createJobUseCase.execute(entity);
            
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    

}
