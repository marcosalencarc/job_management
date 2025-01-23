package br.com.marcosalencarc.job_management.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="company")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Pattern(regexp = "\\S+", message = "the field [username] must not contain spaces")
    private String username;
    
    @Email(message = "The filed [email] must contain a valid e-mail")
    private String email;

    @Length(min = 6, max = 100, message = "The password must contain between 6 and 100 characters")
    private String password;
    
    private String website;
    private String name;
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
    

}
