package br.com.marcosalencarc.job_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private SecurityCompanyFilter securityCompanyFilter;

    private SecurityCandidateFilter securityCandidateFilter;

    private static final String [] PERMIT_ALL_LIST = {
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/actuator/**"
    };

    public SecurityConfig(SecurityCompanyFilter securityFilter, SecurityCandidateFilter securityCandidateFilter) {
        this.securityCompanyFilter = securityFilter;
        this.securityCandidateFilter = securityCandidateFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/candidate/").permitAll()
                            .requestMatchers("/company/").permitAll()
                            .requestMatchers("/company/auth").permitAll()
                            .requestMatchers("/candidate/auth").permitAll()
                            .requestMatchers(PERMIT_ALL_LIST).permitAll();
                    auth.anyRequest().authenticated();

                })
                .addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
