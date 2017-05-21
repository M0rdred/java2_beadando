package hu.tutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import hu.tutor.security.SecurityConfig;

@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
@ServletComponentScan
@ComponentScan
@SpringBootApplication
public class Java2BeadandoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Java2BeadandoApplication.class, args);
	}
}
