package com.okstatelibrary.spacesui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private static final String[] PUBLIC_MATCHERS = {
            "/",
            "/index",
            "/cancel",
            "/booking",
            "/reserve",
            "/summary/**",
            "/error/**",
            "/css/**",
            "/js/**",
            "/img/**",
            "/console/**"
    };

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            RelyingPartyRegistrationRepository relyingPartyRegistrationRepository)
            throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_MATCHERS).permitAll()
                .requestMatchers("/login/**").permitAll()
                .requestMatchers("/saml2/**").permitAll()
                .anyRequest().authenticated()
            )

            .saml2Login(saml -> saml
//                    .relyingPartyRegistrationRepository(
//                            relyingPartyRegistrationRepository
//                    )
                    .successHandler(successHandler())
            )

            .saml2Logout(Customizer.withDefaults())

            .logout(logout -> logout
                .logoutSuccessUrl("/")
            )

            .sessionManagement(session -> session
                .invalidSessionUrl("/")
            )

            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler successHandler() {

        SavedRequestAwareAuthenticationSuccessHandler handler =
                new SavedRequestAwareAuthenticationSuccessHandler();

        handler.setDefaultTargetUrl("/landing");

        return handler;
    }

//    @Bean
//    public RelyingPartyRegistrationRepository relyingPartyRegistrationRepository() {
//
//        RelyingPartyRegistration registration =
//                RelyingPartyRegistration
//                        .withRegistrationId("okstate")
//
//                        .entityId("edu:okstate:library:spaces")
//
//                        .assertionConsumerServiceLocation(
//                                "http://localhost:8080/login/saml2/sso/okstate"
//                        )
//
//                        .assertingPartyMetadata(party -> party
//                                .entityId("YOUR_IDP_ENTITY_ID")
//                                .singleSignOnServiceLocation(
//                                        "YOUR_IDP_SSO_URL"
//                                )
//                                .wantAuthnRequestsSigned(false)
////                                .verificationX509Credentials(c -> c.add(
////                                        idpVerificationCredential
////                                ))
//                        )
//
//                        .build();
//
//        return new InMemoryRelyingPartyRegistrationRepository(
//                registration
//        );
//    }


}

