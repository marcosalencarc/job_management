package br.com.marcosalencarc.job_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.marcosalencarc.job_management.exceptions.JobNotFoundException;
import br.com.marcosalencarc.job_management.exceptions.UserNotFoundException;
import br.com.marcosalencarc.job_management.modules.candidate.entities.ApplyJobEntity;
import br.com.marcosalencarc.job_management.modules.candidate.repositories.ApplyJobRepository;
import br.com.marcosalencarc.job_management.modules.candidate.repositories.CandidateRepository;
import br.com.marcosalencarc.job_management.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    private CandidateRepository candidateRepository;

    private JobRepository jobRepository;

    private ApplyJobRepository applyJobRepository;

    public ApplyJobCandidateUseCase(CandidateRepository candidateRepository, JobRepository jobRepository,
            ApplyJobRepository applyJobRepository) {
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.applyJobRepository = applyJobRepository;
    }

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        this.jobRepository.findById(idJob).orElseThrow(() -> {
            throw new JobNotFoundException();
        });

        var applyJob = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob).build();

        applyJob = this.applyJobRepository.save(applyJob);

        return applyJob;
    }
}
