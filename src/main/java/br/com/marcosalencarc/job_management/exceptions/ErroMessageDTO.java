package br.com.marcosalencarc.job_management.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErroMessageDTO {
    private String message;
    private String field;
}
