package com.amadeomartell.WordStreamTranslator.security.filter;

import com.amadeomartell.WordStreamTranslator.security.token.ApiKeyAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String HEADER = "X-API-KEY";
    private final AuthenticationManager authManager;

    public ApiKeyAuthFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        String key = req.getHeader(HEADER);
        log.info("Received API key: {}", key);

        if (StringUtils.hasText(key)) {
            var authReq = new ApiKeyAuthenticationToken(key);
            authReq.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            try {
                var authRes = authManager.authenticate(authReq);
                SecurityContextHolder.getContext().setAuthentication(authRes);
                log.info("Authentication successful for key: {}", key);
            } catch (Exception ex) {
                log.warn("Authentication failed for key: {}", key);
                SecurityContextHolder.clearContext();
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}
