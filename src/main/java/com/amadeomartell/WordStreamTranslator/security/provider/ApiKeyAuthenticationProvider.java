package com.amadeomartell.WordStreamTranslator.security.provider;

import com.amadeomartell.WordStreamTranslator.repository.ApiKeyRepository;
import com.amadeomartell.WordStreamTranslator.security.token.ApiKeyAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private final ApiKeyRepository repo;

    public ApiKeyAuthenticationProvider(ApiKeyRepository repo) {
        this.repo = repo;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String apiKey = authentication.getCredentials().toString();
        log.info("Authenticating API key: {}", apiKey);

        if (!repo.existsByKey(apiKey)) {
            throw new BadCredentialsException("API Key not found");
        }

        return new ApiKeyAuthenticationToken(
                apiKey,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
