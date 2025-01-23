package br.com.marcosalencarc.job_management.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.marcosalencarc.job_management.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.marcosalencarc.job_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.marcosalencarc.job_management.modules.candidate.repositories.CandidateRepository;

@Service
public class AuthCandidateUseCase {
    @Value("${security.token.secret.candidate}")
    private String secretKey;

    private CandidateRepository candidateRepository;
    private PasswordEncoder passwordEncoder;

    public AuthCandidateUseCase(CandidateRepository candidateRepository, PasswordEncoder passwordEncoder) {
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO)
            throws AuthenticationException {

        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("Username/Password incorrect");
                });

        var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(expiresIn)
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return authCandidateResponse;

    }
}
