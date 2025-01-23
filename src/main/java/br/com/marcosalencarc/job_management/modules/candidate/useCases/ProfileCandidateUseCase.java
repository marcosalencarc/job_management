package br.com.marcosalencarc.job_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.marcosalencarc.job_management.exceptions.UserNotFoundException;
import br.com.marcosalencarc.job_management.modules.candidate.dto.ProfileCandidateDTO;
import br.com.marcosalencarc.job_management.modules.candidate.repositories.CandidateRepository;

@Service
public class ProfileCandidateUseCase {

    private CandidateRepository candidateRepository;

    public ProfileCandidateUseCase(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public ProfileCandidateDTO execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });
        var profile = ProfileCandidateDTO.builder()
                .id(candidate.getId().toString())
                .name(candidate.getName())
                .description(candidate.getDescription())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .build();
        return profile;
    }

}
