package gbennett.autotodo;

import gbennett.autotodo.clients.CanvasClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class AutoToDoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AutoToDoApplication.class, args);
		CanvasClient login = context.getBean(CanvasClient.class);
		try {
			System.out.println(login.getToDoList());
		} catch (IOException e) {
			log.error("Error reading properties file");
		}
	}

}

