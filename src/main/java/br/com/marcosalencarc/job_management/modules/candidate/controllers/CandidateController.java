package br.com.marcosalencarc.job_management.modules.candidate.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcosalencarc.job_management.modules.candidate.dto.ProfileCandidateDTO;
import br.com.marcosalencarc.job_management.modules.candidate.entities.CandidateEntity;
import br.com.marcosalencarc.job_management.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.marcosalencarc.job_management.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.marcosalencarc.job_management.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.marcosalencarc.job_management.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.marcosalencarc.job_management.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Candidate informations")
public class CandidateController {

    private CreateCandidateUseCase createCandidateUseCase;

    private ProfileCandidateUseCase profileCandidateUseCase;

    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    public CandidateController(CreateCandidateUseCase createCandidateUseCase,
            ProfileCandidateUseCase profileCandidateUseCase,
            ListAllJobsByFilterUseCase listAllJobsByFilterUseCase,
            ApplyJobCandidateUseCase applyJobCandidateUseCase) {
        this.createCandidateUseCase = createCandidateUseCase;
        this.profileCandidateUseCase = profileCandidateUseCase;
        this.listAllJobsByFilterUseCase = listAllJobsByFilterUseCase;
        this.applyJobCandidateUseCase = applyJobCandidateUseCase;
    }

    @PostMapping("/")
    @Operation(summary = "Register a candidate", description = "This function is reponsible for register a candidate")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
                @Content(schema = @Schema(implementation = CandidateEntity.class))
        }),
        @ApiResponse(responseCode = "400", description = "User already exists")
})
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Candidate Profile", description = "This function is responsible for searching information about the candidate's profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var result = this.profileCandidateUseCase
                    .execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "List of available jobs for the candidate", description = "This function is reponsible for listing all available jobs by filter")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(Optional<String> filter) {
        String paramValue = filter.orElse("");
        return this.listAllJobsByFilterUseCase.execute(paramValue);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Registering a candidate for a job", description = "This function is reponsible for aegistering a candidate for a job")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob ) {
        var idCandidate = request.getAttribute("candidate_id");

        try {
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
