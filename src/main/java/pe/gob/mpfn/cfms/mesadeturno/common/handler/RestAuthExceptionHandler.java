package pe.gob.mpfn.cfms.mesadeturno.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class RestAuthExceptionHandler implements AuthenticationEntryPoint, AuthenticationFailureHandler, AccessDeniedHandler {
    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.error("commence", authException);
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = authException != null ? authException.getMessage() : "";
        if (authException instanceof OAuth2AuthenticationException exec) {
            OAuth2Error error = exec.getError();
            if (error instanceof BearerTokenError bearerTokenError) {
                if (StringUtils.hasText(error.getDescription())) {
                    message = error.getDescription();
                }
                status = bearerTokenError.getHttpStatus();
            }
        }

        if (!response.isCommitted()) {
            response.setContentType("application/json");
            response.setStatus(status.value());
            response.getWriter().write("{\"message\":\"" + message + "\"}");
        }
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.error("onAuthenticationFailure", exception);
        if (!response.isCommitted()) {
            String message = exception != null ? exception.getMessage() : "";
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"message\":\"" + message + "\"}");
        }
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("handle", accessDeniedException);
        if (!response.isCommitted()) {
            String message = accessDeniedException != null ? accessDeniedException.getMessage() : "";
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"message\":\"" + message + "\"}");
        }
    }
}
