package com.amadeomartell.WordStreamTranslator.security.provider;

import com.amadeomartell.WordStreamTranslator.repository.ApiKeyRepository;
import com.amadeomartell.WordStreamTranslator.security.token.ApiKeyAuthenticationToken;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private final ApiKeyRepository repo;

    public ApiKeyAuthenticationProvider(ApiKeyRepository repo) {
        this.repo = repo;
    }

    @Override
    @Cacheable("apiKeys")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String key = authentication.getCredentials().toString();
        if (!repo.existsByKey(key)) {
            throw new BadCredentialsException("API Key not found");
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new ApiKeyAuthenticationToken(key, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
