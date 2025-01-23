package br.com.marcosalencarc.job_management.modules.candidate.useCases;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.marcosalencarc.job_management.modules.company.entities.JobEntity;
import br.com.marcosalencarc.job_management.modules.company.repositories.JobRepository;

@Service
public class ListAllJobsByFilterUseCase {
    
    private JobRepository jobRepository;

    public ListAllJobsByFilterUseCase(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    public List<JobEntity> execute(String filter){
        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }

}
