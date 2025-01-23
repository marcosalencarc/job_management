package br.com.marcosalencarc.job_management.modules.candidate.useCases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.marcosalencarc.job_management.exceptions.UserFoundException;
import br.com.marcosalencarc.job_management.modules.candidate.entities.CandidateEntity;
import br.com.marcosalencarc.job_management.modules.candidate.repositories.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    private CandidateRepository candidateRepository;

    private PasswordEncoder passwordEncoder;

    public CreateCandidateUseCase(CandidateRepository candidateRepository, PasswordEncoder passwordEncoder) {
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        var password = passwordEncoder.encode(candidateEntity.getPassword());
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getUsername())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        candidateEntity.setPassword(password);
        return candidateRepository.save(candidateEntity);
    }

}
