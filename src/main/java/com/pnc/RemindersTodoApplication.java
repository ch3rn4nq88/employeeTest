package com.pnc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
@EnableAutoConfiguration
@Slf4j
public class RemindersTodoApplication {
	public static void main(String[] args) {
		SpringApplication.run(RemindersTodoApplication.class, args);
	}
}
