package me.tbandawa.web.skyzmetro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SecurityScheme(
	name = "Bearer Authentication",
	type = SecuritySchemeType.HTTP,
	bearerFormat = "JWT",
	scheme = "bearer"
)
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(new Info().title("ZAMA Registration APIs")
				.description("<b>Endpoint API Definitions</b>")
				.version("1.0.0"));
	}
}
