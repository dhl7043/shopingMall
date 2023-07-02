package shopingmall.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import shopingmall.project.config.handler.LoginFailHandler;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("Not Found Authentication");
            return null;
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getDetails();

        return Optional.of(userPrincipal.getUserId());
    }
}
