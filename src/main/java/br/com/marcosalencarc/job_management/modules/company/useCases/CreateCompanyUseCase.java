package br.com.marcosalencarc.job_management.modules.company.useCases;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.marcosalencarc.job_management.exceptions.UserFoundException;
import br.com.marcosalencarc.job_management.modules.company.entities.CompanyEntity;
import br.com.marcosalencarc.job_management.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase{

    private CompanyRepository companyRepository;

    private PasswordEncoder passwordEncoder;

    public CreateCompanyUseCase(CompanyRepository companyRepository, PasswordEncoder passwordEncoder){
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CompanyEntity execute(CompanyEntity companyEntity){
        this.companyRepository
        .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getUsername())
        .ifPresent((user)->{
            throw new UserFoundException();
        });
        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);
        return companyRepository.save(companyEntity);
    }
}