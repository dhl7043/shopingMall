package shopingmall.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import shopingmall.project.config.AuditorAwareImpl;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing // BaseEntity 동작
@SpringBootApplication
linkppublic class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
	public AuditorAware<Long> auditorProvider() {
		// baseEntity에서 createdBy, lastModifiedBy 에 아이디 값 넣기용
		// springsecurity써서 아이디같은 세션정보 꺼내와야함 아래는 테스트용 Optional.of(id) get으로 아이디 꺼내오기

		return new AuditorAwareImpl();
	}
}
