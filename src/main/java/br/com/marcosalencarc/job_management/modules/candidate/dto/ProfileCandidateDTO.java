package br.com.marcosalencarc.job_management.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateDTO {
    private String id;
    private String name;
    private String username;
    private String description;
    private String email;
}
