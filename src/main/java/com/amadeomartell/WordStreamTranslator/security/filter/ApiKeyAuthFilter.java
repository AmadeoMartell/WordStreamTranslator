package com.amadeomartell.WordStreamTranslator.security.filter;

import com.amadeomartell.WordStreamTranslator.security.token.ApiKeyAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String HEADER = "X-API-KEY";
    private final AuthenticationManager authenticationManager;

    public ApiKeyAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String key = request.getHeader(HEADER);

        if (StringUtils.hasText(key)) {
            ApiKeyAuthenticationToken token = new ApiKeyAuthenticationToken(key);
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            try {
                var authResult = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authResult);
            } catch (Exception failed) {
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
