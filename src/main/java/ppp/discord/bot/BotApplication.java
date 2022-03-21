package ppp.discord.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BotApplication {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(BotApplication.class, args);
		for(String c: context.getBeanDefinitionNames()) {
			System.out.println(c);
		}
	}

}
