package br.com.marcosalencarc.job_management.modules.company.useCases;

import org.springframework.stereotype.Service;

import br.com.marcosalencarc.job_management.exceptions.CompanyNotFoundException;
import br.com.marcosalencarc.job_management.modules.company.entities.JobEntity;
import br.com.marcosalencarc.job_management.modules.company.repositories.CompanyRepository;
import br.com.marcosalencarc.job_management.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {

    private JobRepository jobRepository;
    private CompanyRepository companyRepository;

    public CreateJobUseCase (JobRepository jobRepository, CompanyRepository companyRepository){
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    public JobEntity execute(JobEntity jobEntity){
        this.companyRepository
        .findById(jobEntity.getCompanyId())
        .ifPresentOrElse((user)->{}, ()->{
            throw new CompanyNotFoundException();
        });
        return this.jobRepository.save(jobEntity);
    }
    
}
