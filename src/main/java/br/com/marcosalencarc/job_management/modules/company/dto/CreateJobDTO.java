package br.com.marcosalencarc.job_management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {
    @Schema(example = "Job for developer Junior", requiredMode = RequiredMode.REQUIRED)
    private String description;
    @Schema(example = "JUNIOR", requiredMode = RequiredMode.REQUIRED)
    private String level;
    @Schema(example = "Gym Pass, Health Plan", requiredMode = RequiredMode.REQUIRED)
    private String benefits;
}
