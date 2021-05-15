package com.ssafy.green;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GreenFingersApplication {

	public static void main(String[] args) {
//		SpringApplication.run(GreenFingersApplication.class, args);
		SpringApplication app = new SpringApplication(GreenFingersApplication.class);
		app.addListeners(new ApplicationPidFileWriter());
		app.run(args);
	}

}
