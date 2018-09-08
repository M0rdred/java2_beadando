package hu.tutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
@ServletComponentScan
@ComponentScan
@SpringBootApplication
public class TutorThesisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorThesisApplication.class, args);
	}
}
