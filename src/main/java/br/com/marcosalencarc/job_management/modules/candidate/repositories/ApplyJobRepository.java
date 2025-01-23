package br.com.marcosalencarc.job_management.modules.candidate.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.marcosalencarc.job_management.modules.candidate.entities.ApplyJobEntity;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
    
}
