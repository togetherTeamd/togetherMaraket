package com.project.together;

import com.project.together.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.SessionTrackingMode;
import java.util.Collections;

@EnableJpaAuditing
@SpringBootApplication
public class TogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(TogetherApplication.class, args);
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
		return new HiddenHttpMethodFilter();
	}

	@Bean
	public ServletContextInitializer configSession() {
		return servletContext -> {
			servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));

			servletContext.getSessionCookieConfig()
					.setHttpOnly(true);
		};
	}
}
