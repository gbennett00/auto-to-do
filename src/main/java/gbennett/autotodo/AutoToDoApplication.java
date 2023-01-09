package gbennett.autotodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AutoToDoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AutoToDoApplication.class, args);
	}

}
