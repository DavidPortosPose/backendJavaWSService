package net.davidportos.backendJavaWSService;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.davidportos.SpringApplicationContext;
import net.davidportos.backendJavaWSService.dto.UserDTO;
import net.davidportos.backendJavaWSService.models.responses.UserRestResponse;
import net.davidportos.securityAccess.AppProperties;

@SpringBootApplication
@EnableJpaAuditing
public class BackendJavaWsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendJavaWsServiceApplication.class, args);
		System.out.println("Loading");
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}

	@Bean(name = "AppProperties")
	public AppProperties getAddProperties() {
		return new AppProperties();

	}

	@Bean()
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		mapper.typeMap(UserDTO.class, UserRestResponse.class).addMappings(m -> m.skip(UserRestResponse::setPostsList));

		return mapper;
	}
}
